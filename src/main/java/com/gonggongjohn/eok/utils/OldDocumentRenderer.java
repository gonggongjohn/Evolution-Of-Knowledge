package com.gonggongjohn.eok.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Point;

import com.gonggongjohn.eok.EOK;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

@Deprecated
public class OldDocumentRenderer {
	private Point origin1;
	private Point origin2;
	private int width;
	private int height;
	private ResourceLocation documentLoc;
	private Document document;
	private boolean ready;
	private int offsetX;
	private int offsetY;
	private org.apache.logging.log4j.Logger logger = EOK.getLogger();
	private FontRenderer fontRenderer;

	private char[] buffer;
	private int readerIndex;
	//private int currentY;
	private int currentLine;
	private int currentColumn;
	private String sCurrentLine = "";
	private List<Page> pages = new ArrayList<Page>();
	private List<Element> unPagedElements = new ArrayList<Element>();

	private static Map<String, Predicate<String[]>> tokenMap = new HashMap<String, Predicate<String[]>>();

	public OldDocumentRenderer(int origin1X, int origin1Y, int origin2X, int origin2Y, int width, int height,
			ResourceLocation documentLocation) {
		this.init();
		fontRenderer = Minecraft.getMinecraft().fontRenderer;
		origin1 = new Point(origin1X, origin1Y);
		origin2 = new Point(origin2X, origin2Y);
		this.width = width;
		this.height = height;
		this.documentLoc = documentLocation;
		ready = this.read();
	}

	private void init() {
		tokenMap.put("textline", this::addTextLine);
		tokenMap.put("image", this::addImage);
	}

	public void draw(int index, Side side, int offsetX, int offsetY) {
		if (ready) {
			this.offsetX = offsetX;
			this.offsetY = offsetY;
			document.draw(index, side);
		}
	}

	public enum Side {
		
		LEFT(false), 
		RIGHT(true);

		private Side(boolean a) {

		}

		public static Side get(boolean a) {
			return a ? RIGHT : LEFT;
		}
	}

	public int getPages() {
		return document.pageList.size();
	}

