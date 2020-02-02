package com.gonggongjohn.eok.client.gui;

import com.gonggongjohn.eok.EOK;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.lwjgl.opengl.GL11;

public class GUIEOKManual extends GuiScreen {

	private ResourceLocation texture = new ResourceLocation(EOK.MODID + ":" + "textures/gui/container/label.png");
    private ResourceLocation textureBook = new ResourceLocation(EOK.MODID + ":" + "textures/gui/container/book.png");
    private ResourceLocation mdLocation = new ResourceLocation(EOK.MODID + ":" + "manual/eok_manual_info.md");
	
    private ArrayList<String> mdList = new ArrayList<>();
    private ArrayList<String> mdListType = new ArrayList<>();

	private int page = 0;
	private int sec = 0;
	private int changeSec = 0;
	private int lastGuiScale;
	private int offsetX;
	private int offsetY;
	
	private GuiButton btnNext;
	private GuiButton btnPrevious;
	private GuiButton btnMenu2;
	private GuiButton btnMenu3;
	private GuiButton btnMenu4;
	private GuiButton btnMenu5;
	
	public static final int BUTTON_RIGHT = 0;
	public static final int BUTTON_LEFT = 1;
    
    public boolean readMarkdownFile(ResourceLocation mdLocation) {

        try {

            mdList.clear();
            mdListType.clear();

            InputStreamReader inputStreamReader = new InputStreamReader(mc.getResourceManager().getResource(mdLocation).getInputStream(), "UTF-8");
            BufferedReader mdFile = new BufferedReader(inputStreamReader);

            String mdLine;
            mdList.add(" ");

            while((mdLine = mdFile.readLine()) != null) {

                if(mdLine.isEmpty()) {

                    mdList.add(" ");
                }
                else {

                    mdList.add(mdLine);
                }
            }
            
            mdList.add(" ");
            mdFile.close();

            return true;
        }
		catch(FileNotFoundException e) {
			
			System.out.println("File Not Found");		
			return false;
		}
        catch (IOException e) {
        	
			System.out.println("Read Error");
			return false;
		}
    }

    public GUIEOKManual() {
   	
    	super();
    }
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
	    
    	readMarkdownFile(mdLocation);
		
		offsetX = (this.width - 249) / 2;
	    offsetY = (this.height - 175) / 2;
		
	    super.drawScreen(mouseX, mouseY, partialTicks);
	    
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		drawDefaultBackground();

	    mc.getTextureManager().bindTexture(textureBook);
	    drawTexturedModalRect(offsetX, offsetY, 0, 0, 249, 175);
	    switch(page) {
	    
		    case 0: {
		    	
			    mc.renderEngine.bindTexture(texture);
			    drawTexturedModalRect(offsetX + 50, offsetY + 20, 0, 0, 32, 32);  
			    
		    	break;
		    }
		    case 2: {
		    	
			    mc.renderEngine.bindTexture(texture);
			    drawTexturedModalRect(offsetX + 20, offsetY + 20, 35, 2, 32, 28);
			    
			    break;
		    }
		    case 3: {
		    	
			    mc.renderEngine.bindTexture(texture);
			    drawTexturedModalRect(offsetX + 20, offsetY + 20, 67, 2, 32, 28);
			    
			    break;
		    }
		    case 4: {
		    	
	            if((changeSec / 60) % 10 == 5) {
	                	
	              	changeSec = 0;
	            }
	            else {
	
	            	changeSec++;
	            	sec = changeSec / 60;
				    mc.renderEngine.bindTexture(texture);
				    drawTexturedModalRect(offsetX + 20, offsetY + 20, 100, 2, 16 * (1 + sec), 28);
	            }

			    break;
		    }
		    default: {
		    	
		    	break;
		    }
	    }
	    
