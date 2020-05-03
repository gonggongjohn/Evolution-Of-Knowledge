package com.gonggongjohn.eok.api.gui.meta;

import net.minecraft.client.Minecraft;

public interface IMetaGui {
	
	/**
	 * 获取这个Gui的所有控件及其对应的ID。<br><br>
	 * Gets all the controls of this Gui and their corresponding IDs.
	 */
	public ControlMap getControls();
	
	/**
	 * 将一个控件添加至Gui中并返回它本身。<br><br>
	 * Adds a control to this Gui and returns itself.
	 * @return 
	 */
	public GuiControl addControl(GuiControl control);
	
	/**
	 * 将一个控件从Gui中移除。<br><br>
	 * Removes a control from this Gui.
	 */
	public void removeControl(GuiControl control);
	
	/**
	 * 获取游戏窗口的宽。<br><br>
	 * Gets the width of the game screen.
	 */
	default public int getScreenWidth() {
		return Minecraft.getMinecraft().displayWidth;
	}

	/**
	 * 获取游戏窗口的高。<br><br>
	 * Gets the height of the game screen.
	 */
	default public int getScreenHeight() {
		return Minecraft.getMinecraft().displayHeight;
	}
	
}