	private boolean read() {
		/*
		 * InputStream stream; try { stream =
		 * Minecraft.getMinecraft().getResourceManager().getResource(documentLoc).
		 * getInputStream(); } catch (IOException e) { e.printStackTrace(); return
		 * false; } try { reader = new InputStreamReader(stream, "UTF-8"); } catch
		 * (UnsupportedEncodingException e) { e.printStackTrace(); return false; }
		 * List<Document.Page> pageList = new ArrayList<Document.Page>();
		 * List<Document.Element> elements = new
		 * ArrayList<Document.Element>(); List<Integer> chars = new
		 * ArrayList<Integer>(); while(true) { int c; try { c = reader.read(); } catch
		 * (IOException e) { e.printStackTrace(); return false; } if(c == -1) { break; }
		 * chars.add(c); } chars.add(-1);
		 * 
		 * int readerIndex = 0; int currentY = 0; int lines = 1; // WARNING: This is the
		 * current line in the document FILE, isn't that displays on the SCREEN. String
		 * currentLine = "";
		 * 
		 * try { while(chars.get(readerIndex) != -1) { if(chars.get(readerIndex) == '`')
		 * { if(chars.get(readerIndex + 1) == '!') { readerIndex += 2; String m = "";
		 * while(chars.get(readerIndex) != ' ' && chars.get(readerIndex) != '`') { m +=
		 * (char)chars.get(readerIndex).intValue(); readerIndex++; } readerIndex++;
		 * if(m.equalsIgnoreCase("image")) { String img = ""; String _widths = "";
		 * String _heights = ""; while(chars.get(readerIndex) != ',') { img +=
		 * (char)chars.get(readerIndex).intValue(); readerIndex++; } readerIndex++;
		 * while(chars.get(readerIndex) != ',') { _widths +=
		 * (char)chars.get(readerIndex).intValue(); readerIndex++; } readerIndex++;
		 * while(chars.get(readerIndex) != '`') { _heights +=
		 * (char)chars.get(readerIndex).intValue(); readerIndex++; } readerIndex++;
		 * ResourceLocation location = new ResourceLocation(EOK.MODID + ":" + img);
		 * if(currentY + Integer.parseInt(_heights) + 10 > this.height) {
		 * if(Integer.parseInt(_heights) + 10 > this.height) {
		 * EOK.getLogger().error("Image {} is too large.", location.toString());
		 * continue; } pageList.add(new Document.Page(new
		 * ArrayList<Document.Element>(elements))); elements.clear(); currentY = 0;
		 * } elements.add(new Document.Element.Image(location,
		 * Integer.parseInt(_widths), Integer.parseInt(_heights))); currentY +=
		 * Integer.parseInt(_heights);
		 * 
		 * } else if(m.equalsIgnoreCase("end_of_page")) { pageList.add(new
		 * Document.Page(new ArrayList<Document.Element>(elements)));
		 * elements.clear(); currentY = 0; } else
		 * if(m.equalsIgnoreCase("centered_text")) { String s = "";
		 * while(chars.get(readerIndex) != '`') { s +=
		 * (char)chars.get(readerIndex).intValue(); readerIndex++; } if(currentY + 20 >=
		 * this.height) { pageList.add(new Document.Page(new
		 * ArrayList<Document.Element>(elements))); elements.clear();
		 * if(!currentLine.isEmpty()) { elements.add(new
		 * Document.Element.TextLine(new String(currentLine))); currentLine = "";
		 * currentY += 10; } } elements.add(new Document.Element.CenteredText(s));
		 * currentY += 10; } else if(m.equalsIgnoreCase("line")) { String x1s = "";
		 * String y1s = ""; String x2s = ""; String y2s = ""; String widths = ""; String
		 * hex = ""; while(chars.get(readerIndex) != ',') { x1s +=
		 * (char)chars.get(readerIndex).intValue(); readerIndex++; } readerIndex++;
		 * while(chars.get(readerIndex) != ',') { y1s +=
		 * (char)chars.get(readerIndex).intValue(); readerIndex++; } readerIndex++;
		 * while(chars.get(readerIndex) != ',') { x2s +=
		 * (char)chars.get(readerIndex).intValue(); readerIndex++; } readerIndex++;
		 * while(chars.get(readerIndex) != ',') { y2s +=
		 * (char)chars.get(readerIndex).intValue(); readerIndex++; } readerIndex++;
		 * while(chars.get(readerIndex) != ',') { widths +=
		 * (char)chars.get(readerIndex).intValue(); readerIndex++; } readerIndex++;
		 * while(chars.get(readerIndex) != '`') { hex +=
		 * (char)chars.get(readerIndex).intValue(); readerIndex++; } readerIndex++; int
		 * x1, y1, x2, y2, lwidth; int color = 0; x1 = Integer.parseInt(x1s); y1 =
		 * Integer.parseInt(y1s); x2 = Integer.parseInt(x2s); y2 =
		 * Integer.parseInt(y2s); lwidth = Integer.parseInt(widths); hex =
		 * hex.toUpperCase(); for(char c : hex.toCharArray()) { switch(c) { case '#':
		 * continue; case '0': color = color << 4 | 0x00000000; break; case '1': color =
		 * color << 4 | 0x00000001; break; case '2': color = color << 4 | 0x00000002;
		 * break; case '3': color = color << 4 | 0x00000003; break; case '4': color =
		 * color << 4 | 0x00000004; break; case '5': color = color << 4 | 0x00000005;
		 * break; case '6': color = color << 4 | 0x00000006; break; case '7': color =
		 * color << 4 | 0x00000007; break; case '8': color = color << 4 | 0x00000008;
		 * break; case '9': color = color << 4 | 0x00000009; break; case 'A': color =
		 * color << 4 | 0x0000000A; break; case 'B': color = color << 4 | 0x0000000B;
		 * break; case 'C': color = color << 4 | 0x0000000C; break; case 'D': color =
		 * color << 4 | 0x0000000D; break; case 'E': color = color << 4 | 0x0000000E;
		 * break; case 'F': color = color << 4 | 0x0000000F; break; default: break; } }
		 * int r, g, b; r = (color & 0xFF0000) >> 16; g = (color & 0x00FF00) >> 8; b =
		 * (color & 0x0000FF); elements.add(new Document.Element.Line(x1, y1, x2,
		 * y2, lwidth, r, g, b)); } else if(m.equalsIgnoreCase("comment")) {
		 * while(chars.get(readerIndex) != '`') { readerIndex++; } readerIndex++; } else
		 * if(m.equalsIgnoreCase("itemstack")){ InputStream _stream =
		 * this.readResource(chars, readerIndex); ItemStack stack =
		 * this.readItemStack(_stream); } else {
		 * EOK.getLogger().error("Illegal token {} at line {}.", m, lines); }
		 * readerIndex ++; } else {
		 * EOK.getLogger().error("Illegal symbol \"`\" at line {}, delete it.", lines);
		 * readerIndex++; continue; } readerIndex++; lines++; } else
		 * if(chars.get(readerIndex) == '\r') { readerIndex++; } else
		 * if(chars.get(readerIndex) == '\n') {
		 * 
		 * if(currentY + 20 > this.height) { pageList.add(new Document.Page(new
		 * ArrayList<Document.Element>(elements))); elements.clear(); currentY = 0;
		 * } elements.add(new Document.Element.TextLine(new String(currentLine)));
		 * currentLine = ""; currentY += 10; readerIndex ++; lines++; } else { int
		 * strWidth = Minecraft.getMinecraft().fontRenderer.getStringWidth(currentLine);
		 * if(strWidth >= this.width) { if(currentY + 20 > this.height) {
		 * pageList.add(new Document.Page(new
		 * ArrayList<Document.Element>(elements))); elements.clear(); currentY = 0;
		 * } elements.add(new Document.Element.TextLine(new String(currentLine)));
		 * currentLine = ""; currentY += 10; } else { currentLine +=
		 * (char)chars.get(readerIndex).intValue(); readerIndex++; } } } lines--; }
		 * catch(IndexOutOfBoundsException e) { e.printStackTrace(); return false; }
		 * 
		 * 
		 * if(!currentLine.isEmpty()) { if(currentY + 20 >= this.height) {
		 * pageList.add(new Document.Page(new
		 * ArrayList<Document.Element>(elements))); elements.clear(); }
		 * elements.add(new Document.Element.TextLine(new String(currentLine)));
		 * currentLine = ""; currentY += 10; }
		 * 
		 * if(!elements.isEmpty()) { pageList.add(new Document.Page(new
		 * ArrayList<Document.Element>(elements))); elements.clear(); }
		 * 
		 * document = new Document(pageList, this);
		 */
		InputStream stream;
		InputStreamReader streamReader;
		try {
			stream = SerializationUtils.getResource(documentLoc);
		} catch (Exception e) {
			logger.error("Can't read document file {}.", documentLoc.getResourcePath());
			return false;
		}
		try {
			streamReader = new InputStreamReader(stream, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("What happened???!!!!? UTF-8 should always be supported. What did you do?");
			e.printStackTrace();
			return false;
		}
		BufferedReader bufferedReader = new BufferedReader(streamReader);
		int length = 0;
		List<Integer> charList = new ArrayList<Integer>();
		int cc;
		try {
			while (true) {
				cc = bufferedReader.read();
				if (cc == -1)
					break;
				if (cc == '\r')
					continue;
				length++;
				charList.add(cc);
			}
		} catch (IOException e) {
			logger.error("Can't read document.");
			e.printStackTrace();
			return false;
		}
		buffer = new char[length + 1];
		for (int i = 0; i < buffer.length - 1; i++) {
			buffer[i] = (char) charList.get(i).intValue();
		}
		buffer[length] = (char) -1;
		readerIndex = 0;
		currentLine = 1;
		currentColumn = 1;
		//currentY = 0;
		try {
			while (getChar(0) != -1) {
				char c1 = getChar(0);
				char c2 = getChar(1);
				if (c1 == '`' && c2 == '!') {
					offset(2);		
					if (!processToken()) {
						return false;
					}
				} else if (c1 == '`') {
					logger.warn("Syntax error: Illegal \"`\" at line {}, column {}.", currentLine, currentColumn + 1);
					appendChar(c1);
				} else {
					appendChar(c1);
					continue;
				}
			}
		} catch (IndexOutOfBoundsException e) {
			logger.info("Bugs+1!!");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private char readChar() throws IndexOutOfBoundsException {
		char c = buffer[readerIndex];
		if(c == '\n') {
			currentLine++;
			currentColumn = 1;
		} else {
			currentColumn++;
		}
		readerIndex++;
		return c;
	}
	
	private char getChar(int offset) throws IndexOutOfBoundsException {
		return buffer[readerIndex + offset];
	}
	
	private void offset(int offset) throws IndexOutOfBoundsException {
		if(offset < 0) {
			for(int i = 0; i < -offset; i++) {
				switch(getChar(-i)) {
				case '\n':
					currentColumn = 1;
					currentLine--;
					break;
				default:
					currentColumn--;
					break;
				}
			}
		}
		if(offset > 0) {
			for(int i = 0; i < offset; i++) {
				switch(getChar(i)) {
				case '\n':
					currentColumn = 1;
					currentLine++;
					break;
				default:
					currentColumn++;
					break;
				}
			}
		}
		readerIndex += offset;
	}

	private String readToken() throws IndexOutOfBoundsException {
		String token = "";
		while (buffer[readerIndex] != ' ') {
			token += buffer[readerIndex];
			readerIndex++;
		}
		readerIndex++;
		return token;
	}

	private String[] readArguments() throws IndexOutOfBoundsException {
		List<String> argList = new ArrayList<String>();
		String arg = "";
		boolean flag0 = false;
		while (buffer[readerIndex] != '`') {
			while (buffer[readerIndex] != ',') {
				if (buffer[readerIndex] == '`') {
					flag0 = true;
					break;
				}
				arg += buffer[readerIndex];
				if (!flag0) {
					readerIndex++;
				}
			}
			if (!flag0) {
				readerIndex++;
			}
			argList.add(new String(arg));
			arg = "";
		}
		readerIndex++;
		return argList.toArray(new String[0]);
	}

	private boolean processToken() {
		String token = readToken().toLowerCase();
		String[] arguments = readArguments();
		if (tokenMap.containsKey(token)) {
			if (!tokenMap.get(token).test(arguments)) {
				return false;
			}
			;
			return true;
		}
		logger.warn("Invalid token {}.", token);
		return false;
	}
	
	private void processPaging() {
		
	}

	private void appendChar(char s) {
		if (s == '\n') {
			unPagedElements.add(new Element.TextLine(new String(sCurrentLine)));
		} else {
			sCurrentLine += s;
		}
	}

	private boolean addTextLine(String[] args) {
		if (args.length == 1) {
			unPagedElements.add(new Element.TextLine(args[0]));
			return true;
		}
		logger.warn("Error in line {} : There should only be one argument.", currentLine);
		return false;
	}

	private boolean addImage(String[] args) {
		if(args.length == 3) {
			String sPath = args[0];
			String sW = args[1];
			String sH = args[2];
			ResourceLocation location;
			int w;
			int h;
			try {
				location = new ResourceLocation(sPath);
				w = Integer.parseInt(sW);
				h = Integer.parseInt(sH);
			} catch(Exception e) {
				logger.warn("Error in line {} : Illegal arguments.", currentLine);
				e.printStackTrace();
				return false;
			}
			unPagedElements.add(new Element.Image(location, w, h));
			return true;
		}
		logger.warn("Error in line {} : There should only be 3 arguments.", currentLine);
		return false;
	}
	
	private boolean addCenteredText(String[] args) {
		if(args.length == 1) {
			Element.CenteredText tex = new Element.CenteredText(args[0]);
			unPagedElements.add(tex);
			return true;
		}
		logger.warn("Error in line {} : There should only be one argument.", currentLine);
		return false;
	}
	
	private boolean addLine(String[] args) {
		if(args.length == 8) {
			int v0x;
			int v0y;
			int v1x;
			int v1y;
			int width;
			int r, g, b;
			try {
				v0x = Integer.parseInt(args[0]);
				v0y = Integer.parseInt(args[1]);
				v1x = Integer.parseInt(args[2]);
				v1y = Integer.parseInt(args[3]);
				width = Integer.parseInt(args[4]);
				r = Integer.parseInt(args[5]);
				g = Integer.parseInt(args[6]);
				b = Integer.parseInt(args[7]);
			} catch(Exception e) {
				logger.warn("Error in line {} : Illegal arguments.", currentLine);
				e.printStackTrace();
				return false;
			}
			Element.Line line = new Element.Line(v0x, v0y, v1x, v1y, width, r, g, b);
			unPagedElements.add(line);
			return true;
		}
		logger.warn("Error in line {} : There should only be 8 arguments.", currentLine);
		return false;
	}
/*
	private ItemStack readItemStack(InputStream stream) {
		if (stream == null) {
			EOK.getLogger().error("Can't read NBT data.");
			(new NullPointerException("An error occurred while reading NBT data")).printStackTrace();
			return null;
		}
		NBTTagCompound compound;
		try {
			compound = CompressedStreamTools.readCompressed(stream);
		} catch (IOException e) {
			EOK.getLogger().error("Can't read NBT data.");
			e.printStackTrace();
			return null;
		}

		return null;
	}
*/
	private static class Document {

		private List<Page> pageList;
		private OldDocumentRenderer renderer;

		public Document(List<Page> pageList, OldDocumentRenderer renderer) {
			this.pageList = pageList;
			this.renderer = renderer;
		}

		public boolean draw(int index, Side side) {
			int r, g, b;
			r = (Colors.DEFAULT_BLACK & 0xFF0000) >> 16;
			g = (Colors.DEFAULT_BLACK & 0x00FF00) >> 8;
			b = (Colors.DEFAULT_BLACK & 0x0000FF);
			GL11.glColor4f(r, g, b, 1f);
			GlStateManager.color(r, g, b, 1f);
			if (index > pageList.size()) {
				(new IndexOutOfBoundsException(String.format("Page index:%d, pages: %d", index, pageList.size())))
						.printStackTrace();
				return false;
			}
			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder bufferbuilder = tessellator.getBuffer();
			FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
			ItemRenderer itemRenderer = Minecraft.getMinecraft().getItemRenderer();
			Point origin1 = renderer.origin1;
			Point origin2 = renderer.origin2;
			Point origin = renderer.origin1;
			int width = renderer.width;
			int height = renderer.height;
			switch (side) {
			case LEFT:
				GL11.glPushMatrix();
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				GL11.glPushMatrix();
				GL11.glColor4f(1f, 1f, 1f, 1f);
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				GL11.glLineWidth(2f);
				bufferbuilder.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
				bufferbuilder
						.pos(renderer.offsetX + origin1.getX() + 20, renderer.offsetY + origin1.getY() + height - 9, 0)
						.color(0, 0, 0, 255).endVertex();
				bufferbuilder.pos(renderer.offsetX + origin1.getX() + width - 20,
						renderer.offsetY + origin1.getY() + height - 9, 0).color(0, 0, 0, 255).endVertex();
				tessellator.draw();
				/*
				 * Directly use OpenGL to draw lines
				 * 
				 * GL11.glColor3f(0f, 0f, 0f); GL11.glBegin(GL11.GL_LINES);
				 * GL11.glVertex2i(renderer.offsetX + origin1.getX() + 20, renderer.offsetY +
				 * origin1.getY() + height - 9); GL11.glVertex2i(renderer.offsetX +
				 * origin1.getX() + width - 20, renderer.offsetY + origin1.getY() + height - 9);
				 * GL11.glEnd(); GL11.glFlush(); GL11.glEnable(GL11.GL_TEXTURE_2D);
				 * GL11.glPopMatrix(); GL11.glColor4f(1f, 1f, 1f, 1f);
				 */
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				GL11.glPopMatrix();
				GlStateManager.color(r, g, b, 1f);
				fontRenderer.drawString(String.valueOf(index + 1),
						renderer.offsetX + origin1.getX() + width / 2
								- fontRenderer.getStringWidth(String.valueOf(index + 1)) / 2,
						renderer.offsetY + origin1.getY() + height - 8, Colors.DEFAULT_BLACK);
				origin = origin1;
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glPopMatrix();
				GlStateManager.color(r, g, b, 1f);
				break;
			case RIGHT:
				GL11.glPushMatrix();
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				GL11.glColor4f(1f, 1f, 1f, 1f);
				GL11.glPushMatrix();
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				GL11.glLineWidth(2f);
				bufferbuilder.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
				bufferbuilder
						.pos(renderer.offsetX + origin2.getX() + 20, renderer.offsetY + origin2.getY() + height - 9, 0)
						.color(0, 0, 0, 255).endVertex();
				bufferbuilder.pos(renderer.offsetX + origin2.getX() + width - 20,
						renderer.offsetY + origin2.getY() + height - 9, 0).color(0, 0, 0, 255).endVertex();
				tessellator.draw();
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				GL11.glPopMatrix();
				GlStateManager.color(r, g, b, 1f);
				fontRenderer.drawString(String.valueOf(index + 1),
						renderer.offsetX + origin2.getX() + width / 2
								- fontRenderer.getStringWidth(String.valueOf(index + 1)) / 2,
						renderer.offsetY + origin2.getY() + height - 8, Colors.DEFAULT_BLACK);
				origin = origin2;
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glPopMatrix();
				GlStateManager.color(r, g, b, 1f);
				break;
			}
			int x = origin.getX() + renderer.offsetX;
			int y = origin.getY() + renderer.offsetY;
			Page page;
			try {
				page = pageList.get(index);
			} catch (IndexOutOfBoundsException e) {
				e.printStackTrace();
				return false;
			}
			for (Element element : page.elements) {
				switch (element.getType()) {
				case IMAGE:
					int w = ((Element.Image) element).width;
					int h = ((Element.Image) element).height;
					GL11.glPushMatrix();
					GL11.glEnable(GL11.GL_BLEND);
					GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
					GL11.glColor4f(1f, 1f, 1f, 1f);
					Minecraft.getMinecraft().getTextureManager().bindTexture(((Element.Image) element).image);
					Gui.drawModalRectWithCustomSizedTexture(x + (renderer.width - w) / 2, y, 0, 0, w, h, w, h);
					y += h;
					GL11.glDisable(GL11.GL_BLEND);
					GL11.glPopMatrix();
					break;
				case TEXTLINE:
					GL11.glPushMatrix();
					GL11.glEnable(GL11.GL_BLEND);
					GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
					GlStateManager.color(r, g, b, 1f);
					fontRenderer.drawString(((Element.TextLine) element).text, x, y, Colors.DEFAULT_BLACK);
					y += 10;
					GL11.glDisable(GL11.GL_BLEND);
					GL11.glPopMatrix();
					break;
				case CENTERED_TEXT:
					GL11.glPushMatrix();
					GL11.glEnable(GL11.GL_BLEND);
					GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
					Element.CenteredText t = (Element.CenteredText) element;
					String s = t.text;
					GlStateManager.color(r, g, b, 1f);
					fontRenderer.drawString(s, x + renderer.width / 2 - fontRenderer.getStringWidth(s) / 2, y,
							Colors.DEFAULT_BLACK);
					y += 10;
					GL11.glDisable(GL11.GL_BLEND);
					GL11.glPopMatrix();
					break;
				case LINE:
					GL11.glPushMatrix();
					GL11.glEnable(GL11.GL_BLEND);
					GL11.glDisable(GL11.GL_TEXTURE_2D);
					GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
					GL11.glColor4f(1f, 1f, 1f, 1f);
					Element.Line line = (Element.Line) element;
					GL11.glLineWidth(line.width);
					bufferbuilder.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
					bufferbuilder
							.pos(line.v0.getX() + renderer.offsetX + origin.getX(),
									line.v0.getY() + renderer.offsetY + origin.getY(), 0f)
							.color(line.r / 255F, line.g / 255F, line.b / 255F, 1f).endVertex();
					bufferbuilder
							.pos(line.v1.getX() + renderer.offsetX + origin.getX(),
									line.v1.getY() + renderer.offsetY + origin.getY(), 0f)
							.color(line.r / 255F, line.g / 255F, line.b / 255F, 1f).endVertex();
					tessellator.draw();
					GL11.glEnable(GL11.GL_TEXTURE_2D);
					GL11.glDisable(GL11.GL_BLEND);
					GL11.glPopMatrix();
					break;
				case ITEM:
					GL11.glPushMatrix();
					GL11.glEnable(GL11.GL_BLEND);
					GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
					GL11.glColor4f(1f, 1f, 1f, 1f);
					// TODO

					GL11.glDisable(GL11.GL_BLEND);
					GL11.glPopMatrix();
					break;
				case CRAFTING:
					GL11.glPushMatrix();
					GL11.glEnable(GL11.GL_BLEND);
					GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
					GL11.glColor4f(1f, 1f, 1f, 1f);
					// TODO
					GL11.glDisable(GL11.GL_BLEND);
					GL11.glPopMatrix();
					break;
				}
				GL11.glColor4f(r, g, b, 1f);
				GlStateManager.color(r, g, b, 1f);
			}
			return true;
		}
	}

	private static class Page {
		private List<Element> elements;

		public Page(List<Element> elements) {
			this.elements = elements;
		}

	}

	private static class Element {

		protected Type type;

		public enum Type {
			IMAGE, 
			TEXTLINE, 
			CENTERED_TEXT, 
			LINE, 
			ITEM, 
			CRAFTING
		}

		public Type getType() {
			return type;
		}

		private static class Image extends Element {
			private ResourceLocation image;
			private int width;
			private int height;

			public Image(ResourceLocation image, int width, int height) {
				this.image = image;
				this.width = width;
				this.height = height;
				this.type = Type.IMAGE;
			}

		}

		private static class TextLine extends Element {
			private String text;

			public TextLine(String text) {
				this.text = text;
				this.type = Type.TEXTLINE;
			}

		}

		private static class CenteredText extends Element {
			private String text;

			public CenteredText(String text) {
				this.text = text;
				this.type = Type.CENTERED_TEXT;
			}
		}

		private static class Line extends Element {
			private int r;
			private int g;
			private int b;
			private int width;
			private Point v0;
			private Point v1;

			public Line(int v0x, int v0y, int v1x, int v1y, int width, int r, int g, int b) {
				this.r = r;
				this.g = g;
				this.b = b;
				this.width = width;
				this.v0 = new Point(v0x, v0y);
				this.v1 = new Point(v1x, v1y);
				this.type = Type.LINE;
			}
		}
	}

}
