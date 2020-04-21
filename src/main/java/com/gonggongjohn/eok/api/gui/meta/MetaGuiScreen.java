package com.gonggongjohn.eok.api.gui.meta;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.api.gui.Colors;
import com.gonggongjohn.eok.api.render.GLUtils;
import com.gonggongjohn.eok.client.gui.GuiEOKManual;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

/**
 * GuiScreen基类<br><br>
 * GuiScreen Base Class
 * 
 * @see GuiEOKManual
 */
public class MetaGuiScreen extends GuiScreen implements IMetaGui {
	/**
	 * 这个Map用来存储所有的控件，键为控件ID，值为控件的实例。<br><br>
	 * This map is used to store all Gui controls. The key is the control ID and the
	 * value is the control object.
	 */
	private ControlMap controls = new ControlMap(this);
	private ResourceLocation TEXTURE = new ResourceLocation("");
	/**
	 * 这个实例用来创建控件。<br>
	 * This object is used to create Controls.<br><br>
	 * 例子/Example: <br><code>builder.createButton(...);</code><br><br>
	 * <strong>注意/Warning: <br>要使控件被添加进Gui中，必须使用<code>this.addControl(...);
	 * </code>来添加。</strong><br>
	 * <br>If you want to add a control to this Gui, you must use 
	 * <code>this.addControl(...);</code>.</strong>
	 */
	public GuiControl.ControlBuilder builder = new GuiControl.ControlBuilder(this);
	private int nextId = 0;
	/**
	 * 这里的窗口宽度不是游戏窗口宽度，是Gui的宽度。<br><br>
	 * The window width here is not the width of the game window, but the width of
	 * the Gui interface.
	 */
	private int windowWidth;
	/**
	 * 这里的窗口高度不是游戏窗口高度，是Gui的高度。<br><br>
	 * The window height here is not the height of the game window, but the height
	 * of the Gui interface.
	 */
	private int windowHeight;
	private int texWidth;
	private int texHeight;
	private int offsetX;
	private int offsetY;
	/**
	 * 它用来决定在Gui被打开是是否强制调小游戏界面缩放设置。<br><br>
	 * It is used to decide whether or not to force the interface zoom factor to be
	 * turned down when the Gui is opened.
	 */
	private boolean forceChangeGuiScale;
	/**
	 * 它用来决定Gui是否有自定义背景，如果它为true, <code>drawDefaultBackground()</code>
	 * 方法将不会被自动执行。<br><br>
	 * It is used to determine whether the Gui has a custom background If it is
	 * true, the <code>drawDefaultBackground()</code> method will not be executed 
	 * automaticly.
	 */
	private boolean hasCustomBackground = false;
	/**
	 * 存储玩家打开Gui前的界面缩放设置，以便于在玩家关闭Gui时恢复到原来的设置。<br><br>
	 * Store the interface zoom settings before the player opens the Gui, so as to
	 * restore the original interface size when the player closes the Gui.
	 */
	private int lastGuiScale;

	private String title = "";

	/**
	 * Construct a new instance of MetaGuiScreen
	 * 
	 * @param forceChangeGuiScale Whether to force the user to change the interface
	 *                            zoom ratio when opening the Gui
	 */
	public MetaGuiScreen(boolean forceChangeGuiScale) {
		super();
		this.forceChangeGuiScale = forceChangeGuiScale;
		lastGuiScale = Minecraft.getMinecraft().gameSettings.guiScale;
		if (forceChangeGuiScale)
			Minecraft.getMinecraft().gameSettings.guiScale = 3;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		if (!this.hasCustomBackground) {
			this.drawDefaultBackground();
		}
		offsetX = (this.width - this.windowWidth) / 2;
		offsetY = (this.height - this.windowHeight) / 2;
		GLUtils.pushMatrix();
		GLUtils.enableBlend();
		GLUtils.normalBlend();
		GLUtils.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(TEXTURE);
		Gui.drawModalRectWithCustomSizedTexture(offsetX, offsetY, 0, 0, windowWidth, windowHeight, texWidth, texHeight);
		this.fontRenderer.drawString(title, offsetX + windowWidth / 2 - this.fontRenderer.getStringWidth(title) / 2, offsetY + 10, Colors.DEFAULT_BLACK, false);
		GLUtils.disableBlend();
		GLUtils.popMatrix();
		super.drawScreen(mouseX, mouseY, partialTicks);
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
		for(Map.Entry<Integer, GuiControl> entry : controls.entrySet()) {
			if(entry.getValue() instanceof GuiControl.GuiButton) {
				if(button.id == entry.getValue().getId()) {
					((GuiControl.GuiButton)entry.getValue()).func.accept(this);
				}
			}
		}
		super.actionPerformed(button);
	}

