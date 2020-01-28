package com.gonggongjohn.eok.client.gui;

import com.gonggongjohn.eok.inventory.ContainerEOKManual;
import com.gonggongjohn.eok.utils.ButtonBuilder;
import com.gonggongjohn.eok.utils.ButtonData;
import com.gonggongjohn.eok.utils.EOKManualData;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.OpenGlHelper;
//import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import java.io.IOException;

public class GUIEOKManual extends GuiContainer {
    
    private static final ResourceLocation TEXTUREBACK = new ResourceLocation(EOKManualData.getPath().get(0));
    private static final ResourceLocation TEXTURECOMP = new ResourceLocation(EOKManualData.getPath().get(1));
    
	public static final int BUTTON_LEFT = 0;
	public static final int BUTTON_RIGHT = 1;
	public static final int BUTTON_MID = 2;
    
	private ContainerEOKManual containerEOKManual;

    public GUIEOKManual(ContainerEOKManual inventorySlotsIn) {
    	
        super(inventorySlotsIn);
        
        this.xSize = 190;
        this.ySize = 190;
        this.setContainerEOKManual(inventorySlotsIn);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    	
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
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
        GL11.glPopMatrix();
    }

    @Override
    public void initGui() {
    	
        super.initGui();
        
        int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;
        ButtonData bd = new ButtonData();
        
		//Left
		bd.setPosX(offsetX + 40);
		bd.setPosY(offsetY + 150);
		
		bd.setWidth(33);
		bd.setHeight(11);
		bd.setTexture(TEXTURECOMP);
		
		bd.setTextureX(187);
		bd.setTextureY(75);
		bd.setTextureX2(187);
		bd.setTextureY2(86);
		
		bd.setText("上一页");
		this.buttonList.add(ButtonBuilder.CommonButton(BUTTON_LEFT, bd));

		//Right
		bd.setPosX(offsetX + 110);
		bd.setPosY(offsetY + 150);
		bd.setWidth(33);
		bd.setHeight(11);
		bd.setTexture(TEXTURECOMP);
		
		bd.setTextureX(187);
		bd.setTextureY(75);
		bd.setTextureX2(187);
		bd.setTextureY2(86);
		
		bd.setText("下一页");
		this.buttonList.add(ButtonBuilder.CommonButton(BUTTON_RIGHT, bd));
        
		//???
		bd.setPosX(offsetX + 75);
		bd.setPosY(offsetY + 30);
		bd.setWidth(32);
		bd.setHeight(32);
		bd.setTexture(TEXTURECOMP);
		
		bd.setTextureX(0);
		bd.setTextureY(0);
		bd.setTextureX2(0);
		bd.setTextureY2(0);

		bd.setText("");
		this.buttonList.add(ButtonBuilder.CommonButton(BUTTON_MID, bd));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
    	
    	switch (button.id) {
    	
			case BUTTON_LEFT : {
				
				//previousPage();
				break;
			}
			case BUTTON_RIGHT : {
				
				//nextPage();
				break;
			}
			
			case BUTTON_MID : {
				
				//Label Menu
				break;
			}
    	}
    }

	public ContainerEOKManual getContainerEOKManual() {
		
		return containerEOKManual;
	}

	public void setContainerEOKManual(ContainerEOKManual containerEOKManual) {
		
		this.containerEOKManual = containerEOKManual;
	}
    
    /*
	public void previousPage() {
		
		containerEOKManual.previousPage();
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("currentPage", containerEOKManual.currentPage);
		
		
	}

	public void nextPage() {
		
		containerEOKManual.nextPage();
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("currentPage", containerEOKManual.currentPage);
	}
	*/
}
