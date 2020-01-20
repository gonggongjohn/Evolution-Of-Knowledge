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
 * GUI基类
 */
public abstract class MetaGUIScreen extends GuiScreen {
	/**
	 * 这个Map用于存储所有GUI控件。 键为控件id，值为控件对象。
	 */
	private Map<Integer, GUIControl> controls = new HashMap<Integer, GUIControl>();
	private ResourceLocation TEXTURE = new ResourceLocation("");
	private int windowWidth;
	private int windowHeight;
	private int texWidth;
	private int texHeight;
	private int offsetX;
	private int offsetY;
	private boolean forceSwitchGuiScale;
	private int lastGuiScale;
	
	
	public MetaGUIScreen(boolean forceSwitchGuiScale) {
		super();
		this.forceSwitchGuiScale = forceSwitchGuiScale;
		lastGuiScale = Minecraft.getMinecraft().gameSettings.guiScale;
		if(forceSwitchGuiScale)
			Minecraft.getMinecraft().gameSettings.guiScale = 3;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		// offsetX = (this.width - this.windowWidth) / 2;
		// offsetY = (this.height - this.windowHeight) / 2;
		this.drawDefaultBackground();
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(TEXTURE);
		Gui.drawScaledCustomSizeModalRect(offsetX, offsetY, 0f, 0f, texWidth, texHeight, (int)0.8*width, (int)0.8*height, 800, 420);
		// Gui.drawModalRectWithCustomSizedTexture(offsetX, offsetY, 0, 0, windowWidth, windowHeight, texWidth, texHeight);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		// TODO 自动生成的方法存根
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
		// TODO 自动生成的方法存根
		super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		// TODO 自动生成的方法存根
		super.actionPerformed(button);
	}

	@Override
	public void initGui() {
		// TODO 自动生成的方法存根
		super.initGui();
	}

	@Override
	public void updateScreen() {
		// TODO 自动生成的方法存根
		super.updateScreen();
	}

	@Override
	public void onGuiClosed() {
		if(forceSwitchGuiScale) {
			Minecraft.getMinecraft().gameSettings.guiScale = lastGuiScale;
		}
		super.onGuiClosed();
	}

	@Override
	public void drawBackground(int tint) {
		// TODO 自动生成的方法存根
		super.drawBackground(tint);
	}

	@Override
	public boolean doesGuiPauseGame() {
		// TODO 自动生成的方法存根
		return super.doesGuiPauseGame();
	}

	public Map<Integer, GUIControl> getControls() {
		return this.controls;
	}

	public int getWindowWidth() {
		return windowWidth;
	}

	public int getWindowHeight() {
		return windowHeight;
	}
	
	public void setWindowSize(int width, int height) {
		if(width <= 0 || height <= 0) {
			EOK.getLogger().error("An error occurred when initializating GUI, width and height must be positive.");
			throw new IllegalArgumentException("Width and height must be positive");
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
	
	public int getOffsetX() {
		return offsetX;
	}
	
	public int getOffsetY() {
		return offsetY;
	}
	
	/*
	public void setForceSwitchScreenScale(boolean a) {
		forceSwitchGuiScale = true;
	}
	*/
	
	public void setTextureSize(int width, int height) {
		if(width <= 0 || height <= 0) {
			EOK.getLogger().error("An error occurred when initializating GUI, texture width and texture height must be positive.");
			throw new IllegalArgumentException("Texture width and texture height must be positive");
		}
		this.texWidth = width;
		this.texHeight = height;
	}

	public ResourceLocation getTexture() {
		return TEXTURE;
	}

	public void setTexture(ResourceLocation texture) {
		if(texture == null) throw new IllegalArgumentException("Texture can't be null");
		TEXTURE = texture;
	}

}
