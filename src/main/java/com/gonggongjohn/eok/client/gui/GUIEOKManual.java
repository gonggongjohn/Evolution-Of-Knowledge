package com.gonggongjohn.eok.client.gui;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import com.gonggongjohn.eok.EOK;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

/*
 * La classse représantant l'interface graphique de manuel d'EOK
 * @author EOK - Cuckoo
 * @version 1.2
 */
public class GUIEOKManual extends GuiScreen {

	/**
	 * la source des labels du manuel
	 */
	private ResourceLocation texture = new ResourceLocation(EOK.MODID + ":" + "textures/gui/manual/label.png");
	/**
	 * la source de l'image du manuel
	 */
	private ResourceLocation textureBook = new ResourceLocation(EOK.MODID + ":" + "textures/gui/manual/book.png");
	/**
	 * la source des texts du manuel
	 */
	private ResourceLocation mdLocation = new ResourceLocation(EOK.MODID + ":" + "manual/eok_manual_info.md");
	
	/**
	 * Une list qui enregistre les lines dans la fichier
	 */
    private ArrayList<String> mdList = new ArrayList<>();
	/**
	 * la page
	 */
	private int page = 0;
	/**
	 * les deux compteurs du temps
	 */
	private int sec = 0;
	private int changeSec = 0;
	/**
	 * les deux offsets qui permettent de modifier la position de GUI
	 */
	private int offsetX;
	private int offsetY;
	/**
	 * les deux bouttons pour changer la page
	 */
	private GuiButton btnNext;
	private GuiButton btnPrevious;
	/**
	 * les quatre bouttons dans le menu
	 */
	private GuiButton btnMenu2;
	private GuiButton btnMenu3;
	private GuiButton btnMenu4;
	private GuiButton btnMenu5;
	/**
	 * la définition du nombre en utilisant après dans le choix
	 */
	public static final int BUTTON_RIGHT = 0;
	public static final int BUTTON_LEFT = 1;

