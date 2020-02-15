package com.gonggongjohn.eok.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Point;

import com.gonggongjohn.eok.EOK;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class DocumentRenderer {
	private Point origin1;
	private Point origin2;
	private int width;
	private int height;
	private ResourceLocation documentLoc;
	private InputStreamReader reader;
	private Document document;
	private boolean ready;
	private int offsetX;
	private int offsetY;

	public DocumentRenderer(int origin1X, int origin1Y, int origin2X, int origin2Y, int width, int height, ResourceLocation documentLocation) {
		origin1 = new Point(origin1X, origin1Y);
		origin2 = new Point(origin2X, origin2Y);
		this.width = width;
		this.height = height;
		this.documentLoc = documentLocation;
		ready = this.read();
	}

	public void draw(int index, Side side, int offsetX, int offsetY) {
		if(ready) {
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
		InputStream stream;
		try {
			stream = Minecraft.getMinecraft().getResourceManager().getResource(documentLoc).getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		try {
			reader = new InputStreamReader(stream, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		}
		List<Document.Page> pageList = new ArrayList<Document.Page>();
		List<Document.Page.Element> elements = new ArrayList<Document.Page.Element>();
		List<Integer> chars = new ArrayList<Integer>();
		while(true) {
			int c;
			try {
				c = reader.read();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
			if(c == -1) {
				break;
			}
			chars.add(c);
		}
		chars.add(-1);
		
		int readerIndex = 0;
		int currentY = 0;
		int lines = 1; // WARNING: This is the current line in the document FILE, isn't that displays on the SCREEN.
		String currentLine = "";
		
		try {
			while(chars.get(readerIndex) != -1) {
				if(chars.get(readerIndex) == '`') {
					if(chars.get(readerIndex + 1) == '!') {
						readerIndex += 2;
						String m = "";
						while(chars.get(readerIndex) != ' ' && chars.get(readerIndex) != '`') {
							m += (char)chars.get(readerIndex).intValue();
							readerIndex++;
						}
						readerIndex++;
						if(m.equalsIgnoreCase("image")) {
							String img = "";
							String _widths = "";
							String _heights = "";
							while(chars.get(readerIndex) != ',') {
								img += (char)chars.get(readerIndex).intValue();
								readerIndex++;
							}
							readerIndex++;
							while(chars.get(readerIndex) != ',') {
								_widths += (char)chars.get(readerIndex).intValue();
								readerIndex++;
							}
							readerIndex++;
							while(chars.get(readerIndex) != '`') {
								_heights += (char)chars.get(readerIndex).intValue();
								readerIndex++;
							}
							readerIndex++;
							ResourceLocation location = new ResourceLocation(EOK.MODID + ":" + img);
							if(currentY + Integer.parseInt(_heights) + 10 > this.height) {
								if(Integer.parseInt(_heights) + 10 > this.height) {
									EOK.getLogger().error("Image {} is too large.", location.toString());
									continue;
								}
								pageList.add(new Document.Page(new ArrayList<Document.Page.Element>(elements)));
								elements.clear();
								currentY = 0;
							}
							elements.add(new Document.Page.Element.Image(location, Integer.parseInt(_widths), Integer.parseInt(_heights)));
							currentY += Integer.parseInt(_heights);
							
						} else if(m.equalsIgnoreCase("end_of_page")) {
							pageList.add(new Document.Page(new ArrayList<Document.Page.Element>(elements)));
							elements.clear();
							currentY = 0;
						} else if(m.equalsIgnoreCase("centered_text")) {
							String s = "";
							while(chars.get(readerIndex) != '`') {
								s += (char)chars.get(readerIndex).intValue();
								readerIndex++;
							}
							if(currentY + 20 >= this.height) {
								pageList.add(new Document.Page(new ArrayList<Document.Page.Element>(elements)));
								elements.clear();
								if(!currentLine.isEmpty()) {
									elements.add(new Document.Page.Element.TextLine(new String(currentLine)));
									currentLine = "";
									currentY += 10;
								}
							}
							elements.add(new Document.Page.Element.CenteredText(s));
							currentY += 10;
						} else if(m.equalsIgnoreCase("line")) {
							String x1s = "";
							String y1s = "";
							String x2s = "";
							String y2s = "";
							String widths = "";
							String hex = "";
							while(chars.get(readerIndex) != ',') {
								x1s += (char)chars.get(readerIndex).intValue();
								readerIndex++;
							}
							readerIndex++;
							while(chars.get(readerIndex) != ',') {
								y1s += (char)chars.get(readerIndex).intValue();
								readerIndex++;
							}
							readerIndex++;
							while(chars.get(readerIndex) != ',') {
								x2s += (char)chars.get(readerIndex).intValue();
								readerIndex++;
							}
							readerIndex++;
							while(chars.get(readerIndex) != ',') {
								y2s += (char)chars.get(readerIndex).intValue();
								readerIndex++;
							}
							readerIndex++;
							while(chars.get(readerIndex) != ',') {
								widths += (char)chars.get(readerIndex).intValue();
								readerIndex++;
							}
							readerIndex++;
							while(chars.get(readerIndex) != '`') {
								hex += (char)chars.get(readerIndex).intValue();
								readerIndex++;
							}
							readerIndex++;
							int x1, y1, x2, y2, lwidth;
							int color = 0;
							x1 = Integer.parseInt(x1s);
							y1 = Integer.parseInt(y1s);
							x2 = Integer.parseInt(x2s);
							y2 = Integer.parseInt(y2s);
							lwidth = Integer.parseInt(widths);
							hex = hex.toUpperCase();
							for(char c : hex.toCharArray()) {
								switch(c) {
								case '#': continue;
								case '0': color = color << 4 | 0x00000000; break;
								case '1': color = color << 4 | 0x00000001; break;
								case '2': color = color << 4 | 0x00000002; break;
								case '3': color = color << 4 | 0x00000003; break;
								case '4': color = color << 4 | 0x00000004; break;
								case '5': color = color << 4 | 0x00000005; break;
								case '6': color = color << 4 | 0x00000006; break;
								case '7': color = color << 4 | 0x00000007; break;
								case '8': color = color << 4 | 0x00000008; break;
								case '9': color = color << 4 | 0x00000009; break;
								case 'A': color = color << 4 | 0x0000000A; break;
								case 'B': color = color << 4 | 0x0000000B; break;
								case 'C': color = color << 4 | 0x0000000C; break;
								case 'D': color = color << 4 | 0x0000000D; break;
								case 'E': color = color << 4 | 0x0000000E; break;
								case 'F': color = color << 4 | 0x0000000F; break;
								default: break;
								}
							}
							int r, g, b;
							r = (color & 0xFF0000) >> 16;
							g = (color & 0x00FF00) >> 8;
							b = (color & 0x0000FF);
							elements.add(new Document.Page.Element.Line(x1, y1, x2, y2, lwidth, r, g, b));
						} else if(m.equalsIgnoreCase("comment")) {
							while(chars.get(readerIndex) != '`') {
								readerIndex++;
							}
							readerIndex++;
						} else {
							EOK.getLogger().error("Illegal token {} at line {}.", m, lines);
						}
						readerIndex ++;
					} else {
						EOK.getLogger().error("Illegal symbol \"`\" at line {}, delete it.", lines);
						readerIndex++;
						continue;
					}
					readerIndex++;
					lines++;
				} else if(chars.get(readerIndex) == '\r') {
					readerIndex++;
				} else if(chars.get(readerIndex) == '\n') {
				
					if(currentY + 20 > this.height) {
						pageList.add(new Document.Page(new ArrayList<Document.Page.Element>(elements)));
						elements.clear();
						currentY = 0;
					}
					elements.add(new Document.Page.Element.TextLine(new String(currentLine)));
					currentLine = "";
					currentY += 10;
					readerIndex ++;
					lines++;
				} else {
					int strWidth = Minecraft.getMinecraft().fontRenderer.getStringWidth(currentLine);
					if(strWidth >= this.width) {
						if(currentY + 20 > this.height) {
							pageList.add(new Document.Page(new ArrayList<Document.Page.Element>(elements)));
							elements.clear();
							currentY = 0;
						}
						elements.add(new Document.Page.Element.TextLine(new String(currentLine)));
						currentLine = "";
						currentY += 10;
					} else {
						currentLine += (char)chars.get(readerIndex).intValue();
						readerIndex++;
					}
				}
			}
			lines--;
		} catch(IndexOutOfBoundsException e) {
			e.printStackTrace();
			return false;
		}

		if(!currentLine.isEmpty()) {
			if(currentY + 20 >= this.height) {
				pageList.add(new Document.Page(new ArrayList<Document.Page.Element>(elements)));
				elements.clear();
			}
			elements.add(new Document.Page.Element.TextLine(new String(currentLine)));
			currentLine = "";
			currentY += 10;
		}
		
		if(!elements.isEmpty()) {
			pageList.add(new Document.Page(new ArrayList<Document.Page.Element>(elements)));
			elements.clear();
		}
		
		document = new Document(pageList, this);
		
		return true;
	}

	private static class Document {
		
		private List<Page> pageList;
		private DocumentRenderer renderer;
		
		public Document(List<Page> pageList, DocumentRenderer renderer) {
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
			if(index > pageList.size()) {
				(new IndexOutOfBoundsException(String.format("Page index:%d, pages: %d", index, pageList.size())))
				.printStackTrace();
				return false;
			}
			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder bufferbuilder = tessellator.getBuffer();
			FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
			Point origin1 = renderer.origin1;
			Point origin2 = renderer.origin2;
			Point origin = renderer.origin1;
			int width = renderer.width;
			int height = renderer.height;
			switch(side) {
			case LEFT:
				GL11.glPushMatrix();
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				GL11.glPushMatrix();
				GL11.glColor4f(1f, 1f, 1f, 1f);
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				GL11.glLineWidth(2f);
				bufferbuilder.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
				bufferbuilder.pos(renderer.offsetX + origin1.getX() + 20, renderer.offsetY + origin1.getY() + height - 9, 0).color(0, 0, 0, 255).endVertex();
				bufferbuilder.pos(renderer.offsetX + origin1.getX() + width - 20, renderer.offsetY + origin1.getY() + height - 9, 0).color(0, 0, 0, 255).endVertex();
				tessellator.draw();
				/*
				 * Directly use OpenGL to draw lines
				 * 
				GL11.glColor3f(0f, 0f, 0f);
				GL11.glBegin(GL11.GL_LINES);
				GL11.glVertex2i(renderer.offsetX + origin1.getX() + 20, renderer.offsetY + origin1.getY() + height - 9);
				GL11.glVertex2i(renderer.offsetX + origin1.getX() + width - 20, renderer.offsetY + origin1.getY() + height - 9);
				GL11.glEnd();
				GL11.glFlush();
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				GL11.glPopMatrix();
				GL11.glColor4f(1f, 1f, 1f, 1f);
				*/
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				GL11.glPopMatrix();
				GlStateManager.color(r, g, b, 1f);
				fontRenderer.drawString(String.valueOf(index + 1), renderer.offsetX + origin1.getX() + width / 2 - fontRenderer.getStringWidth(String.valueOf(index + 1)) / 2, renderer.offsetY + origin1.getY() + height - 8, Colors.DEFAULT_BLACK);
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
				bufferbuilder.pos(renderer.offsetX + origin2.getX() + 20, renderer.offsetY + origin2.getY() + height - 9, 0).color(0, 0, 0, 255).endVertex();
				bufferbuilder.pos(renderer.offsetX + origin2.getX() + width - 20, renderer.offsetY + origin2.getY() + height - 9, 0).color(0, 0, 0, 255).endVertex();
				tessellator.draw();
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				GL11.glPopMatrix();
				GlStateManager.color(r, g, b, 1f);
				fontRenderer.drawString(String.valueOf(index + 1), renderer.offsetX + origin2.getX() + width / 2 - fontRenderer.getStringWidth(String.valueOf(index + 1)) / 2, renderer.offsetY + origin2.getY() + height - 8, Colors.DEFAULT_BLACK);
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
			} catch(IndexOutOfBoundsException e) {
				e.printStackTrace();
				return false;
			}
			for(Page.Element element : page.elements) {
				switch(element.getType()) {
				case IMAGE:
					int w = ((Page.Element.Image)element).width;
					int h = ((Page.Element.Image)element).height;
					GL11.glPushMatrix();
					GL11.glEnable(GL11.GL_BLEND);
					GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
					GL11.glColor4f(1f, 1f, 1f, 1f);
					Minecraft.getMinecraft().getTextureManager().bindTexture(((Page.Element.Image)element).image);
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
					fontRenderer.drawString(((Page.Element.TextLine)element).text, x, y, Colors.DEFAULT_BLACK);
					y += 10;
					GL11.glDisable(GL11.GL_BLEND);
					GL11.glPopMatrix();
					break;
				case CENTERED_TEXT:
					GL11.glPushMatrix();
					GL11.glEnable(GL11.GL_BLEND);
					GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
					Page.Element.CenteredText t = (Page.Element.CenteredText)element;
					String s = t.text;
					GlStateManager.color(r, g, b, 1f);
					fontRenderer.drawString(s, x + renderer.width / 2 - fontRenderer.getStringWidth(s) / 2, y, Colors.DEFAULT_BLACK);
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
					Page.Element.Line line = (Page.Element.Line)element;
					GL11.glLineWidth(line.width);
					bufferbuilder.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
					bufferbuilder.pos(line.v0.getX() + renderer.offsetX + origin.getX(), line.v0.getY() + renderer.offsetY + origin.getY(), 0f).color(line.r / 255F, line.g / 255F, line.b / 255F, 1f).endVertex();
					bufferbuilder.pos(line.v1.getX() + renderer.offsetX + origin.getX(), line.v1.getY() + renderer.offsetY + origin.getY(), 0f).color(line.r / 255F, line.g / 255F, line.b / 255F, 1f).endVertex();
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
		
		private static class Page {
			private List<Element> elements;

			public Page(List<Element> elements) {
				this.elements = elements;
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
	}
}
