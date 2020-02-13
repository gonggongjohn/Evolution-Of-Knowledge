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
		String currentLine = "";
		
		try {
			while(chars.get(readerIndex) != -1) {
				if(chars.get(readerIndex) == '`') {
					if(chars.get(readerIndex + 1) == '!') {
						readerIndex += 2;
						String m = "";
						for(int i=0; i<3; i++) {
							m += (char)chars.get(readerIndex).intValue();
							readerIndex++;
						}
						if(m.equals("img")) {
							readerIndex++;
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
								pageList.add(new Document.Page(new ArrayList<Document.Page.Element>(elements)));
								elements.clear();
								currentY = 0;
							}
							elements.add(new Document.Page.Element.Image(location, Integer.parseInt(_widths), Integer.parseInt(_heights)));
							currentY += Integer.parseInt(_heights);
							
						} else if(m.equals("eop")) {
							pageList.add(new Document.Page(new ArrayList<Document.Page.Element>(elements)));
							elements.clear();
							currentY = 0;
							readerIndex += 1;
						}
					} else {
						readerIndex++;
						continue;
					}
				} else if(chars.get(readerIndex) == '\r') {
					readerIndex ++;
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
		} catch(IndexOutOfBoundsException e) {
			e.printStackTrace();
			return false;
		}

		pageList.add(new Document.Page(new ArrayList<Document.Page.Element>(elements)));
		elements.clear();
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
				bufferbuilder.color(0, 0, 0, 0);
				bufferbuilder.pos(renderer.offsetX + origin1.getX() + 20, renderer.offsetY + origin1.getY() + height - 9, 0).endVertex();
				bufferbuilder.pos(renderer.offsetX + origin1.getX() + width - 20, renderer.offsetY + origin1.getY() + height - 9, 0).endVertex();
				tessellator.draw();
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				GL11.glPopMatrix();
				GL11.glColor4f(1f, 1f, 1f, 1f);
				fontRenderer.drawString(String.valueOf(index + 1), renderer.offsetX + origin1.getX() + width / 2 - fontRenderer.getStringWidth(String.valueOf(index + 1)) / 2, renderer.offsetY + origin1.getY() + height - 8, Colors.DEFAULT_BLACK);
				origin = origin1;
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glPopMatrix();
				break;
			case RIGHT:
				GL11.glPushMatrix();
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				GL11.glPushMatrix();
				GL11.glColor4f(1f, 1f, 1f, 1f);
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				GL11.glLineWidth(2f);
				bufferbuilder.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
				bufferbuilder.color(0, 0, 0, 0);
				bufferbuilder.pos(renderer.offsetX + origin2.getX() + 20, renderer.offsetY + origin2.getY() + height - 9, 0).endVertex();
				bufferbuilder.pos(renderer.offsetX + origin2.getX() + width - 20, renderer.offsetY + origin2.getY() + height - 9, 0).endVertex();
				tessellator.draw();
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				GL11.glPopMatrix();
				GL11.glColor4f(1f, 1f, 1f, 1f);
				fontRenderer.drawString(String.valueOf(index + 2), renderer.offsetX + origin2.getX() + width / 2 - fontRenderer.getStringWidth(String.valueOf(index + 2)) / 2, renderer.offsetY + origin2.getY() + height - 8, Colors.DEFAULT_BLACK);
				origin = origin2;
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glPopMatrix();
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
					Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 0, w, h, w, h);
					y += h;
					GL11.glDisable(GL11.GL_BLEND);
					GL11.glPopMatrix();
					break;
				case TEXTLINE:
					GL11.glPushMatrix();
					GL11.glEnable(GL11.GL_BLEND);
					GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
					GL11.glColor4f(1f, 1f, 1f, 1f);
					fontRenderer.drawString(((Page.Element.TextLine)element).text, x, y, Colors.DEFAULT_BLACK);
					y += 10;
					GL11.glDisable(GL11.GL_BLEND);
					GL11.glPopMatrix();
					break;
				}
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
					TEXTLINE
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
			}
		}
	}
}
