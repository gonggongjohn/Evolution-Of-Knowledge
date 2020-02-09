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
 * GUIScreen Base Class
 * 
 * @see GUIScreenTest
 */
public abstract class MetaGUIScreen extends GuiScreen {
	/**
	 * This map is used to store all GUI controls. The key is the control ID and the value is the control object.
	 */
	private Map<Integer, GUIControl> controls = new HashMap<Integer, GUIControl>();
	private ResourceLocation TEXTURE = new ResourceLocation("");
	/**
	 * The window width here is not the width of the game window, but the width of the GUI interface
	 */
	private int windowWidth;
	/**
	 * The window height here is not the height of the game window, but the height of the GUI interface
	 */
	private int windowHeight;
	private int texWidth;
	private int texHeight;
	private int offsetX;
	private int offsetY;
	/**
	 * It is used to decide whether or not to force the interface zoom factor to be changed when the GUI is opened
	 */
	private boolean forceChangeGuiScale;
	/**
	 * It is used to determine whether the GUI has a custom background
	 * If it is true, the drawDefaultBackground() method will not be executed
	 */
	private boolean hasCustomBackground = false;
	/**
	 * Store the interface zoom settings before the player opens the GUI, so as to restore the original interface size when the player closes the GUI
	 */
	private int lastGuiScale;
	/**
	 * Ratio of abscissa of top left corner vertex of GUI to screen width
	 */
	private float guiPosX;
	/**
	 * The ratio of the vertical coordinate of the top left corner of GUI to the screen height
	 */
	private float guiPosY;
	private String title = "";
	
	/**
	 * Construct a new instance of MetaGUIScreen
	 * 
	 * @param forceChangeGuiScale Whether to force the user to change the interface zoom ratio when opening the GUI
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
	 * Get all the controls of this GUI and their corresponding IDs
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
	 * Get the abscissa of the top left corner of GUI
	 */
	public int getOffsetX() {
		return offsetX;
	}
	
	/**
	 * Get the vertical coordinate of the top left corner of GUI
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
	 * Set the title of the interface, which is displayed in the middle of the upper part of the interface by default
	 * @param title Title, doesn't support multi line yet
	 */
	public void setTitle(String title) {
		if(title == null)
			throwArgumentException("title can't be null");
		this.title = title;
	}
	
	/**
	 * Gets whether the GUI will force the interface scaling settings to be modified when it is opened
	 */
	public boolean whetherToChangeGuiScaleWhenOpening() {
		return forceChangeGuiScale;
	}
	
	/*
	 * Get the width of the game screen
	 */
	public int getScreenWidth() {
		return width;
	}
	
	/**
	 * Get the height of the game screen
	 */
	public int getScreenHeight() {
		return height;
	}
	
	/**
	 * Set the ratio of the horizontal / vertical coordinates of the top left corner of the GUI to the screen width / height
	 * @param x Ratio of top left corner vertex of GUI to screen width
	 * @param y Ratio of top left corner vertex of GUI to screen height
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
	 * Set whether this GUI has a custom background
	 * If it is true, the drawDefaultBackground() method will not be executed
	 */
	public void setHasCustomBackground(boolean b) {
		this.hasCustomBackground = b;
	}

}
