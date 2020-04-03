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
import com.gonggongjohn.eok.utils.GLUtils.ColorRGB;
import com.gonggongjohn.eok.utils.datatypes.Size2i;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
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
	private boolean err;
	public final boolean isDocumentExternal;
	
	public DocumentRenderer(int org1X, int org1Y, int org2X, int org2Y, int width, int height, String documentPath) {
		isDocumentExternal = false;
		this.org1X = org1X;
		this.org1Y = org1Y;
		this.org2X = org2X;
		this.org2Y = org2Y;
		this.width = width;
		this.height = height;
		logger = EOK.getLogger();
		this.bufferBuilder = Tessellator.getInstance().getBuffer();
		this.documentLocation = new ResourceLocation(documentPath);
		this.tokenMap = new HashMap<String, Predicate<String[]>>();
		init();
		if(!(available = read())) {
			Minecraft.getMinecraft().player.sendStatusMessage(new TextComponentString(I18n.format("message.documentrenderer.error")), false);
		} else if(err){
			Minecraft.getMinecraft().player.sendStatusMessage(new TextComponentString(I18n.format("message.ducumentrenderer.syntaxerror")), false);
		}
	}
	
	public DocumentRenderer(int org1X, int org1Y, int org2X, int org2Y, int width, int height, File documentFile) {
		isDocumentExternal = true;
		this.org1X = org1X;
		this.org1Y = org1Y;
		this.org2X = org2X;
		this.org2Y = org2Y;
		this.width = width;
		this.height = height;
		logger = EOK.getLogger();
		this.bufferBuilder = Tessellator.getInstance().getBuffer();
		this.documentFile = documentFile;
		this.tokenMap = new HashMap<String, Predicate<String[]>>();
		init();
		if(!(available = read())) {
			Minecraft.getMinecraft().player.sendStatusMessage(new TextComponentString(I18n.format("message.documentrenderer.error")), false);
		} else if(err){
			Minecraft.getMinecraft().player.sendStatusMessage(new TextComponentString(I18n.format("message.ducumentrenderer.syntaxerror")), false);
		}
	}
	
	public boolean isAvailable() {
		return available;
	}
	
	private void init() {
		tokenMap.put("center", this::addCenteredText);
		tokenMap.put("end_of_page", this::addEndOfPageMark);
		tokenMap.put("comment", (s) -> true);
		tokenMap.put("image", this::addImage);
		tokenMap.put("drawline", this::addLine);
	}
	
	public static enum Side {
		LEFT, RIGHT
	}
	
	public BufferedReader getResource(ResourceLocation location) throws UnsupportedEncodingException, IOException {
		return new BufferedReader(new InputStreamReader(DataUtils.getResource(location), "UTF-8"));
	}
	
	public boolean read() {
		err = false;
		pages.clear();
		elements.clear();
		available = true;
		long startTime = System.currentTimeMillis();
		if(this.width < 20 || this.height < 20) {
			logger.error("Text area is too small! It must be larger than 20*20 pixels.");
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
				logger.error("Can't read document file \"{}:{}\"", documentLocation.getResourceDomain(), documentLocation.getResourcePath());
			} else {
				logger.error("Can't read document file \"{}\"", documentFile.getAbsolutePath());
			}
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
			logger.warn("Wait...WTF? 0 pages?? What's wrong???");
			return false;
		}
		return true;
	}
	
	private void appendErrText(String str) {
		appendText("§c§lERROR:" + str);
		err = true;
	}
	
	private void appendText(String str) {
		String line = "";
		char[] chars = str.toCharArray();
		for(char c : chars) {
			if(GLUtils.getStringWidth(line) >= this.width) {
				elements.add(new Element.TextLine(new String(line)));
				line = "";
				line += c;
			} else {
				line += c;
			}
		}
		elements.add(new Element.TextLine(line));
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
						logger.warn("Invalid token {} at line {}.", statement, lineNumber);
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
						logger.warn("Invalid token {} at line {}.", token, lineNumber);
					}
					
				}
			} else {
				appendText(line);
			}
		} catch(Exception e) {
			logger.error("An error occurred while processing line \"{}\" (Line {})", line, lineNumber);
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
				logger.error("This element's size exceeds the limit({}*{})! Loading won't continue.", this.width, this.height);
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
			logger.warn("Illegal Arguments: Line {}: There must be only one argument.");
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
			logger.warn("Illegal Arguments: Line {}: There must be only 1 arguments.");
			return false;
		}
		try {
			if(!isDocumentExternal) {
				elements.add(new Element.Image(new ResourceLocation(args[0]), this.width, this.height));
			} else {
				if(new File(Minecraft.getMinecraft().mcDataDir.getAbsolutePath() + File.separator + args[0]).exists()) {
					elements.add(new Element.Image(new File(Minecraft.getMinecraft().mcDataDir.getAbsolutePath() + File.separator + args[0]), this.width, this.height));
				} else {
					logger.error("File not found: {}", Minecraft.getMinecraft().mcDataDir.getAbsolutePath() + File.separator + args[0]);
					throw new FileNotFoundException(String.format("File not found: %s", Minecraft.getMinecraft().mcDataDir.getAbsolutePath() + File.separator + args[0]));
				}
			}
		} catch(Exception e) {
			logger.warn("Syntax error at line {}.", lineNumber);
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/* {x1:int, y1:int, x2:int, y2:int, width:int, color:string[#XXXXXX]} */
	private boolean addLine(String[] args) {
		if(args.length != 6) {
			logger.warn("Illegal Arguments: Line {}: There must be only 6 arguments.");
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
			logger.warn("Syntax error at line {}: The input is not a number!", lineNumber);
			logger.warn("\t{}", (e.getMessage() != null) ? e.getMessage() : "");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public int getPages() {
		return documentIn.pages.size();
	}

	public void draw(int pageIndex, Side side, int offsetX, int offsetY) {
		if(!available) {
			if(side == Side.LEFT) {
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
	
	private static final class Document {
		
		private final List<Page> pages;

		public Document(List<Page> pages) {
			this.pages = pages;
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
	}
	
	private static abstract class Element {
		
		private static enum Type {
			END_OF_PAGE, TEXTLINE, CENTERED_TEXT, IMAGE, LINE, ITEM, CRAFTING
		}
		
		protected abstract Type getType();
		
		protected abstract int getHeight();
		
		protected abstract void draw(int x, int y, DocumentRenderer renderer);
		
		private static class EndOfPage extends Element {

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
		
		private static class TextLine extends Element {
			
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
		
		private static class CenteredText extends Element {
			
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
		
		private static class Image extends Element {
			
			private BufferedImage image;
			private int width;
			private int height;
			private int realWidth;
			private int realHeight;
			
			private Image(ResourceLocation location, int windowWidth, int windowHeight) throws IOException {
				this.image = TextureUtil.readBufferedImage(Minecraft.getMinecraft().getResourceManager().getResource(location).getInputStream());
				this.width = this.image.getWidth();
				this.height = this.image.getHeight();
				Size2i size = new Size2i(this.width, this.height);
				size.scaleToSize(windowWidth, windowHeight);
				this.realWidth = size.getWidth();
				this.realHeight = size.getHeight();
			}
			
			private Image(File image, int windowWidth, int windowHeight) throws FileNotFoundException, IOException {
				this.image = TextureUtil.readBufferedImage(new FileInputStream(image));
				this.width = this.image.getWidth();
				this.height = this.image.getHeight();
				Size2i size = new Size2i(this.width, this.height);
				size.scaleToSize(windowWidth, windowHeight);
				this.realWidth = size.getWidth();
				this.realHeight = size.getHeight();
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
				int textureId;
				try {
					textureId = GLUtils.bindTexture(image);
				} catch(Exception e) {
					e.printStackTrace();
					return;
				}
				if(width == realWidth && height == realHeight) {
					Gui.drawModalRectWithCustomSizedTexture(x + renderer.width / 2 - width / 2, y, 0, 0, width, height, width, height);
				} else {
					Gui.drawScaledCustomSizeModalRect(x + renderer.width / 2 - realWidth / 2, y, 0, 0, width, height, realWidth, realHeight, width, height);
				}
				//GLUtils.deleteTexture(textureId);
			}
		}
		
		private static class Line extends Element {
			
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
				return 0;
			}

			@Override
			protected void draw(int x, int y, DocumentRenderer renderer) {
				BufferBuilder bb = renderer.bufferBuilder;
				GLUtils.glLineWidth(width);
				bb.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
				bb.pos(x1, y1, 0).color(r, g, b, 255).endVertex();
				bb.pos(x2, y2, 0).color(r, g, b, 255).endVertex();
				bb.finishDrawing();
			}
		}
		
		private static class Item extends Element {

			@Override
			protected Type getType() {
				// TODO Item
				return null;
			}

			@Override
			protected int getHeight() {
				// TODO Item
				return 0;
			}
			// TODO

			@Override
			protected void draw(int x, int y, DocumentRenderer renderer) {
				// TODO 自动生成的方法存根
				
			}
		}
		
		private static class Crafting extends Element {

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
