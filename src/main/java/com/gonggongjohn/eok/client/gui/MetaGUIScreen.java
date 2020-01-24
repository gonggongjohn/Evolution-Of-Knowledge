package com.gonggongjohn.eok.client.gui;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import com.gonggongjohn.eok.EOK;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

/**
 * GUIScreen基类
 * 
 * @see GUIScreenTest
 */
public abstract class MetaGUIScreen extends GuiScreen {
	/**
	 * 这个Map用于存储所有GUI控件。 键为控件id，值为控件对象。
	 */
	private Map<Integer, GUIControl> controls = new HashMap<Integer, GUIControl>();
	private ResourceLocation TEXTURE = new ResourceLocation("");
	/**
	 * 这里的windowWidth不是游戏窗口的宽，是GUI界面的宽
	 */
	private int windowWidth;
	/**
	 * 这里的windowHeight不是游戏窗口的高，是GUI界面的高
	 */
	private int windowHeight;
	private int texWidth;
	private int texHeight;
	private int offsetX;
	private int offsetY;
	/**
	 * 它用来决定在打开GUI时是否强制更改界面缩放倍数
	 */
	private boolean forceChangeGuiScale;
	/**
	 * 它用来决定这个GUI是否有自定义的背景
	 * 如果它为true，将不会执行drawDefaultBackground()方法
	 */
	private boolean hasCustomBackground = false;
	/**
	 * 存储玩家打开GUI之前的界面缩放设置，以便在玩家关闭GUI时恢复原来的界面大小
	 */
	private int lastGuiScale;
	/**
	 * GUI左上角顶点的横坐标与屏幕宽度的比值
	 */
	private float guiPosX;
	/**
	 * GUI左上角顶点的纵坐标与屏幕高度的比值
	 */
	private float guiPosY;
	private String title = "";
	
	/**
	 * 构造一个新的MetaGUIScreen实例
	 * 
	 * @param forceChangeGuiScale 玩家打开GUI时是否强制更改界面缩放倍数
	 */
	public MetaGUIScreen(boolean forceChangeGuiScale) {
		super();
		this.forceChangeGuiScale = forceChangeGuiScale;
		lastGuiScale = Minecraft.getMinecraft().gameSettings.guiScale;
		if(forceChangeGuiScale)
			Minecraft.getMinecraft().gameSettings.guiScale = 3;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		// offsetX = (this.width - this.windowWidth) / 2;
		// offsetY = (this.height - this.windowHeight) / 2;
		if(!this.hasCustomBackground) {
			this.drawDefaultBackground();
		}
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(TEXTURE);
		//Gui.drawScaledCustomSizeModalRect(offsetX, offsetY, 0f, 0f, texWidth, texHeight, (int)0.8*width, (int)0.8*height, 800, 420);
		// Gui.drawModalRectWithCustomSizedTexture(offsetX, offsetY, 0, 0, windowWidth, windowHeight, texWidth, texHeight);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}
	
	

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
		super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
	}

	@Override
	public void initGui() {
		super.initGui();
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
	}

	@Override
	public void onGuiClosed() {
		if(forceChangeGuiScale) {
			Minecraft.getMinecraft().gameSettings.guiScale = lastGuiScale;
		}
		super.onGuiClosed();
	}

	@Override
	public void drawBackground(int tint) {
		super.drawBackground(tint);
	}

	@Override
	public boolean doesGuiPauseGame() {
		return super.doesGuiPauseGame();
	}

	/**
	 * 获取这个GUI的所有控件及其所对应的ID
	 */
	public Map<Integer, GUIControl> getControls() {
		return this.controls;
	}
	
	private static void throwArgumentException(String s) {
		EOK.getLogger().error("An error occurred when rendering GUI, " + s + ".");
		throw new IllegalArgumentException(s);
	}

	public int getWindowWidth() {
		return windowWidth;
	}

	public int getWindowHeight() {
		return windowHeight;
	}
	
	public void setWindowSize(int width, int height) {
		if(width <= 0 || height <= 0) {
			throwArgumentException("width and height must be positive");
		}
		this.windowWidth = width;
		this.windowHeight = height;
	}

	public int getTexWidth() {
		return texWidth;
	}

	public int getTexHeight() {
		return texHeight;
	}
	
	/**
	 * 获取GUI左上角顶点的横坐标
	 */
	public int getOffsetX() {
		return offsetX;
	}
	
	/**
	 * 获取GUI左上角顶点的纵坐标
	 */
	public int getOffsetY() {
		return offsetY;
	}
	
	public void setTextureSize(int width, int height) {
		if(width <= 0 || height <= 0) {
			throwArgumentException("texture width and texture height must be positive");
		}
		this.texWidth = width;
		this.texHeight = height;
	}

	public ResourceLocation getTexture() {
		return TEXTURE;
	}

	public void setTexture(ResourceLocation texture) {
		if(texture == null)
			throwArgumentException("texture can't be null");
		TEXTURE = texture;
	}
	
	public String getTitle() {
		return title;
	}
	
	/**
	 * 设置界面标题，标题默认显示在界面上部中间
	 * @param title 标题，不支持多行
	 */
	public void setTitle(String title) {
		if(title == null)
			throwArgumentException("title can't be null");
		this.title = title;
	}
	
	/**
	 * 获取该GUI在打开时是否会强制修改界面缩放设置
	 */
	public boolean whetherToChangeGuiScaleWhenOpening() {
		return forceChangeGuiScale;
	}
	
	/*
	 * 获取游戏屏幕的宽
	 */
	public int getScreenWidth() {
		return width;
	}
	
	/**
	 * 获取游戏屏幕的高
	 */
	public int getScreenHeight() {
		return height;
	}
	
	/**
	 * 设置GUI左上角顶点的横/纵坐标与屏幕宽/高的比值
	 * @param x GUI左上角顶点与屏幕宽的比值
	 * @param y GUI左上角顶点与屏幕高的比值
	 */
	public void setStartPos(float x, float y) {
		if(x < 0 || y < 0)
			throwArgumentException("abscissa and ordinate must be positive");
		this.guiPosX = x;
		this.guiPosY = y;
	}
	
	private int calculateGuiPosX() {
		return (int)(width * guiPosX);
	}
	
	private int calculateGuiPosY() {
		return (int)(height * guiPosY);
	}
	
	/**
	 * 设置这个GUI是否有自定义背景
	 * 如果它为true，将不会执行drawDefaultBackground()方法
	 */
	public void setHasCustomBackground(boolean b) {
		this.hasCustomBackground = b;
	}

}
