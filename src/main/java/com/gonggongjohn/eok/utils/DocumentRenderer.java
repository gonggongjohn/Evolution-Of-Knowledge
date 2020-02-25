package com.gonggongjohn.eok.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.apache.logging.log4j.Logger;

import com.gonggongjohn.eok.EOK;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;

public class DocumentRenderer {
	private final int org1X;
	private final int org1Y;
	private final int org2X;
	private final int org2Y;
	private final int width;
	private final int height;
	private final Logger logger;
	private final FontRenderer fontRenderer;
	private final BufferBuilder bufferBuilder;
	private final IResourceManager resourceManager;
	private int lineNumber;
	private ResourceLocation documentLocation;
	private String[] orgLines;
	private static Map<String, Predicate<String[]>> tokenMap = new HashMap<String, Predicate<String[]>>();
	private Document documentIn;
	private List<Page> pages = new ArrayList<Page>();
	private List<Element> elements = new ArrayList<Element>();
	public final boolean available;
	
	public DocumentRenderer(int org1X, int org1Y, int org2X, int org2Y, int width, int height, String documentPath) {
		init();
		this.org1X = org1X;
		this.org1Y = org1Y;
		this.org2X = org2X;
		this.org2Y = org2Y;
		this.width = width;
		this.height = height;
		logger = EOK.getLogger();
		this.fontRenderer = Minecraft.getMinecraft().fontRenderer;
		this.bufferBuilder = Tessellator.getInstance().getBuffer();
		this.resourceManager = Minecraft.getMinecraft().getResourceManager();
		this.documentLocation = new ResourceLocation(documentPath);
		if(!(available = read())) {
			Minecraft.getMinecraft().player.sendStatusMessage(new TextComponentString(I18n.format("message.documentrenderer.error")), false);
		}
	}
	
	private void init() {
		tokenMap.put("center", this::addCenteredText);
		tokenMap.put("comment", (s) -> true);
		tokenMap.put("image", this::addImage);
		tokenMap.put("drawline", this::addLine);
	}
	
	public static enum Side {
		LEFT, RIGHT
	}
	
	public BufferedReader getResource(ResourceLocation location) throws UnsupportedEncodingException, IOException {
		return new BufferedReader(new InputStreamReader(resourceManager.getResource(location).getInputStream(), "UTF-8"));
	}
	
	public boolean read() {
		long startTime = System.currentTimeMillis();
		if(this.width < 20 || this.height < 20) {
			logger.error("Text area is too small! It must be larger than 20*20 pixels.");
			return false;
		}
		logger.debug("Loading document {}:{}", documentLocation.getResourceDomain(), documentLocation.getResourcePath());
		lineNumber = 1;
		BufferedReader reader;
		List<String> lineList = new ArrayList<String>();
		try {
			reader = getResource(documentLocation);
			String line;
			while((line = reader.readLine()) != null) {
				lineList.add(line);
				break;
			}
			orgLines = (String[])lineList.toArray();
		} catch(Exception e) {
			logger.error("Can't read document file \"{}:{}\"", documentLocation.getResourceDomain(), documentLocation.getResourcePath());
			return false;
		}
		logger.debug("Processing tokens");
		for(String line : orgLines) {
			if(!processLine(line)) {
				return false;
			}
			lineNumber++;
		}
		logger.debug("Processing pages");
		if(!buildDocument()) return false;
		logger.debug("Loading completed in {}ms.", System.currentTimeMillis() - startTime);
		return true;
	}
	
	private void appendText(String str) {
		str = "§c§lERROR:" + str;
		String line = "";
		char[] chars = str.toCharArray();
		for(char c : chars) {
			if(fontRenderer.getStringWidth(line) >= this.width) {
				elements.add(new Element.TextLine(new String(line)));
				line = "";
				line += c;
			} else {
				line += c;
			}
		}
		if(!line.isEmpty()) {
			elements.add(new Element.TextLine(line));
		}
	}
	
