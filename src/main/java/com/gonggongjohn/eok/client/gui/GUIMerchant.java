package com.gonggongjohn.eok.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

public class GUIMerchant extends GuiContainer{

	public GUIMerchant(Container inventorySlotsIn) {
		super(inventorySlotsIn);
		// TODO 自动生成的构造函数存根
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		// TODO 自动生成的方法存根
		
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
