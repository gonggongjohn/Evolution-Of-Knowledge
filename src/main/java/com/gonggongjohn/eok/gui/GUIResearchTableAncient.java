package com.gonggongjohn.eok.gui;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.containers.ContainerResearchTableAncient;
import com.gonggongjohn.eok.data.ResearchData;
import com.gonggongjohn.eok.tileEntities.TEResearchTableAncient;
import com.gonggongjohn.eok.utils.ResearchUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Random;

public class GUIResearchTableAncient extends GuiContainer{
    private ResourceLocation backTextureL = new ResourceLocation(EOK.MODID, "textures/gui/guiResearchTableAncientBackgroundLeftFR.png");
    private ResourceLocation backTextureR = new ResourceLocation(EOK.MODID, "textures/gui/guiResearchTableAncientBackgroundRight.png");
    private ResourceLocation componentTexture = new ResourceLocation(EOK.MODID, "textures/gui/guiResearchTableComponents.png");
    private ResourceLocation paperTexture;
    private int offsetX, offsetY;
    private int x,y;
    private InventoryPlayer inventory;
    private TEResearchTableAncient te;

    public GUIResearchTableAncient(TEResearchTableAncient te, EntityPlayer player) {
        super(new ContainerResearchTableAncient(te, player));
        inventory = player.inventory;
        this.te = te;
        this.xSize = 360;
        this.ySize = 210;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        Minecraft.getMinecraft().renderEngine.bindTexture(backTextureL);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        x = (width - xSize) / 2;
        y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 56, 0, xSize / 2, ySize);
        Minecraft.getMinecraft().renderEngine.bindTexture(backTextureR);
        drawTexturedModalRect(x + xSize / 2, y, 20, 0, xSize / 2, ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX,int mouseY){

    }

    @Override
    public void initGui(){
        super.initGui();
        offsetX = (this.width - this.xSize) / 2;
        offsetY = (this.height - this.ySize) / 2;
        this.buttonList.add(new GuiButton(0, offsetX + 55, offsetY + 182, 64, 16, "") {
            @Override
            public void drawButton(Minecraft mc, int mouseX, int mouseY) {
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                Minecraft.getMinecraft().renderEngine.bindTexture(componentTexture);
                this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 8, this.width, this.height);
                String title = I18n.format("button.startResearch.title");
                this.drawString(mc.fontRenderer, title, this.xPosition + (this.width - mc.fontRenderer.getStringWidth(title)) / 2, this.yPosition + 4, 0x404040);
            }
        });
    }

    @Override
    protected void actionPerformed(GuiButton button) {

    }
}

