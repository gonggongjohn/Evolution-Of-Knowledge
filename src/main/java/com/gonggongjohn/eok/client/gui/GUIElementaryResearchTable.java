package com.gonggongjohn.eok.client.gui;

import com.gonggongjohn.eok.EOK;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

public class GUIElementaryResearchTable extends GuiContainer {
    private static final String TEXTURE_BACK = EOK.MODID + ":" + "textures/gui/container/elementary_research_table.png";
    private static final String TEXTURE_COMP = EOK.MODID + ":" + "textures/gui/container/elementary_research_table_components.png";
    private static final ResourceLocation TEXTUREBACK = new ResourceLocation(TEXTURE_BACK);
    private static final ResourceLocation TEXTURECOMP = new ResourceLocation(TEXTURE_COMP);
    public GUIElementaryResearchTable(Container inventorySlotsIn) {
        super(inventorySlotsIn);
        this.xSize = 256;
        this.ySize = 192;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TEXTUREBACK);
        int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;

        this.drawTexturedModalRect(offsetX, offsetY, 0, 0, this.xSize, this.ySize);
        this.drawTexturedModalRect(offsetX + 10, offsetY + 148, 0, 33, 90, 16);
        GL11.glPopMatrix();
    }

    @Override
    public void initGui() {
        super.initGui();
        int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;
        this.buttonList.add(new GuiButton(0, offsetX + 219, offsetY + 129, 32, 32, ""){
            @Override
            public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks){
                if(this.visible){
                    GL11.glPushMatrix();
                    GL11.glEnable(GL11.GL_BLEND);
                    OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    mc.getTextureManager().bindTexture(TEXTURECOMP);
                    this.drawTexturedModalRect(this.x, this.y, 0, 0, this.width, this.height);
                    GL11.glPopMatrix();
                }
            }
        });
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        //TODO
    }
}
