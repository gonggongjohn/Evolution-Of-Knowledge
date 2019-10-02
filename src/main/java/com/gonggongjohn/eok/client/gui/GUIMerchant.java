package com.gonggongjohn.eok.client.gui;

import org.lwjgl.opengl.GL11;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.utils.ButtonBuilder;
import com.gonggongjohn.eok.utils.ButtonData;
import com.gonggongjohn.eok.utils.Colors;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GUIMerchant extends GuiContainer {

	public final ResourceLocation TEXTURE = new ResourceLocation(EOK.MODID + ":textures/gui/container/merchant.png");

	public static final int BUTTON_LEFT = 0;
	public static final int BUTTON_RIGHT = 1;
	public static final int BUTTON_TRADE = 2;
	public static final int DEAL_ITEM_1 = 3;
	public static final int DEAL_ITEM_2 = 4;
	public static final int DEAL_ITEM_3 = 5;
	public static final int DEAL_ITEM_4 = 6;
	public static final int DEAL_ITEM_5 = 7;

	public GUIMerchant(Container inventorySlotsIn) {
		super(inventorySlotsIn);
		this.xSize = 186;
		this.ySize = 226;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(TEXTURE);
		int offsetX = (this.width - this.xSize) / 2;
		int offsetY = (this.height - this.xSize) / 2;
		this.drawTexturedModalRect(offsetX, offsetY, 0, 0, this.xSize, this.ySize);

	}

	@Override
	public void initGui() {
		super.initGui();

		int offsetX = (this.width - this.xSize) / 2;
		int offsetY = (this.height - this.xSize) / 2;

		// 再见 反人类的按钮代码
		/*
		 * this.buttonList.add(new GuiButton(BUTTON_LEFT, offsetX + 126, offsetY + 116,
		 * 22, 20, "<") {
		 * 
		 * @Override public void drawButton(Minecraft mc, int mouseX, int mouseY, float
		 * partialTicks) { if (this.visible) { GL11.glPushMatrix(); GL11.glColor4f(1.0F,
		 * 1.0F, 1.0F, 1.0F); mc.getTextureManager().bindTexture(TEXTURE); if (mouseX >=
		 * this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y
		 * + this.height) { this.drawTexturedModalRect(this.x, this.y, 209, 40,
		 * this.width, this.height); } else { this.drawTexturedModalRect(this.x, this.y,
		 * 187, 40, this.width, this.height); } FontRenderer fontrenderer =
		 * mc.fontRenderer; this.drawCenteredString(fontrenderer, this.displayString,
		 * this.x + this.width / 2, this.y + (this.height - 8) / 2, 0xE0E0E0);
		 * GL11.glPopMatrix(); } }
		 * 
		 * });
		 */

		// 我可比上面那坨好看多了
		// --------按钮--------

		ButtonData bd = new ButtonData();

		// 左翻页按钮
		bd.setPosX(offsetX + 126);
		bd.setPosY(offsetY + 116);
		bd.setWidth(22);
		bd.setHeight(20);
		bd.setTexture(TEXTURE);
		bd.setTextureX(187);
		bd.setTextureY(40);
		bd.setTextureX2(209);
		bd.setTextureY2(40);
		bd.setText("<");
		this.buttonList.add(ButtonBuilder.CommonButton(BUTTON_LEFT, bd));

		// 右翻页按钮
		bd.setPosX(offsetX + 154);
		bd.setPosY(offsetY + 116);
		bd.setWidth(22);
		bd.setHeight(20);
		bd.setTexture(TEXTURE);
		bd.setTextureX(187);
		bd.setTextureY(40);
		bd.setTextureX2(209);
		bd.setTextureY2(40);
		bd.setText(">");
		this.buttonList.add(ButtonBuilder.CommonButton(BUTTON_RIGHT, bd));

	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		// TODO 自动生成的方法存根
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
	}

	@Override
	public void onGuiClosed() {
		// TODO 自动生成的方法存根
		super.onGuiClosed();
	}

}
