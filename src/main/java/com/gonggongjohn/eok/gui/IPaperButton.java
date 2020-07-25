package com.gonggongjohn.eok.gui;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.data.ResearchData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class IPaperButton extends GuiButton {
    private ResourceLocation componentTexture = new ResourceLocation(EOK.MODID, "textures/gui/guiResearchTableComponents.png");
    public int representResearchID;
    private boolean justFinish = false;

    public IPaperButton(int p_i1021_1_, int p_i1021_2_, int p_i1021_3_, int p_i1021_4_, int p_i1021_5_, String p_i1021_6_, int researchID) {
        super(p_i1021_1_, p_i1021_2_, p_i1021_3_, p_i1021_4_, p_i1021_5_, p_i1021_6_);
        this.representResearchID = researchID;
    }


    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY){
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().renderEngine.bindTexture(componentTexture);
        if (ResearchData.utilResearches.contains(representResearchID)) {
            drawTexturedModalRect(this.xPosition, this.yPosition, 152, 30, TextureSizeData.paperButtonWeight, TextureSizeData.paperButtonHeight);
        }
        else if(justFinish){
            drawTexturedModalRect(this.xPosition, this.yPosition, 108, 30, TextureSizeData.paperButtonWeight, TextureSizeData.paperButtonHeight);
        }
        else {
            drawTexturedModalRect(this.xPosition, this.yPosition, 130, 30, TextureSizeData.paperButtonWeight, TextureSizeData.paperButtonHeight);
        }
        int relx = mouseX - this.xPosition, rely = mouseY - this.yPosition;
        if (relx >= 0 && rely >= 0 && relx < this.width && rely < this.height)
        {
            String name = I18n.format("research" + representResearchID + ".name");
            String description = I18n.format("research" + representResearchID + ".description");
            drawString(mc.fontRenderer,  name, mouseX + 5, mouseY + 5, 0x404040);
            drawString(mc.fontRenderer,  description, mouseX + 5, mouseY + 14, 0x404040);
        }
    }

    public IPaperButton setJustFinish(){
        this.justFinish = true;
        return this;
    }
}
