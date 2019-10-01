package com.gonggongjohn.eok.client.gui;

import com.gonggongjohn.eok.EOK;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GUIMerchant extends GuiContainer {

	public final ResourceLocation TEXTURE = new ResourceLocation(EOK.MODID + ":textures/gui/container/merchant.png");

	public GUIMerchant(Container inventorySlotsIn) {
		super(inventorySlotsIn);
		this.xSize = 206;
		this.ySize = 226;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(TEXTURE);
		int offsetX = (this.width - this.xSize) / 2;
		int offsetY = (this.height - this.xSize) / 2;
		this.drawTexturedModalRect(offsetX, offsetY, 0, 0, this.xSize, this.ySize);

	}

	@Override
	public void initGui() {
		// TODO 自动生成的方法存根
		super.initGui();
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
