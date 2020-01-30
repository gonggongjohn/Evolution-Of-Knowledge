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
	
	private int sec = 0;
	private int changeSec = 0;

	private int windowWidth;
	private int windowHeight;
	
	private int offsetX;
	private int offsetY;
	
	private int texWidth;
	private int texHeight;
	
	private GuiButton btnNext;
	private GuiButton btnPrevious;
	private GuiButton btnMenu2;
	private GuiButton btnMenu3;
	private GuiButton btnMenu4;
	private GuiButton btnMenu5;
	
	public static final int BUTTON_RIGHT = 0;
	public static final int BUTTON_LEFT = 1;
	public static final int BUTTON_MENU2 = 2;
	public static final int BUTTON_MENU3 = 3;
	public static final int BUTTON_MENU4 = 4;
	public static final int BUTTON_MENU5 = 5;

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
	    
	    switch(page) {
	    
		    case 0: {
		    	
			    mc.renderEngine.bindTexture(texture);
			    drawTexturedModalRect(offsetX + 77, offsetY + 40, 0, 0, 32, 32);  
			    
		    	break;
		    }
		    case 2: {
		    	
			    mc.renderEngine.bindTexture(texture);
			    drawTexturedModalRect(offsetX + 40, offsetY + 20, 35, 2, 32, 28);
			    
			    break;
		    }
		    case 3: {
		    	
			    mc.renderEngine.bindTexture(texture);
			    drawTexturedModalRect(offsetX + 40, offsetY + 20, 67, 2, 32, 28);
			    
			    break;
		    }
		    case 4: {
		    	
	            if((changeSec / 60) % 10 == 5) {
	                	
	              	System.out.println("  " + changeSec);
	              	changeSec = 0;
	            }
	            else {
	
	            	changeSec++;
	            	sec = changeSec / 60;
				    mc.renderEngine.bindTexture(texture);
				    drawTexturedModalRect(offsetX + 40, offsetY + 20, 100, 2, 16 * (1 + sec), 28);
	            }

			    break;
		    }
		    default: {
		    	
		    	break;
		    }
	    }
	    
	    super.drawScreen(mouseX, mouseY, partialTicks);
	    
	    if(page == 0) {
	    	
		    drawHoveringText("EOK Manual", offsetX + 55, offsetY + 90);
	    }
	    else {
	    	
		    fontRenderer.drawString("Page" + page, offsetX + 80, offsetY + 152, 0, false);
	    	//drawString(fontRenderer, "Page" + page, offsetX + 80, offsetY + 152, 0);
	    }
	    
	    //fontRenderer.drawStringWithShadow("Test String", offsetX, offsetY, 0);
	    
	    switch(page) {
	    
	    	case 0: {
	    		
	    		break;
	    	}
	    	case 1: {
	    		
	    		fontRenderer.drawString("Menu", offsetX + 80, offsetY + 20, 0, false);
	    		
	    		break;
	    	}
		    case 2: {
		    	
		    	fontRenderer.drawString("EOK手册", offsetX + 80, offsetY + 22, 0, false);
		    	fontRenderer.drawString("    EOK手册将一直伴随您，", offsetX + 40, offsetY + 60, 0, false);
		    	fontRenderer.drawString("引导，并协助你完成游戏。", offsetX + 40, offsetY + 70, 0, false);
			    
		    	break;
		    }
		    case 3: {
		    	
		    	fontRenderer.drawString("折射式望远镜", offsetX + 80, offsetY + 22, 0, false);
		    	fontRenderer.drawString("    有了它，能帮祝你更好的", offsetX + 40, offsetY + 60, 0, false);
		    	fontRenderer.drawString("观察这个世界。", offsetX + 40, offsetY + 70, 0, false);
		    	fontRenderer.drawString("    先在右下角的槽中放入", offsetX + 40, offsetY + 80, 0, false);
		    	fontRenderer.drawString("§f§n莎草纸§r", offsetX + 40, offsetY + 90, 0, true);
		    	fontRenderer.drawString("再点左下角的按钮记", offsetX + 67, offsetY + 90, 0, false);  
		    	fontRenderer.drawString("录下这个瞬间吧。", offsetX + 40, offsetY + 100, 0, false);  
		    	
		    	break;
		    }
		    case 4: {
		    	
		    	fontRenderer.drawString("清醒度", offsetX + 40, offsetY + 50, 0, false);
		    	fontRenderer.drawString("描述：", offsetX + 40, offsetY + 70, 0, false);
		    	fontRenderer.drawString("    在研究台伏案学习会降", offsetX + 40, offsetY + 80, 0, false);
		    	fontRenderer.drawString("低你的清醒度，如果清醒度为", offsetX + 40, offsetY + 90, 0, false);
		    	fontRenderer.drawString("零则无法继续点亮研究节点。", offsetX + 40, offsetY + 100, 0, false);
		    	fontRenderer.drawString("    睡觉可以恢复清醒度。", offsetX + 40, offsetY + 110, 0, false);
		    	
		    	break;
		    }
		    case 5: {
		    	
		    	drawHoveringText("别看了，全是鸽子", offsetX + 50, offsetY + 90);
		    	
		    	break;
		    }
		    default: {
		    	
		    	fontRenderer.drawString("待开发", offsetX + 80, offsetY + 80, 0, false);
		    	
		    	break;
		    }
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
	    
	    if(page == 1) {
	    	
	    	buttonList.add(setBtnMenu2(new GuiButton(2, offsetX + 44, offsetY + 35, 60, 20, "EOK手册")));
	    	buttonList.add(setBtnMenu3(new GuiButton(3, offsetX + 44, offsetY + 57, 60, 20, "折射式望远镜")));
	    	buttonList.add(setBtnMenu4(new GuiButton(4, offsetX + 44, offsetY + 79, 60, 20, "清醒度")));
	    	buttonList.add(setBtnMenu5(new GuiButton(5, offsetX + 44, offsetY + 105, 60, 20, "开发人员表")));
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
			
			default: {
				
	    		page = button.id;
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

	public GuiButton getBtnMenu2() {
		
		return btnMenu2;
	}

	public GuiButton setBtnMenu2(GuiButton btnMenu2) {
		
		this.btnMenu2 = btnMenu2;
		return btnMenu2;
	}

	public GuiButton getBtnMenu3() {
		
		return btnMenu3;
	}

	public GuiButton setBtnMenu3(GuiButton btnMenu3) {
		
		this.btnMenu3 = btnMenu3;
		return btnMenu3;
	}

	public GuiButton getBtnMenu4() {
		
		return btnMenu4;
	}

	public GuiButton setBtnMenu4(GuiButton btnMenu4) {
		
		this.btnMenu4 = btnMenu4;
		return btnMenu4;
	}

	public GuiButton getBtnMenu5() {
		
		return btnMenu5;
	}

	public GuiButton setBtnMenu5(GuiButton btnMenu5) {
		
		this.btnMenu5 = btnMenu5;
		return btnMenu5;
	}
}
