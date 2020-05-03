package com.gonggongjohn.eok.api.gui.meta;

/**
 * 定义了与MetaGui相关的类使用的样式常量。<br><br>
 * Defines the style constants used by classes which is related
 * to MetaGui.
 */
public class MetaGuiConstants {
	/*
	 * 以下样式常量适用于MetaGuiScreen和MetaGuiContainer。
	 * The style constants below are for MetaGuiScreen and MetaGuiContainer.
	 */

	public static final int NORMAL 						= 	0;
	/**
	 * 它用来决定在Gui被打开是是否强制调小游戏界面缩放设置。<br><br>
	 * It is used to decide whether or not to force the interface zoom factor to be
	 * turned down when the Gui is opened.
	 * 
	 * @see MetaGuiScreen#forceChangeGuiScale
	 * @see MetaGuiScreen#MetaGuiScreen
	 * @see MetaGuiScreen#onGuiClosed
	 */
	public static final int GUI_FORCE_CHANGE_SCALE 		= 	0b00000001;
	/**
	 * 它用来决定Gui是否有自定义背景，如果它为true, <code>drawDefaultBackground()</code>
	 * 方法将不会被自动执行。<br><br>
	 * It is used to determine whether the Gui has a custom background If it is
	 * true, the <code>drawDefaultBackground()</code> method will not be executed 
	 * automaticly.
	 * 
	 * @see MetaGuiScreen#hasCustonBackground
	 */
	public static final int GUI_HAS_CUSTOM_BACKGROUND 	= 	0b00000010;
	/**
	 * 如果设置了这个样式，Gui在打开时将不会暂停游戏。<br><br>
	 * If this style is set, the Gui won't pause the game while opening.
	 * 
	 * @see MetaGuiScreen#MetaGuiScreen
	 * @see MetaGuiScreen#doesGuiPauseGame
	 */
	public static final int GUI_NOT_PAUSE_GAME 			= 	0b00000100;
}
