package com.gonggongjohn.eok.gui;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.data.ResearchData;
import com.gonggongjohn.eok.utils.ResearchUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class IResearchSelector {
    private Minecraft client;
    private int width;
    private int height;
    private int left;
    private int top;
    private int totalShiftDistance = 0;
    private int selectedIndex = -1;
    private GuiScreen parent;
    private ResourceLocation componentTexture = new ResourceLocation(EOK.MODID, "textures/gui/guiResearchTableComponents.png");

    public IResearchSelector(Minecraft client, GuiScreen parent, int width, int height, int left, int top) {
        this.client = client;
        this.parent = parent;
        this.width = width;
        this.height = height;
        this.left = left;
        this.top = top;
    }

    public void drawScreen(int shiftDistance, int mouseX, int mouseY){
        if (this.client.theWorld != null)
        {
            //this.drawGradientRect(this.left, this.top, this.left + this.width, this.top + this.height, -1072689136, -804253680);
            Minecraft.getMinecraft().renderEngine.bindTexture(componentTexture);
            parent.drawTexturedModalRect(this.left + this.width - 8, this.top, 0, 56, 8, 128);
            totalShiftDistance = totalShiftDistance + shiftDistance;
            for(int i = 0; i < ResearchUtils.finishedResearch.size(); i++){
                drawSlot(i, ResearchUtils.finishedResearch.get(i), totalShiftDistance, mouseX, mouseY);
            }
        }
    }

    protected void drawGradientRect(int par1, int par2, int par3, int par4, int par5, int par6)
    {
        float f = (float)(par5 >> 24 & 255) / 255.0F;
        float f1 = (float)(par5 >> 16 & 255) / 255.0F;
        float f2 = (float)(par5 >> 8 & 255) / 255.0F;
        float f3 = (float)(par5 & 255) / 255.0F;
        float f4 = (float)(par6 >> 24 & 255) / 255.0F;
        float f5 = (float)(par6 >> 16 & 255) / 255.0F;
        float f6 = (float)(par6 >> 8 & 255) / 255.0F;
        float f7 = (float)(par6 & 255) / 255.0F;
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_F(f1, f2, f3, f);
        tessellator.addVertex((double)par3, (double)par2, 0.0D);
        tessellator.addVertex((double)par1, (double)par2, 0.0D);
        tessellator.setColorRGBA_F(f5, f6, f7, f4);
        tessellator.addVertex((double)par1, (double)par4, 0.0D);
        tessellator.addVertex((double)par3, (double)par4, 0.0D);
        tessellator.draw();
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    private void drawSlot(int index, int id, int totalShiftDistance, int mouseX, int mouseY){
        int x = left + 15 + (index % 5) * (TextureSizeData.paperButtonWeight + 5);
        int y = top + 10 + (index / 5) * (TextureSizeData.paperButtonHeight + 5) - totalShiftDistance;
        if(y >= top && y<= top + height) {
            int relx = mouseX - x, rely = mouseY - y;
            if(relx >= 0 && rely >= 0 && relx < TextureSizeData.paperButtonWeight && rely < TextureSizeData.paperButtonHeight && Mouse.isButtonDown(0)){
                selectedIndex = index;
                selectedAction(id);
            }
            if(index == selectedIndex) {
                if (ResearchData.utilResearches.contains(id)) {
                    parent.drawTexturedModalRect(x, y, 152, 30, TextureSizeData.paperButtonWeight, TextureSizeData.paperButtonHeight);
                } else {
                    parent.drawTexturedModalRect(x, y, 130, 30, TextureSizeData.paperButtonWeight, TextureSizeData.paperButtonHeight);
                }
            }
            else {
                if(ResearchData.utilResearches.contains(id)){
                    parent.drawTexturedModalRect(x, y, 152, 8, TextureSizeData.paperButtonWeight, TextureSizeData.paperButtonHeight);
                }
                else {
                    parent.drawTexturedModalRect(x, y, 130, 8, TextureSizeData.paperButtonWeight, TextureSizeData.paperButtonHeight);
                }
            }
            if (relx >= 0 && rely >= 0 && relx < TextureSizeData.paperButtonWeight && rely < TextureSizeData.paperButtonHeight)
            {
                String name = I18n.format("research" + id + ".name");
                String description = I18n.format("research" + id + ".description");
                parent.drawString(client.fontRenderer,  name, mouseX + 5, mouseY + 5, 0x404040);
                parent.drawString(client.fontRenderer,  description, mouseX + 5, mouseY + 14, 0x404040);
            }
        }
    }

    protected void selectedAction(int id){

    }
}
