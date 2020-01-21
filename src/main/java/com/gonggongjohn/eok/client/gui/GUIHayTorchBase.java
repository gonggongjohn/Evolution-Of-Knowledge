package com.gonggongjohn.eok.client.gui;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.inventory.ContainerHayTorchBase;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
//import org.lwjgl.opengl.GL11;

public class GUIHayTorchBase extends GuiContainer {
	
    private static final String TEXTURE_BACK = EOK.MODID + ":" + "textures/gui/container/hay_torch_base.png";  
    private static final ResourceLocation TEXTUREBACK = new ResourceLocation(TEXTURE_BACK);   
    
    public GUIHayTorchBase(ContainerHayTorchBase inventorySlotIn) {
    	
        super(inventorySlotIn);
    }
    
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    	
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
    	
    	/*
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        */
        
        this.mc.getTextureManager().bindTexture(TEXTUREBACK);
        int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(offsetX,offsetY,0,0,xSize,ySize);
        
        //GL11.glPopMatrix();
    }
    
    @Override
    public void initGui() {
    	
        super.initGui();
    }
}