	/**
	 * permet de lire des lines dans la fichier
	 * @param mdLocation
	 *            la source de la fichier eok_manual_info
	 * @return vrai si réusir, fause si non
	 */
    public boolean readMarkdownFile(ResourceLocation mdLocation) {

        try {
        	//éfacer tout les data dans la list
            mdList.clear();
            //permet de trouver le chemin pour la fichier eok_manual_info.md
            InputStreamReader inputStreamReader = new InputStreamReader(mc.getResourceManager().getResource(mdLocation).getInputStream(), "UTF-8");
            BufferedReader mdFile = new BufferedReader(inputStreamReader);
            //une chaîne de charactère qui enregistre une line
            String mdLine;
            mdList.add(" ");
            //une boucle permet de lire chaque line
            while((mdLine = mdFile.readLine()) != null) {

                if(mdLine.isEmpty()) {

                    mdList.add(" ");
                }
                else {

                    mdList.add(mdLine);
                }
            }
            
            mdList.add(" ");
            //fichier lire terminé
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
    
	/**
	 * Constructeur du Robot
	 */
    public GUIEOKManual() {
   	
    	super();
    }
    
	/**
	 * permet d'afficher sur l'écran
	 * 
	 * @param mouseX
	 * @param mouseY
	 * @param partialTicks
	 *         
	 */
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
	    //lire la fichier
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
	    //l'affichage de chaque fois deux pages
	    switch(page) {
	    
		    case 0: {
		    	//le symbole d'EOK
			    mc.renderEngine.bindTexture(texture);
			    drawTexturedModalRect(offsetX + 50, offsetY + 20, 0, 0, 32, 32);  
			    
		    	break;
		    }
		    case 2: {
		    	//le manuel d'EOK
			    mc.renderEngine.bindTexture(texture);
			    drawTexturedModalRect(offsetX + 20, offsetY + 20, 35, 2, 32, 28);
			    //le téléscope
			    mc.renderEngine.bindTexture(texture);
			    drawTexturedModalRect(offsetX + 140, offsetY + 20, 67, 2, 32, 28);   
			    
			    break;
		    }
		    case 3: {
		    	//un certain temps
		    	if((changeSec / 60) % 10 == 5) {
                	
	              	changeSec = 0;
	            }
	            else {
	
	            	changeSec++;
	            	sec = changeSec / 60;
	            	//la concience
				    mc.renderEngine.bindTexture(texture);
				    drawTexturedModalRect(offsetX + 20, offsetY + 20, 100, 2, 16 * (1 + sec), 28);
	            }

			    break;
		    }
		    default: {
		    	
		    	break;
		    }
	    }
	    //过两分钟就看不懂自己在干嘛？
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
	    //la couvecture
	    if(page == 0) {
	    	
	    	if(mouseX >= offsetX + 50  && mouseY >=  offsetY + 20 && mouseX < offsetX + 82 && mouseY < offsetY + 52) {
	    		
			    drawHoveringText("EOK Symbol", mouseX, mouseY);
	    	}
	    	fontRenderer.drawString("try mouse on the EOK Symbol", offsetX + 50, offsetY + 60, 0, false);
	    }
	    //l'affiche du nombre de page
	    else {
	    	
		    fontRenderer.drawString(mdList.get(20 * page - 9), offsetX + 40, offsetY + 156, 0, false);
		    fontRenderer.drawString(mdList.get(20 * page + 1), offsetX + 170, offsetY + 156, 0, false);
	    }
    	//Menu
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
	    else if(page != 0){
	    	//les contenus du manuel
		    fontRenderer.drawString(mdList.get((20 * (page - 1)) + 2), offsetX + 60, offsetY + 22, 0, false);  
		    for(int i = 0; i < 8; i++) {
	    		
	    		fontRenderer.drawString(mdList.get((20 * (page - 1)) + i + 3), offsetX + 10, offsetY + 60 + (10 * i), 0, false);
	    	}
		    fontRenderer.drawString(mdList.get((20 * page - 10) + 2), offsetX + 180, offsetY + 22, 0, false);
	    	for(int i = 0; i < 8; i++) {
	    		
	    		fontRenderer.drawString(mdList.get((20 * page - 10) + i + 3), offsetX + 130, offsetY + 60 + (10 * i), 0, false);
	    	}
	    }
	    
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}

	/**
	 * permet inisialiser l'écran
	 *         
	 */
    @Override
    public void initGui() {
    	
    	buttonList.clear();
    	
    	super.initGui();
    	//这两行要改！！！
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
    
	/**
	 * fermeture de l'écran
	 *         
	 */
	@Override
	public void onGuiClosed() {

		super.onGuiClosed();
	}
	
	/**
	 * afficher la texture de l'écran
	 *         
	 */
	@Override
	public void drawBackground(int tint) {
		
		super.drawBackground(tint);
	}
	
	/**
	 * les traitements des boutons
	 *         
	 */
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
					
				page = button.id / 2 + 1;
	    		this.initGui();
	    		
	    		break;
			}
    	}
    }
    
	/**
	 * getter de l'attribut offsetX
	 * 
	 * @return l'attribut offsetX
	 */
	public int getOffsetX() {
		
		return offsetX;
	}

	/**
	 * getter de l'attribut offsetY
	 * 
	 * @return l'attribut offsetY
	 */
	public int getOffsetY() {
		
		return offsetY;
	}

	/**
	 * getter de l'attribut btnNext
	 * 
	 * @return l'attribut btnNext
	 */
	public GuiButton getBtnNext() {
		
		return btnNext;
	}
	
	/**
	 * getter de l'attribut btnNext
	 * 
	 * @return l'attribut btnNext
	 */
	public GuiButton getBtnPrevious() {
		
		return btnPrevious;
	}

	/**
	 * setter de l'attribut btnNext
	 * 
	 * @param btnNext
	 * @return l'attribut btnNext
	 */
	public GuiButton setBtnNext(GuiButton btnNext) {
		
		this.btnNext = btnNext;
		return btnNext;
	}

	/**
	 * setter de l'attribut btnPrevious
	 * 
	 * @param btnPrevious
	 * @return l'attribut btnPrevious
	 */
	public GuiButton setBtnPrevious(GuiButton btnPrevious) {
		
		this.btnPrevious = btnPrevious;
		return btnPrevious;
	}
	
	/**
	 * getter de l'attribut btnMenu2
	 * 
	 * @return l'attribut btnMenu2
	 */
	public GuiButton getBtnMenu2() {
		
		return btnMenu2;
	}

	/**
	 * setter de l'attribut btnMenu2
	 * 
	 * @param btnMenu2
	 * @return l'attribut btnMenu2
	 */
	public GuiButton setBtnMenu2(GuiButton btnMenu2) {
		
		this.btnMenu2 = btnMenu2;
		return btnMenu2;
	}

	/**
	 * getter de l'attribut btnMenu3
	 * 
	 * @return l'attribut btnMenu3
	 */
	public GuiButton getBtnMenu3() {
		
		return btnMenu3;
	}

	/**
	 * setter de l'attribut btnMenu3
	 * 
	 * @param btnMenu3
	 * @return l'attribut btnMenu3
	 */
	public GuiButton setBtnMenu3(GuiButton btnMenu3) {
		
		this.btnMenu3 = btnMenu3;
		return btnMenu3;
	}

	/**
	 * getter de l'attribut btnMenu4
	 *
	 * @return l'attribut btnMenu4
	 */
	public GuiButton getBtnMenu4() {
		
		return btnMenu4;
	}

	/**
	 * setter de l'attribut btnMenu4
	 * 
	 * @return l'attribut btnMenu4
	 */
	public GuiButton setBtnMenu4(GuiButton btnMenu4) {
		
		this.btnMenu4 = btnMenu4;
		return btnMenu4;
	}

	/**
	 * getter de l'attribut btnMenu5
	 * 
	 * @return l'attribut btnMenu5
	 */
	public GuiButton getBtnMenu5() {
		
		return btnMenu5;
	}

	/**
	 * setter de l'attribut btnMenu5
	 * 
	 * @param btnMenu5
	 * @return l'attribut btnMenu5
	 */
	public GuiButton setBtnMenu5(GuiButton btnMenu5) {
		
		this.btnMenu5 = btnMenu5;
		return btnMenu5;
	}
}