	@Override
	public void initGui() {
		offsetX = (this.width - this.windowWidth) / 2;
		offsetY = (this.height - this.windowHeight) / 2;
		super.initGui();
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
	}

	@Override
	public void onGuiClosed() {
		if (forceChangeGuiScale) {
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
		return false;
	}

	@Override
	public ControlMap getControls() {
		return this.controls;
	}

	/**
	 * 抛出IllegalArgumentException并输出报错信息。<br><br>
	 * Throws an IllegalArgumentException and prints this error message. 
	 */
	private static void throwArgumentException(String message) {
		EOK.getLogger().error("An error occurred when rendering Gui, " + message + ".");
		throw new IllegalArgumentException(message);
	}
	
	/**
	 * 获取这个Gui的贴图位置。<br><br>
	 * Gets the texture location of this Gui.
	 */
	public ResourceLocation getTexture() {
		return TEXTURE;
	}
	
	public int getTextureWidth() {
		return this.texWidth;
	}
	
	public int getTextureHeight() {
		return this.texHeight;
	}

	/**
	 * 获取界面的实际宽度。<br><br>
	 * Gets the real width of this Gui.
	 */
	public int getWindowWidth() {
		return windowWidth;
	}

	/**
	 * 获取界面的实际高度。<br><br>
	 * Gets the real height of this Gui.
	 */
	public int getWindowHeight() {
		return windowHeight;
	}

	/**
	 * 设置界面的实际大小，<strong>不建议在构造函数/initGui()之外使用。</strong><br><br>
	 * Sets the real width of this Gui, <strong>it's not recommended to use this 
	 * outside of the constructor/initGui().</strong>
	 */
	public void setWindowSize(int width, int height) {
		if (width <= 0 || height <= 0) {
			throwArgumentException("width and height must be positive");
		}
		this.windowWidth = width;
		this.windowHeight = height;
	}

	/**
	 * 获取Gui左上角的横坐标。<br><br>
	 * Gets the abscissa of the top left corner of Gui.
	 */
	public int getOffsetX() {
		return offsetX;
	}

	/**
	 * 获取Gui左上角的纵坐标。<br><br>
	 * Gets the vertical coordinate of the top left corner of Gui.
	 */
	public int getOffsetY() {
		return offsetY;
	}

	/**
	 * 指定贴图大小，<strong>不建议在构造函数/initGui()之外使用。</strong><br><br>
	 * Appoints the texture size, <strong>it's not recommended to use this 
	 * outside of the constructor/initGui().</strong>
	 */
	public void setTextureSize(int width, int height) {
		if (width <= 0 || height <= 0) {
			throwArgumentException("texture width and texture height must be positive");
		}
		this.texWidth = width;
		this.texHeight = height;
	}

	/**
	 * 设置这个Gui的贴图位置。<br><br>
	 * Sets the texture location of this Gui.
	 */
	public void setTexture(ResourceLocation texture) {
		if (texture == null)
			throwArgumentException("texture can't be null");
		TEXTURE = texture;
	}

	/**
	 * 获取这个Gui的标题。<br><br>
	 * Gets the title of this Gui.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 设置显示在界面上边中间的标题，<strong>不支持多行</strong>。<br><br>
	 * Set the title of the interface, which is displayed in the middle of the upper
	 * part of the interface by default, <strong>doesn't support multi line yet</strong>.
	 * 
	 */
	public void setTitle(String title) {
		if (title == null)
			throwArgumentException("title can't be null");
		this.title = title;
	}

	/**
	 * 获取Gui在被打开时是否会强制修改MC界面缩放设置。<br><br>
	 * Gets whether the Gui will force the interface scaling settings to be modified
	 * when it is opened
	 */
	public boolean whetherToChangeGuiScaleWhenOpening() {
		return forceChangeGuiScale;
	}

	/**
	 * 指定Gui是否有自定义背景，如果它为true, <code>drawDefaultBackground()</code>
	 * 方法将不会被自动执行。<br><br>
	 * Appoints whether the Gui has a custom background If it is
	 * true, the <code>drawDefaultBackground()</code> method will not be executed 
	 * automaticly.
	 */
	public void setHasCustomBackground(boolean b) {
		this.hasCustomBackground = b;
	}
	
	protected List<GuiButton> getButtonList() {
		return this.buttonList;
	}
	
	@Override
	public void addControl(GuiControl control) {
		this.controls.put(nextId++, control);
	}
	
	@Override
	public void removeControl(GuiControl control) {
		this.controls.remove(control.getId());
	}

}
