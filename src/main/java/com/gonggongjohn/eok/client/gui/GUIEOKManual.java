package com.gonggongjohn.eok.client.gui;

import com.gonggongjohn.eok.EOK;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import java.io.IOException;
import org.lwjgl.opengl.GL11;

public class GUIEOKManual extends GuiScreen {
	
	private ResourceLocation texture = new ResourceLocation(EOK.MODID + ":" + "textures/gui/container/label.png");
    private ResourceLocation textureBook = new ResourceLocation(EOK.MODID + ":" + "textures/gui/container/eok_manual.png");
    
	private int lastGuiScale;
	private int page = 0;

	private int windowWidth;
	private int windowHeight;
	
	private int offsetX;
	private int offsetY;
	
	private int texWidth;
	private int texHeight;
	
	private GuiButton btnNext;
	private GuiButton btnPrevious;
	
	public static final int BUTTON_RIGHT = 0;
	public static final int BUTTON_LEFT = 1;
	public static final int BUTTON_MID = 2;

    public GUIEOKManual() {
   	
    	super();
    }
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		
		offsetX = (this.width - 190) / 2;
	    offsetY = (this.height - 200) / 2;
		
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	    
		drawDefaultBackground();

	    mc.getTextureManager().bindTexture(textureBook);
	    drawTexturedModalRect(offsetX, offsetY, 0, 0, 190, 190);
	    
	    //this.drawHoveringText("Hello World", offsetX, offsetY);
	    
	    if(page == 0) {
	    	
		    mc.renderEngine.bindTexture(texture);
		    drawTexturedModalRect(offsetX + 77, offsetY + 40, 0, 0, 32, 32);
		    
	    }
	    
	    super.drawScreen(mouseX, mouseY, partialTicks);
	    
	    if(page == 0) {
	    	
		    drawHoveringText("EOK Manual", offsetX + 55, offsetY + 90);    
	    }
	    
	    //fontRenderer.drawStringWithShadow("Test String", offsetX, offsetY, 0);
	    
	    if(page > 0) {
	    	
		    fontRenderer.drawString("Page" + page, offsetX + 80, offsetY + 152, 0, false);
	    	//drawString(fontRenderer, "Page" + page, offsetX + 80, offsetY + 152, 0);
	    }
	    
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}

    @Override
    public void initGui() {
    	
    	buttonList.clear();
    	
    	super.initGui();
    	
	    offsetX = (this.width - 190) / 2;
	    offsetY = (this.height - 200) / 2;

	    
	    if(page < 9) {
	    	
		    buttonList.add(setBtnNext(new GuiButton(0, offsetX + 128, offsetY + 145, 16, 20, ">>")));
	    }
	    
	    if(page > 0) {
	    	
		    buttonList.add(setBtnPrevious(new GuiButton(1, offsetX + 44, offsetY + 145, 16, 20, "<<")));
	    }
    }
    
	@Override
	public void updateScreen() {
		
		super.updateScreen();
	}

	@Override
	public void onGuiClosed() {

		Minecraft.getMinecraft().gameSettings.guiScale = lastGuiScale;

		super.onGuiClosed();
	}

	@Override
	public void drawBackground(int tint) {
		
		super.drawBackground(tint);
	}
	
    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
    	
    	switch (button.id) {
    	
			case BUTTON_LEFT : {
				
				if(page > 0) {
					
					page--;
				}
				this.initGui();
				break;
			}
			
			case BUTTON_RIGHT : {
				
				if(page < 9) {
					
					page++;
				}
				this.initGui();
				break;
			}
    	}
    }

	public int getWindowWidth() {
		
		return windowWidth;
	}

	public int getWindowHeight() {
		
		return windowHeight;
	}
	
	public int getOffsetX() {
		
		return offsetX;
	}

	public int getOffsetY() {
		
		return offsetY;
	}
	
	public int getTexWidth() {
		
		return texWidth;
	}

	public void setTexWidth(int texWidth) {
		
		this.texWidth = texWidth;
	}

	public int getTexHeight() {
		
		return texHeight;
	}

	public void setTexHeight(int texHeight) {
		
		this.texHeight = texHeight;
	}

	public GuiButton getBtnNext() {
		
		return btnNext;
	}

	public GuiButton setBtnNext(GuiButton btnNext) {
		
		this.btnNext = btnNext;
		return btnNext;
	}

	public GuiButton getBtnPrevious() {
		
		return btnPrevious;
	}

	public GuiButton setBtnPrevious(GuiButton btnPrevious) {
		
		this.btnPrevious = btnPrevious;
		return btnPrevious;
	}
}
