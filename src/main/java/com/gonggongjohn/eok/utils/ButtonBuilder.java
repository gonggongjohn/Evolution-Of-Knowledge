package com.gonggongjohn.eok.utils;

import com.github.zi_jing.cuckoolib.client.gui.Colors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ButtonBuilder {
	
	/**
	 * @param id 按钮ID
	 * @param buttondata 按钮信息（可以在多个按钮上使用同一个buttondata）
	 * @return
	 */
	public static GuiButton CommonButton(int id, ButtonData buttondata) {

		final int posX = buttondata.getPosX();
		final int posY = buttondata.getPosY();
		final int width = buttondata.getWidth();
		final int height = buttondata.getHeight();
		final int textureX = buttondata.getTextureX();
		final int textureY = buttondata.getTextureY();
		final int textureX2 = buttondata.getTextureX2();
		final int textureY2 = buttondata.getTextureY2();
		final ResourceLocation TEXTURE = buttondata.getTexture();
		final String text = buttondata.getText();
		final int txtcolor = buttondata.getTxtcolor();
		final boolean hasCustomTxtColor = buttondata.isCustomTxtColor();

		GuiButton button = new GuiButton(id, posX, posY, width, height, text) {

			@Override
			public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
				if (this.visible) {

					GL11.glPushMatrix();
					GlStateManager.enableBlend();
			        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
			        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					mc.getTextureManager().bindTexture(TEXTURE);

					if (mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width
							&& mouseY < this.y + this.height) {
						this.drawTexturedModalRect(this.x, this.y, textureX2, textureY2, this.width, this.height);
					} else {
						this.drawTexturedModalRect(this.x, this.y, textureX, textureY, this.width, this.height);
					}

					// 如果按钮上的字符串为空则不渲染文字
					if (!text.equals("")) {

						FontRenderer fontrenderer = mc.fontRenderer;

						// 默认文字颜色
						if (!hasCustomTxtColor) {

							if (mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width
									&& mouseY < this.y + this.height) {
								this.drawCenteredString(fontrenderer, text, this.x + this.width / 2,
										this.y + (this.height - 8) / 2, Colors.MOUSEON_TEXT);
							} else {
								this.drawCenteredString(fontrenderer, text, this.x + this.width / 2,
										this.y + (this.height - 8) / 2, Colors.DEFAULT_TEXT);
							}
						} else {
							// 自定义颜色
							this.drawCenteredString(fontrenderer, text, this.x + this.width / 2,
									this.y + (this.height - 8) / 2, txtcolor);
						}
					}

					GL11.glPopMatrix();

				}
			}

		};

		return button;
	}

}
