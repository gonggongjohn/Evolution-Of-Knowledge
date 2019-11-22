package com.gonggongjohn.eok.client.gui;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.capabilities.IResearchData;
import com.gonggongjohn.eok.handlers.CapabilityHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.ArrayList;

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
    public void drawScreen(int mouseX, int mouseY, float partialTicks){
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) { GL11.glPushMatrix();
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
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
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

        EntityPlayer player = Minecraft.getMinecraft().player;
        ArrayList<Integer> finList = new ArrayList<Integer>();
        if(player.hasCapability(CapabilityHandler.capResearchData, null)){
            IResearchData researchData = player.getCapability(CapabilityHandler.capResearchData, null);
            finList = researchData.getFinishedResearch();
            if(finList.size() != 0) {
                for (int i = 1; i <= finList.size(); i++) {
                    this.buttonList.add(new ButtonElementaryResearchTable(i, finList.get(i - 1), offsetX + calcButtonLeftPos(i), offsetY + calcButtonTopPos(i), 32, 32, offsetY));
                }
            }

        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        //TODO
    }

    private int calcButtonLeftPos(int index){
        if (index % 2 != 0) return 9;
        else return 42;
    }

    private int calcButtonTopPos(int index){
        if (index % 2 == 0) return 9 + 36 * (index / 2 - 1);
        else return 9 + 36 * (index / 2);
    }
}