		if (mouseX >= offsetX + 7 && mouseY >=  offsetY + 147 && mouseX < offsetX + 40 && mouseY < offsetY + 169) {
			
			if(page != 0) {
				
				mc.renderEngine.bindTexture(textureBook);
				drawTexturedModalRect(offsetX + 7, offsetY + 147, 53, 473, 33, 22);
			}
			
			if(page != 9) {
				
				mc.renderEngine.bindTexture(textureBook);
				drawTexturedModalRect(offsetX + 207, offsetY + 147, 9, 441, 33, 22);
			}
		} 
		else if (mouseX >= offsetX + 207 && mouseY >=  offsetY + 147 && mouseX < offsetX + 240 && mouseY < offsetY + 169) {
			
			if(page != 9) {
				
				mc.renderEngine.bindTexture(textureBook);
				drawTexturedModalRect(offsetX + 207, offsetY + 147, 53, 441, 33, 22);
			}
			
			if(page != 0) {
				
				mc.renderEngine.bindTexture(textureBook);
				drawTexturedModalRect(offsetX + 7, offsetY + 147, 9, 473, 33, 22);
			}
		} 
		else {
			
			if(page != 0) {
				
				mc.renderEngine.bindTexture(textureBook);
				drawTexturedModalRect(offsetX + 7, offsetY + 147, 9, 473, 33, 22);
			}
			
			if(page != 9) {
				
				mc.renderEngine.bindTexture(textureBook);
				drawTexturedModalRect(offsetX + 207, offsetY + 147, 9, 441, 33, 22);
			}
		}
	    
	    if(page == 0) {
	    	
	    	if(mouseX >= offsetX + 50  && mouseY >=  offsetY + 20 && mouseX < offsetX + 82 && mouseY < offsetY + 52) {
	    		
			    drawHoveringText("EOK Symbol", mouseX, mouseY);
	    	}
	    	fontRenderer.drawString("try mouse on the EOK Symbol", offsetX + 50, offsetY + 60, 0, false);
	    }
	    else {
	    	
		    fontRenderer.drawString(mdList.get(10 * page + 1), offsetX + 40, offsetY + 156, 0, false);
	    }
    	
	    if(page == 1) {
	    	
	    	for(int i = 0; i < 4; i++) {
		    	
	    		fontRenderer.drawString(mdList.get((10 * page) + i * 2 + 2), offsetX + 11, offsetY + 35 + (22 * i), 0, false);
	    	}
	    	
			if (mouseX >= offsetX + 11 && mouseY >=  offsetY + 35 && mouseX < offsetX + 71 && mouseY < offsetY + 43) {
				
				fontRenderer.drawString(mdList.get((10 * page) + 3), offsetX + 11, offsetY + 35, 0, false);
			}
			if (mouseX >= offsetX + 11 && mouseY >=  offsetY + 57 && mouseX < offsetX + 71 && mouseY < offsetY + 65) {
				
				fontRenderer.drawString(mdList.get((10 * page) + 5), offsetX + 11, offsetY + 57, 0, false);
			}
			if (mouseX >= offsetX + 11 && mouseY >=  offsetY + 79 && mouseX < offsetX + 71 && mouseY < offsetY + 87) {
				
				fontRenderer.drawString(mdList.get((10 * page) + 7), offsetX + 11, offsetY + 79, 0, false);
			}
			if (mouseX >= offsetX + 11 && mouseY >=  offsetY + 101 && mouseX < offsetX + 71 && mouseY < offsetY + 113) {
				
				fontRenderer.drawString(mdList.get((10 * page) + 9), offsetX + 11, offsetY + 101, 0, false);
			}
	    }
	    else {

		    fontRenderer.drawString(mdList.get((10 * page) + 2), offsetX + 60, offsetY + 22, 0, false);
	    	for(int i = 0; i < 8; i++) {
	    		
	    		fontRenderer.drawString(mdList.get((10 * page) + i + 3), offsetX + 11, offsetY + 60 + (10 * i), 0, false);
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
	    	
		    buttonList.add(setBtnNext(new GuiButton(0, offsetX + 177, offsetY + 159, 35, 23, "")));
	    }
	    
	    if(page > 0) {

		    buttonList.add(setBtnPrevious(new GuiButton(1, offsetX - 22, offsetY + 159, 33, 23, "")));
	    }

	    if(page == 1) {

	    	buttonList.add(setBtnMenu2(new GuiButton(2, offsetX - 18, offsetY + 46, 60, 9, "")));
	    	buttonList.add(setBtnMenu3(new GuiButton(3, offsetX - 18, offsetY + 68, 60, 9, "")));
	    	buttonList.add(setBtnMenu4(new GuiButton(4, offsetX - 18, offsetY + 90, 60, 9, "")));
	    	buttonList.add(setBtnMenu5(new GuiButton(5, offsetX - 18, offsetY + 113, 60, 9, "")));
	    }
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
	
	public int getOffsetX() {
		
		return offsetX;
	}

	public int getOffsetY() {
		
		return offsetY;
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