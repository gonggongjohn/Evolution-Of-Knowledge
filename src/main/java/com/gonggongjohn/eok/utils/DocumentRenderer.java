package com.gonggongjohn.eok.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.api.gui.Colors;
import com.gonggongjohn.eok.api.render.GLUtils;
import com.gonggongjohn.eok.api.utils.DataUtils;
import com.gonggongjohn.eok.api.utils.Utils;
import com.gonggongjohn.eok.api.utils.datatypes.ColorRGB;
import com.gonggongjohn.eok.api.utils.datatypes.Size2i;
import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class DocumentRenderer {
	private final int org1X;
	private final int org1Y;
	private final int org2X;
	private final int org2Y;
	private final int width;
	private final int height;
	private final Logger logger;
	private final Tessellator tessellator;
	private final BufferBuilder bufferBuilder;
	private int lineNumber;
	private ResourceLocation documentLocation;
	private File documentFile;
	private String[] orgLines;
	private Map<String, Predicate<String[]>> tokenMap;
	private Document documentIn;
	private List<Page> pages = new ArrayList<Page>();
	private List<Element> elements = new ArrayList<Element>();
	private boolean available;
	public final boolean isDocumentExternal;
	private final List<Integer> textureList;
	public static final String localManualPath = "local_manual" + File.separator;
	
	public DocumentRenderer(int org1X, int org1Y, int org2X, int org2Y, int width, int height, String documentPath) {
		isDocumentExternal = false;
		this.org1X = org1X;
		this.org1Y = org1Y;
		this.org2X = org2X;
		this.org2Y = org2Y;
		this.width = width;
		this.height = height;
		logger = EOK.getLogger();
		this.tessellator = Tessellator.getInstance();
		this.bufferBuilder = tessellator.getBuffer();
		this.documentLocation = new ResourceLocation(documentPath);
		this.tokenMap = new HashMap<String, Predicate<String[]>>();
		textureList = new ArrayList<Integer>();
		init();
		if(!(available = read())) {
			Minecraft.getMinecraft().player.sendStatusMessage(new TextComponentString(I18n.format("message.documentrenderer.error")), false);
		}
	}
	
	public DocumentRenderer(int org1X, int org1Y, int org2X, int org2Y, int width, int height, File documentFile) {
		this.isDocumentExternal = true;
		this.org1X = org1X;
		this.org1Y = org1Y;
		this.org2X = org2X;
		this.org2Y = org2Y;
		this.width = width;
		this.height = height;
		logger = EOK.getLogger();
		this.tessellator = Tessellator.getInstance();
		this.bufferBuilder = Tessellator.getInstance().getBuffer();
		this.documentFile = documentFile;
		this.tokenMap = new HashMap<String, Predicate<String[]>>();
		textureList = new ArrayList<Integer>();
		init();
		if(!(available = read())) {
			Minecraft.getMinecraft().player.sendStatusMessage(new TextComponentString(I18n.format("message.documentrenderer.error")), false);
		}
	}
	
	public boolean isAvailable() {
		return available;
	}
	
	public void remove() {
		for(int id : this.textureList) {
			GLUtils.deleteTexture(id);
		}
		if(documentIn != null) {
			documentIn.remove();
		}
	}
	
	private void init() {
		tokenMap.put("center", this::addCenteredText);
		tokenMap.put("end_of_page", this::addEndOfPageMark);
		tokenMap.put("comment", (s) -> true);
		tokenMap.put("image", this::addImage);
		tokenMap.put("drawline", this::addLine);
		tokenMap.put("hyperlink", this::addHyperLink);
	}
	
	public static enum DocumentSide {
		LEFT, RIGHT
	}
	
	public BufferedReader getResource(ResourceLocation location) throws UnsupportedEncodingException, IOException {
		return new BufferedReader(new InputStreamReader(DataUtils.getResource(location), "UTF-8"));
	}
	
	public boolean reload(boolean isExternal, String path) {
		this.remove();
		if(!isExternal) {
			this.documentLocation = new ResourceLocation(path);
		} else {
			this.documentFile = new File(path);
		}
		if(!(available = read())) {
			Minecraft.getMinecraft().player.sendStatusMessage(new TextComponentString(I18n.format("message.documentrenderer.error")), false);
		}
		return this.available;
	}
	
	public boolean read() {
		pages.clear();
		elements.clear();
		available = true;
		this.remove();
		this.textureList.clear();
		lineNumber = 0;
		long startTime = System.currentTimeMillis();
		if(this.width < 20 || this.height < 20) {
			this.error(I18n.format("manual.error.text_area_too_small"));
			return false;
		}
		if(!isDocumentExternal) {
			logger.info("Loading document {}:{}", documentLocation.getResourceDomain(), documentLocation.getResourcePath());
		} else {
			logger.info("Loading document {}", documentFile.getAbsolutePath());
		}
		lineNumber = 1;
		BufferedReader reader;
		List<String> lineList = new ArrayList<String>();
		try {
			if(!isDocumentExternal) {
				reader = getResource(documentLocation);
				String line;
				while((line = reader.readLine()) != null) {
					lineList.add(line);
				}
				orgLines = lineList.toArray(new String[0]);
			} else {
				reader = new BufferedReader(new InputStreamReader(new FileInputStream(documentFile), "UTF-8"));
				String line;
				while((line = reader.readLine()) != null) {
					lineList.add(line);
				}
				orgLines = lineList.toArray(new String[0]);
			}
		} catch(Exception e) {
			if(!isDocumentExternal) {
				this.error(I18n.format("manual.error.cant_read_document_file", this.documentLocation.toString()));
			} else {
				this.error(I18n.format("manual.error.cant_read_document_file", this.documentFile.getPath()));
			}
			this.error(e.toString());
			e.printStackTrace();
			return false;
		}
		logger.info("Parsing document structure");
		for(String line : orgLines) {
			if(!processLine(line)) {
				return false;
			}
			lineNumber++;
		}
		logger.info("Building pages");
		if(!buildDocument()) return false;
		logger.info("Loading completed in {}ms.", System.currentTimeMillis() - startTime);
		if(documentIn.pages.size() == 0) {
			this.warning(I18n.format("manual.msg.document_empty"));
			return false;
		}
		return true;
	}
	
	private void appendErrText(String str) {
		appendText("§c§l" + I18n.format("manual.text.error") + ":" + str);
	}
	
	private void appendText(String str) {
		char styleMark = '§';
		int index = 0;
		boolean colored = false;
		char currentColor = 'f';
		boolean random = false;			// k
		boolean bold = false;			// l
		boolean strikethrough = false;	// m
		boolean underlined = false;		// n
		boolean italic = false;			// o
		String allStyles = "0123456789abcdefklmnor";
		String colors = "0123456789abcdef";
		String tmp = new String(str);
		String output = "";
		for(int i = 0; i < str.length(); i++) {
			index = tmp.indexOf(styleMark);
			if(index == -1) {	// 没有任何样式代码
				if(tmp.isEmpty()) {	// 所有字符都处理完毕了，直接结束
					break;
				} else {
					for(char c : tmp.toCharArray()) {	// 给剩余字符附上样式并结束
						if(colored) {
							output += styleMark;
							output += currentColor;
						}
						if(random) {
							output += styleMark;
							output += 'k';
						}
						if(bold) {
							output += styleMark;
							output += 'l';
						}
						if(strikethrough) {
							output += styleMark;
							output += 'm';
						}
						if(underlined) {
							output += styleMark;
							output += 'n';
						}
						if(italic) {
							output += styleMark;
							output += 'o';
						}
						output += c;
					}
					break;
				}
			}
			if(index != 0) {	// 有样式代码但是不在开头，要先把前面的字符附上已有的样式
				for(char c : tmp.toCharArray()) {
					if(c == styleMark) break;
					if(colored) {
						output += styleMark;
						output += currentColor;
					}
					if(random) {
						output += styleMark;
						output += 'k';
					}
					if(bold) {
						output += styleMark;
						output += 'l';
					}
					if(strikethrough) {
						output += styleMark;
						output += 'm';
					}
					if(underlined) {
						output += styleMark;
						output += 'n';
					}
					if(italic) {
						output += styleMark;
						output += 'o';
					}
					output += c;
					tmp = tmp.substring(1);
				}
				continue;
			}
			if(index == tmp.length()) break;
			index++;
			if(allStyles.indexOf(tmp.charAt(index)) == -1) {	// 样式代码无效，保持原样
				output += tmp.substring(0, 2);
				tmp = tmp.substring(2);
				continue;
			}
			// 正式开始处理样式
			// 字符的转移放到最后
			if(colors.indexOf(tmp.charAt(index)) != -1) {	// 颜色代码
				colored = true;
				currentColor = tmp.charAt(index);
			} else if(tmp.charAt(index) == 'r'){	// §r
				colored = false;
				currentColor = 'f';
				random = false;
				bold = false;
				strikethrough = false;
				underlined = false;
				italic = false;
			} else {	// 其他样式代码
				switch(tmp.charAt(index)) {
					case 'k': random = true; break;
					case 'l': bold = true; break;
					case 'm': strikethrough = true; break;
					case 'n': underlined = true; break;
					case 'o': italic = true; break;
					default: break;	// 不可能执行
				}
			}
			// 转移字符
			tmp = tmp.substring(2);
		}

		List<String> lines = trimStringToWidthWithoutStyleMarks(output, this.width);
		for(String line : lines) {
			elements.add(new Element.TextLine(line));
		}
	}
	
	/**
	 * 将一个字符串按一定的宽度限制分成若干行(忽略样式标识)
	 * @param 输入的字符串
	 * @return 一个List，每行一个字符串
	 */
	private List<String> trimStringToWidthWithoutStyleMarks(String str, int limit) {
		List<String> lines = new ArrayList<String>();
		String line = "";
		for(char c : str.toCharArray()) {
			if(GLUtils.getStringWidth(line) >= limit) {
				lines.add(new String(line));
				line = "";
			}
			line += c;
		}
		if(!line.isEmpty()) {
			lines.add(new String(line));
		}
		return lines;
	}
	
	private boolean processLine(String line) {
		try {
			if(line.startsWith("`!") && line.endsWith("`")) {
				String statement = line.substring(2, line.length() - 1);	// 索引从0开始，并且要去掉最后一个字符，所以还要减1
				if(statement.indexOf(' ') == -1) {	// statement with no arguments
					if(tokenMap.containsKey(statement)) {
						if(!tokenMap.get(statement).test(new String[0])) {
							appendErrText(line);
						}
					} else {
						this.warning(I18n.format("manual.error.invalid_token", statement));
					}
				} else {
					String token = statement.substring(0, statement.indexOf(' '));
					String args = statement.substring(statement.indexOf(' ') + 1);
					if(tokenMap.containsKey(token)) {
						if(args.indexOf(' ') == -1) {	// statement with only one argument
							if(!tokenMap.get(token).test(new String[] {args})) {
								appendErrText(line);
							}
						} else {	// statement with multiple arguments
							String[] argArray = args.split(" ");
							if(!tokenMap.get(token).test(argArray)) {
								appendErrText(line);
							}
						}
					} else {
						this.warning(I18n.format("manual.error.invalid_token", statement));
					}
					
				}
			} else {
				appendText(line);
			}
		} catch(Exception e) {
			logger.error("An error occurred while processing line \"{}\" (Line {})", line, lineNumber);
			this.error(I18n.format("manual.error.error_processing_statement"));
			this.error(e.toString());
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private boolean buildDocument() {
		logger.info("buildDocument: Loaded {} elements.", elements.size());
		int currentY = 0;
		List<Element> currentElements = new ArrayList<Element>();
		for(Element element : elements) {
			if(element.getHeight() > this.height) {
				this.error(I18n.format("manual.error.element_too_large", this.width, this.height));
				return false;
			}
			if(element instanceof Element.EndOfPage) {
				pages.add(new Page(new ArrayList<Element>(currentElements)));
				currentElements.clear();
				currentY = 0;
				continue;
			}
			if(currentY + element.getHeight() >= this.height) {
				pages.add(new Page(new ArrayList<Element>(currentElements)));
				currentElements.clear();
				currentY = 0;
				currentElements.add(element);
				currentY += element.getHeight();
			} else {
				currentElements.add(element);
				currentY += element.getHeight();
			}
		}
		if(!currentElements.isEmpty()) {
			pages.add(new Page(new ArrayList<Element>(currentElements)));
			currentElements.clear();
		}
		documentIn = new Document(new ArrayList<Page>(pages));
		logger.info("buildDocument: Built {} pages.", pages.size());
		return true;
	}
	
	/* {text:string} */
	private boolean addCenteredText(String[] args) {
		if(args.length != 1) {
			this.warning(I18n.format("manual.error.illegal_arguments", 1));
			return false;
		}
		elements.add(new Element.CenteredText(args[0]));
		return true;
	}
	
	private boolean addEndOfPageMark(String[] args) {
		elements.add(new Element.EndOfPage());
		return true;
	}
	
	/* {path:string} */
	private boolean addImage(String[] args) {
		if(args.length != 1) {
			this.warning(I18n.format("manual.error.illegal_arguments", 1));
			return false;
		}
		try {
			if(!isDocumentExternal) {
				elements.add(new Element.Image(new ResourceLocation(args[0]), this.width, this.height));
			} else {
				ResourceLocation location = new ResourceLocation(args[0]);
				File file = new File(Minecraft.getMinecraft().mcDataDir.getAbsolutePath() + File.separator + DocumentRenderer.localManualPath + location.getResourceDomain() + File.separator + location.getResourcePath());
				if(file.exists()) {
					elements.add(new Element.Image(file, this.width, this.height));
				} else {
					logger.error("File not found: {}", file.getAbsolutePath());
					throw new FileNotFoundException(String.format("File not found: %s", file.getAbsolutePath()));
				}
			}
		} catch(Exception e) {
			this.warning(I18n.format("manual.error.syntax_error"));
			this.warning(e.toString());
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/* {x1:int, y1:int, x2:int, y2:int, width:int, color:string[#XXXXXX]} */
	private boolean addLine(String[] args) {
		if(args.length != 6) {
			this.warning(I18n.format("manual.error.illegal_arguments", 6));
			return false;
		}
		try {
			String hex = args[5];
			ColorRGB rgb = GLUtils.hexStringToRGB(hex);
			int x1 = Integer.parseInt(args[0]);
			int y1 = Integer.parseInt(args[1]);
			int x2 = Integer.parseInt(args[2]);
			int y2 = Integer.parseInt(args[3]);
			int width = Integer.parseInt(args[4]);
			int r = rgb.getR();
			int g = rgb.getG();
			int b = rgb.getB();
			elements.add(new Element.Line(x1, y1, x2, y2, width, r, g, b));
		} catch(NumberFormatException e) {
			this.warning(I18n.format("manual.error.syntax_error"));
			this.warning(e.toString());
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/* {text:string, link:string[domain:path]} */
	private boolean addHyperLink(String[] args) {
		if(args.length != 2) {
			this.warning(I18n.format("manual.error.illegal_arguments", 2));
			return false;
		}
		try {
			if(!isDocumentExternal) {
				Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(args[1]));	// 判断文档是否存在，不存在会抛出异常
				elements.add(new Element.HyperLink(args[0], args[1], this.isDocumentExternal));
			} else {
				ResourceLocation location = new ResourceLocation(args[1]);
				String link = Minecraft.getMinecraft().mcDataDir.getAbsolutePath() + File.separator + DocumentRenderer.localManualPath + location.getResourceDomain() + File.separator + location.getResourcePath();
				File document = new File(link);
				if(!document.exists()) throw new FileNotFoundException("Can't find document file" + args[0]);
				elements.add(new Element.HyperLink(args[0], link, this.isDocumentExternal));
			}
		} catch(Exception e) {
			this.error(I18n.format("manual.error.cant_read_document_file", args[0]));
			this.error(e.toString());
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public int getPages() {
		if(documentIn == null)
			return 0;
		return documentIn.pages.size();
	}

	public void draw(int pageIndex, DocumentSide side, int offsetX, int offsetY) {
		if(!available) {
			if(side == DocumentSide.LEFT) {
				GLUtils.drawString(I18n.format("manual.error"), offsetX + org1X, offsetY + org1Y, Colors.DEFAULT_BLACK);
			}
			return;
		}
		int originX;
		int originY;
		switch(side) {
		case LEFT:
			originX = offsetX + org1X;
			originY = offsetY + org1Y;
			break;
		case RIGHT:
			originX = offsetX + org2X;
			originY = offsetY + org2Y;
			break;
		default:	// never reaches, but it is necessary
			originX = 0;
			originY = 0;
			break;
		}
		this.documentIn.pages.get(pageIndex).draw(originX, originY, this);
		GLUtils.drawCenteredString(String.valueOf(pageIndex + 1), originX + width / 2, originY + height + 5, Colors.DEFAULT_BLACK);
	}
	
	public void onMouseClick(int mouseX, int mouseY, int mouseButton, int pageIndex) {
		if(this.pages == null)
			return;
		if(this.pages.size() == 0)
			return;
		for(Element e : this.pages.get(pageIndex).elements){
			e.onClicked(this);
		}
	}
	
	private void error(String msg) {
		if(!this.isDocumentExternal) {
			Minecraft.getMinecraft().player.sendStatusMessage(new TextComponentString(I18n.format("manual.msg.error", this.documentLocation.toString(), this.lineNumber, msg)), false);
			this.logger.error(I18n.format("manual.msg.error", this.documentLocation.toString(), this.lineNumber, msg));
		} else {
			Minecraft.getMinecraft().player.sendStatusMessage(new TextComponentString(I18n.format("manual.msg.error", this.documentFile.getPath(), this.lineNumber, msg)), false);
			this.logger.error(I18n.format("manual.msg.error", this.documentFile.getPath(), this.lineNumber, msg));
		}
	}
	
	private void warning(String msg) {
		if(!this.isDocumentExternal) {
			Minecraft.getMinecraft().player.sendStatusMessage(new TextComponentString(I18n.format("manual.msg.warning", this.documentLocation.toString(), this.lineNumber, msg)), false);
			this.logger.error(I18n.format("manual.msg.warning", this.documentLocation.toString(), this.lineNumber, msg));
		} else {
			Minecraft.getMinecraft().player.sendStatusMessage(new TextComponentString(I18n.format("manual.msg.warning", this.documentFile.getPath(), this.lineNumber, msg)), false);
			this.logger.error(I18n.format("manual.msg.warning", this.documentFile.getPath(), this.lineNumber, msg));
		}
	}
	
	private static final class Document {
		
		private final List<Page> pages;

		public Document(List<Page> pages) {
			this.pages = pages;
		}
		
		public void remove() {
			for(Page p : pages) {
				p.remove();
			}
		}
	}
	
	private static final class Page {
		
		private final List<Element> elements;

		public Page(List<Element> elements) {
			this.elements = elements;
		}
		
		public void draw(int orgX, int orgY, DocumentRenderer renderer) {
			int currentX = orgX;
			int currentY = orgY;
			for(Element element : elements) {
				GLUtils.resetState();
				element.draw(currentX, currentY, renderer);
				currentY += element.getHeight();
			}
		}
		
		public void remove() {
			for(Element e : elements) {
				e.remove();
			}
		}
	}
	
	public static abstract class Element {
		
		protected static enum Type {
			END_OF_PAGE, TEXTLINE, CENTERED_TEXT, IMAGE, LINE, ITEM, CRAFTING, HYPERLINK
		}
		
		protected abstract Type getType();

		protected abstract int getHeight();
		
		protected abstract void draw(int x, int y, DocumentRenderer renderer);
		
		protected void remove() {
			
		}
		
		protected void onClicked(DocumentRenderer renderer) {
			
		}
		
		protected static class EndOfPage extends Element {

			@Override
			protected Type getType() {
				return Type.END_OF_PAGE;
			}

			@Override
			protected int getHeight() {
				return 0;
			}

			@Override
			protected void draw(int x, int y, DocumentRenderer renderer) {
				
			}
		}
		
		protected static class TextLine extends Element {
			
			private String str;
			
			private TextLine(String str) {
				this.str = str;
			}

			@Override
			protected Type getType() {
				return Type.TEXTLINE;
			}

			@Override
			protected int getHeight() {
				return 10;
			}

			@Override
			protected void draw(int x, int y, DocumentRenderer renderer) {
				GLUtils.drawString(str, x, y, Colors.DEFAULT_BLACK);
			}
		}
		
		protected static class CenteredText extends Element {
			
			private String str;
			
			private CenteredText(String str) {
				this.str = str;
			}

			@Override
			protected Type getType() {
				return Type.CENTERED_TEXT;
			}

			@Override
			protected int getHeight() {
				return 10;
			}

			@Override
			protected void draw(int x, int y, DocumentRenderer renderer) {
				GLUtils.drawCenteredString(str, x + renderer.width / 2, y, Colors.DEFAULT_BLACK);
			}
		}
		
		protected static class Image extends Element {
			
			private final BufferedImage image;
			private final int width;
			private final int height;
			private final int realWidth;
			private final int realHeight;
			private final int glTextureId;
			
			private Image(ResourceLocation location, int windowWidth, int windowHeight) throws IOException {
				this.image = TextureUtil.readBufferedImage(Minecraft.getMinecraft().getResourceManager().getResource(location).getInputStream());
				this.width = this.image.getWidth();
				this.height = this.image.getHeight();
				Size2i size = new Size2i(this.width, this.height);
				size.scaleToSize(windowWidth, windowHeight);
				this.realWidth = size.getWidth();
				this.realHeight = size.getHeight();
				this.glTextureId = GLUtils.loadTexture(this.image);
			}
			
			private Image(File image, int windowWidth, int windowHeight) throws FileNotFoundException, IOException {
				this.image = TextureUtil.readBufferedImage(new FileInputStream(image));
				this.width = this.image.getWidth();
				this.height = this.image.getHeight();
				Size2i size = new Size2i(this.width, this.height);
				size.scaleToSize(windowWidth, windowHeight);
				this.realWidth = size.getWidth();
				this.realHeight = size.getHeight();
				this.glTextureId = GLUtils.loadTexture(this.image);
			}

			@Override
			protected Type getType() {
				return Type.IMAGE;
			}

			@Override
			protected int getHeight() {
				return realHeight;
			}

			@Override
			protected void draw(int x, int y, DocumentRenderer renderer) {
				GLUtils.bindTexture(this.glTextureId);
				if(width == realWidth && height == realHeight) {
					Gui.drawModalRectWithCustomSizedTexture(x + renderer.width / 2 - width / 2, y, 0, 0, width, height, width, height);
				} else {
					Gui.drawScaledCustomSizeModalRect(x + renderer.width / 2 - realWidth / 2, y, 0, 0, width, height, realWidth, realHeight, width, height);
				}
			}
			
			@Override
			protected void remove() {
				GLUtils.deleteTexture(glTextureId);
			}
		}
		
		protected static class Line extends Element {
			
			private int x1;
			private int y1;
			private int x2;
			private int y2;
			private int width;
			private int r;
			private int g;
			private int b;
			
			private Line(int x1, int y1, int x2, int y2, int width, int r, int g, int b) {
				this.x1 = x1;
				this.y1 = y1;
				this.x2 = x2;
				this.y2 = y2;
				this.width = width;
				this.r = r;
				this.g = g;
				this.b = b;
			}

			@Override
			protected Type getType() {
				return Type.LINE;
			}

			@Override
			protected int getHeight() {
				return 10;
			}

			@Override
			protected void draw(int x, int y, DocumentRenderer renderer) {
				BufferBuilder bb = renderer.bufferBuilder;
				Tessellator tesr = renderer.tessellator;
				GLUtils.glLineWidth(width);
				GLUtils.enableBlend();
				GLUtils.disableTexture2D();
				bb.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
				bb.pos(x + x1, y + y1, 0).color(r, g, b, 255).endVertex();
				bb.pos(x + x2, y + y2, 0).color(r, g, b, 255).endVertex();
				tesr.draw();
				GLUtils.enableTexture2D();
				GLUtils.disableBlend();
			}
		}
		
		public static class HyperLink extends Element {

			private final boolean isExternal;
			private final String text;
			private final String link;
			private int x = 0;
			private int y = 0;
			
			private HyperLink(String text, String link, boolean isExternal) {
				this.isExternal = isExternal;
				this.link = link;
				this.text = text;
			}
			
			@Override
			protected Type getType() {
				return Type.HYPERLINK;
			}

			@Override
			protected int getHeight() {
				return 10;
			}

			@Override
			protected void draw(int x, int y, DocumentRenderer renderer) {
				this.x = x;
				this.y = y;
				int mouseX = Utils.getMouseX();
				int mouseY = Utils.getMouseY();
				boolean mouseOn = mouseX >= x && mouseX <= x + renderer.width && mouseY >= y && mouseY <= y + 10;
				if(!mouseOn) {
					GLUtils.drawCenteredString(this.text, x + renderer.width / 2, y, Colors.DEFAULT_BLACK);
				} else {
					GLUtils.drawCenteredString(this.text, x + renderer.width / 2, y, 0x00FF00);
					GLUtils.drawSimpleToolTip(Lists.newArrayList(text, I18n.format("manual.hyperlink.mouseon")));
				}
			}

			@Override
			protected void onClicked(DocumentRenderer renderer) {
				int mouseX = Utils.getMouseX();
				int mouseY = Utils.getMouseY();
				if(mouseX >= x && mouseX <= x + renderer.width && mouseY >= y && mouseY <= y + 10) {
					renderer.reload(this.isExternal, this.link);
				}
			}
			
		}
		
		public static class Item extends Element {

			private ItemStack stack;
			
			private Item(String data) {
				// TODO Item
			}
			
			@Override
			protected Type getType() {
				return Type.ITEM;
			}

			@Override
			protected int getHeight() {
				return 20;
			}

			@Override
			protected void draw(int x, int y, DocumentRenderer renderer) {
				// TODO 自动生成的方法存根
				
			}
		}
		
		protected static class Crafting extends Element {

			@Override
			protected Type getType() {
				// TODO Crafting
				return null;
			}

			@Override
			protected int getHeight() {
				// TODO Crafting
				return 0;
			}
			// TODO

			@Override
			protected void draw(int x, int y, DocumentRenderer renderer) {
				// TODO 自动生成的方法存根
				
			}
		}
		
	}
}
