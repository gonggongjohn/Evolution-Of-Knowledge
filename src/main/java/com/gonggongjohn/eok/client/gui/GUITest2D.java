package com.gonggongjohn.eok.client.gui;

import org.lwjgl.opengl.GL11;

import com.gonggongjohn.eok.EOK;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public class GUITest2D extends GuiScreen {
    private ResourceLocation background = new ResourceLocation(EOK.MODID + ":" + "textures/gui/container/book.png");
    private int offsetX, offsetY;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.offsetX = (this.width - 249) / 2;
        this.offsetY = (this.height - 175) / 2;
        super.drawScreen(mouseX, mouseY, partialTicks);
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        drawDefaultBackground();
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(offsetX, offsetY, 0, 0, 249, 175);
        mc.fontRenderer.drawString("123&l456", offsetX + 10, offsetY + 10, 0xFF0000);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }
}
