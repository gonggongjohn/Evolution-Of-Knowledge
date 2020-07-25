package com.gonggongjohn.eok.gui;

import org.lwjgl.opengl.GL11;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.containers.ContainerOriginalForgeFurnace;
import com.gonggongjohn.eok.tileEntities.TEOriginalForgeFurnace;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class GUIOriginalForgeFurnace extends GuiContainer {
	private ResourceLocation texture = new ResourceLocation(EOK.MODID, "textures/gui/guiOriginalForgeFurnace.png");
	private ContainerOriginalForgeFurnace container;

	public GUIOriginalForgeFurnace(ContainerOriginalForgeFurnace container) {
		super(container);
		this.container = container;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		int offsetX = (this.width - this.xSize) / 2;
		int offsetY = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(offsetX, offsetY, 0, 0, this.xSize, this.ySize);
		int height = (int) (container.getBurnTime() / 1600 * 14 + 1);
		this.drawTexturedModalRect(offsetX + 30, offsetY + 78 - height, 177, 14 - height, 14, height);
		float temp = container.getFire();
		int dis = (int) ((temp > 1500 ? 1500 : temp) / 1500 * 75 - 1);
		this.drawTexturedModalRect(offsetX + 48 + dis, offsetY + 63, 177, 15, 5, 17);
		fontRendererObj.drawString(I18n.format("tile.originalForgeFurnace.name"), offsetX + 5, offsetY + 5, 0x404040);
		fontRendererObj.drawString(I18n.format("item.heatable.temperature", (int) temp), offsetX + 49, offsetY + 54,
				0x404040);
	}
}