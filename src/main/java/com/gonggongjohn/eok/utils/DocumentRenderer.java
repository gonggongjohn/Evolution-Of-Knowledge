package com.gonggongjohn.eok.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.Point;

import com.gonggongjohn.eok.EOK;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class DocumentRenderer {
	private Point origin1;
	private Point origin2;
	private int width;
	private int height;
	private ResourceLocation documentLoc;
	private InputStreamReader reader;
	private int side;
	private int lines;
	private int line;
	private int pages;
	private int page;

	public DocumentRenderer(int origin1X, int origin1Y, int origin2X, int origin2Y, int width, int height, ResourceLocation documentLocation) {
		origin1 = new Point(origin1X, origin1Y);
		origin2 = new Point(origin2X, origin2Y);
		this.width = width;
		this.height = height;
		this.documentLoc = documentLocation;
		this.read();
		lines = (int)height / 10;
	}

	public void draw() {

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
		char c;
		int currentY = 0;
		String currentLine = "";
		
		while(chars.get(readerIndex) != -1) {
			if(chars.get(readerIndex) == '`') {
				if(chars.get(readerIndex + 1) == '!') {
					readerIndex += 2;
					String m = "";
					for(int i=0; i<3; i++) {
						m += chars.get(readerIndex);
						readerIndex++;
					}
					if(m == "img") {
						readerIndex++;
						String img = "";
						String _widths = "";
						String _heights = "";
						while(chars.get(readerIndex) != ',') {
							img += chars.get(readerIndex);
							readerIndex++;
						}
						readerIndex++;
						while(chars.get(readerIndex) != ',') {
							_widths += chars.get(readerIndex);
							readerIndex++;
						}
						readerIndex++;
						while(chars.get(readerIndex) != '`') {
							_heights += chars.get(readerIndex);
							readerIndex++;
						}
						readerIndex++;
						ResourceLocation location = new ResourceLocation(EOK.MODID + ":" + img);
						if(currentY + Integer.parseInt(_heights) > this.height) {
							pageList.add(new Document.Page(elements));
							elements.clear();
							currentY = 0;
						}
						elements.add(new Document.Page.Element.Image(location, new Point(0, currentY), Integer.parseInt(_widths), Integer.parseInt(_heights)));
						currentY += Integer.parseInt(_heights);
						
					} else if(m == "eop") {
						pageList.add(new Document.Page(elements));
						elements.clear();
						currentY = 0;
						readerIndex += 1;
					}
				}
			} else if(chars.get(readerIndex) == '\n') {
				if(currentY + 10 > this.height) {
					pageList.add(new Document.Page(elements));
					elements.clear();
					currentY = 0;
				}
				elements.add(new Document.Page.Element.TextLine(currentLine));
				currentY += 10;
				readerIndex += 1;
			} else {
				int strWidth = Minecraft.getMinecraft().fontRenderer.getStringWidth(currentLine);
				if(strWidth >= this.width) {
					if(currentY + 10 > this.height) {
						pageList.add(new Document.Page(elements));
						elements.clear();
						currentLine = "";
						currentY = 0;
					}
					elements.add(new Document.Page.Element.TextLine(currentLine));
					currentY += 10;
				} else {
					currentLine += chars.get(readerIndex);
					readerIndex++;
				}
			}
		}
		// TODO
		return true;
	}

	private static class Document {
		
		private List<Page> pageList;
		
		public Document(List<Page> pageList) {
			this.pageList = pageList;
		}
		
		public void draw(int index) {
			
		}
		
		private static class Page {
			private List<Element> elements;

			public Page(List<Element> elements) {
				this.elements = elements;
			}

			private static class Element {

				protected Type type;

				public static enum Type {
					TEXT,
					IMAGE
				}

				public Type getType() {
					return type;
				}

				public void draw() {

				}

				private static class Image extends Element {
					private ResourceLocation image;
					private Point pos;
					private int width;
					private int height;

					public Image(ResourceLocation image, Point pos, int width, int height) {
						this.image = image;
						this.pos = pos;
						this.width = width;
						this.height = height;
						this.type = Type.IMAGE;
					}

					public ResourceLocation getImage() {
						return image;
					}

					public Point getPos() {
						return pos;
					}

					public int getWidth() {
						return width;
					}

					public int getHeight() {
						return height;
					}

					@Override
					public void draw() {

					}
				}

				private static class TextLine extends Element {
					private String text;

					public TextLine(String text) {
						this.text = text;
						this.type = Type.TEXT;
					}

					public String getText() {
						return text;
					}

					@Override
					public void draw() {

					}
				}
			}
		}
	}
}
