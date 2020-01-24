package com.gonggongjohn.eok.client.gui;

import org.lwjgl.opengl.GL11;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.utils.Colors;

import net.minecraft.util.ResourceLocation;

/**
 * 这是MetaGUIScreen的示例
 * 在游戏内输入命令/eoktestscreen可打开该界面
 * 
 * @see MetaGUIScreen
 */
public class GUIScreenTest extends MetaGUIScreen {

	public GUIScreenTest() {
		super(true);
		this.setTexture(new ResourceLocation(EOK.MODID + ":textures/gui/screen/test_screen.png"));
		this.setTextureSize(800, 800);
		this.setWindowSize(800, 420);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(this.getTexture());
		this.drawCenteredString(this.fontRenderer, "Meta Gui Screen Test", this.getOffsetX() + this.getWindowWidth() / 2, this.getOffsetY() + 10, Colors.DEFAULT_BLACK);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}
	
}