	private boolean processLine(String line) {
		try {
			if(line.startsWith("`! ") && line.endsWith("`")) {
				String statement = line.substring(3, line.length() - 1);
				if(statement.indexOf(' ') == -1) {	// statement with no arguments
					if(tokenMap.containsKey(statement)) {
						if(!tokenMap.get(statement).test(new String[0])) {
							appendText(line);
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
								appendText(line);
							}
						} else {	// statement with multiple arguments
							String[] argArray = args.split(" ");
							if(!tokenMap.get(token).test(argArray)) {
								appendText(line);
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
		int currentY = 0;
		List<Element> currentElements = new ArrayList<Element>();
		for(Element element : elements) {
			if(element.getHeight() > this.height) {
				logger.error("This element's size exceeds the limit({}*{})", this.width, this.height);
				return false;
			}
			if(currentY + element.getHeight() >= this.height) {
				pages.add(new Page(new ArrayList<Element>(currentElements)));
				currentElements.clear();
				currentElements.add(element);
			} else {
				currentElements.add(element);
			}
		}
		if(!currentElements.isEmpty()) {
			pages.add(new Page(new ArrayList<Element>(currentElements)));
			currentElements.clear();
		}
		documentIn = new Document(pages);
		return true;
	}
	
	private boolean addCenteredText(String[] args) {
		if(args.length != 1) {
			logger.warn("Illegal Arguments: Line {}: There must be only one argument.");
			return false;
		}
		elements.add(new Element.CenteredText(args[0]));
		return true;
	}
	
	/* {path, width, height} */
	private boolean addImage(String[] args) {
		if(args.length != 3) {
			logger.warn("Illegal Arguments: Line {}: There must be only 3 argument.");
			return false;
		}
		try {
			elements.add(new Element.Image(new ResourceLocation(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2])));
		} catch(Exception e) {
			logger.warn("Syntax error at line {}.", lineNumber);
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private boolean addLine(String[] args) {
		// TODO
		return true;
	}

	public int getPages() {
		return pages.size();
	}

	public void draw(int pageIndex, Side side, int offsetX, int offsetY) {
		if(!available) {
			if(side == Side.LEFT) {
				fontRenderer.drawString(I18n.format("manual.error"), offsetX + org1X, offsetY + org1Y, Colors.DEFAULT_BLACK);
			}
			return;
		}
		// TODO 自动生成的方法存根
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
		}
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
	}
	
	private static abstract class Element {
		
		private static enum Type {
			NONE, TEXTLINE, CENTERED_TEXT, IMAGE, LINE, ITEM, CRAFTING
		}
		
		protected abstract Type getType();
		
		protected abstract int getHeight();
		
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
		}
		
		private static class Image extends Element {
			
			private ResourceLocation location;
			private int width;
			private int height;
			
			private Image(ResourceLocation location, int width, int height) {
				this.location = location;
				this.width = width;
				this.height = height;
			}

			@Override
			protected Type getType() {
				return Type.IMAGE;
			}

			@Override
			protected int getHeight() {
				return height;
			}
		}
		
		private static class Line extends Element {
			
			private int x1;
			private int y1;
			private int x2;
			private int y2;
			private int width;
			private int color;
			
			private Line(int x1, int y1, int x2, int y2, int width, int color) {
				this.x1 = x1;
				this.y1 = y1;
				this.x2 = x2;
				this.y2 = y2;
				this.width = width;
				this.color = color;
			}

			@Override
			protected Type getType() {
				return Type.LINE;
			}

			@Override
			protected int getHeight() {
				return 0;
			}
		}
		
		private static class Item extends Element {

			@Override
			protected Type getType() {
				// TODO 自动生成的方法存根
				return null;
			}

			@Override
			protected int getHeight() {
				// TODO 自动生成的方法存根
				return 0;
			}
			// TODO
		}
		
		private static class Crafting extends Element {

			@Override
			protected Type getType() {
				// TODO 自动生成的方法存根
				return null;
			}

			@Override
			protected int getHeight() {
				// TODO 自动生成的方法存根
				return 0;
			}
			// TODO
		}
		
	}
}
