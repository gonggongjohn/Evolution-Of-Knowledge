package com.gonggongjohn.eok.client.gui;

import org.lwjgl.opengl.GL11;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.api.gui.meta.GuiControl;
import com.gonggongjohn.eok.api.gui.meta.MetaGuiScreen;
import com.gonggongjohn.eok.utils.DocumentRenderer;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

/**
 * This is an example of MetaGuiScreen
 * Send /eoktestscreen command in the game to open this interface
 * 
 * @see MetaGuiScreen
 */
public class GuiScreenTest extends MetaGuiScreen {

	private GuiControl.GuiButton pageUp;
	private GuiControl.GuiButton pageDown;
	protected DocumentRenderer renderer;
	protected int pages;
	private int pageIndex;
	
	public GuiScreenTest() {
		super(true);
		this.setTexture(new ResourceLocation(EOK.MODID + ":textures/gui/screen/test_screen.png"));
		this.setTextureSize(512, 512);
		this.setWindowSize(279, 180);
		this.setTitle("");
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		if(!renderer.isAvailable())
			return;
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(this.getTexture());
		renderer.draw(pageIndex, DocumentRenderer.Side.LEFT, this.getOffsetX(), this.getOffsetY());
		if(pageIndex + 1 < pages) 
			renderer.draw(pageIndex + 1, DocumentRenderer.Side.RIGHT, this.getOffsetX(), this.getOffsetY());
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}

	@Override
	public void initGui() {
		super.initGui();
		this.initDocument();
		pageUp = builder.createButton(13, 151, 20, 20, 0, 181, 21, 181, this::pageUp);
		pageDown = builder.createButton(248, 151, 20, 20, 0, 202, 21, 202, this::pageDown);
		this.addControl(pageUp);
		this.addControl(pageDown);
	}
	
	private void pageUp(MetaGuiScreen gui) {
		if(this.pageIndex < 2) return;
		this.pageIndex -= 2;
	}
	
	private void pageDown(MetaGuiScreen gui) {
		if(this.pageIndex + 2 >= this.pages) return;
		this.pageIndex += 2;
	}

	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		renderer.remove();
	}
	
	protected void initDocument() {
		renderer = new DocumentRenderer(17, 13, 149, 13, 115, 135, EOK.MODID + ":manual/index/index.edt");
		pages = renderer.getPages();
	}
}
