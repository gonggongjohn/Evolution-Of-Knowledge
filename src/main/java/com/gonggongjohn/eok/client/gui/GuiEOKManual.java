package com.gonggongjohn.eok.client.gui;

import java.io.IOException;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.api.gui.meta.GuiControl;
import com.gonggongjohn.eok.api.gui.meta.MetaGuiConstants;
import com.gonggongjohn.eok.api.gui.meta.MetaGuiScreen;
import com.gonggongjohn.eok.api.render.GLUtils;
import com.gonggongjohn.eok.utils.DocumentRenderer;

import net.minecraft.util.ResourceLocation;

/**
 * This is an example of MetaGuiScreen
 * Send /eoktestscreen command in the game to open this interface
 * 
 * @see MetaGuiScreen
 */
public class GuiEOKManual extends MetaGuiScreen {

	private GuiControl.Button pageUp;
	private GuiControl.Button pageDown;
	protected DocumentRenderer renderer;
	private int pageIndex;
	private boolean initialized = false;
	
	public GuiEOKManual() {
		super(MetaGuiConstants.NORMAL);
		this.setTexture(new ResourceLocation(EOK.MODID + ":textures/gui/screen/eok_manual.png"));
		this.setTextureSize(512, 512);
		this.setWindowSize(279, 180);
		this.setTitle("");
		pageUp = controlFactory.createButton(20, 20, 0, 181, 21, 181, this::pageUp);
		pageDown = controlFactory.createButton(20, 20, 0, 202, 21, 202, this::pageDown);
		this.setPreRenderFunction((gui) -> {
			if(!initialized) {
				this.initDocument();
			}
		});
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		if(renderer == null)
			return;
		GLUtils.pushMatrix();
		GLUtils.enableBlend();
		GLUtils.normalBlend();
		GLUtils.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		GLUtils.bindTexture(this.getTexture());
		if(renderer.getPages() > 0) {
			renderer.draw(pageIndex, DocumentRenderer.DocumentSide.LEFT, this.getOffsetX(), this.getOffsetY());
			if(pageIndex + 1 < renderer.getPages()) 
				renderer.draw(pageIndex + 1, DocumentRenderer.DocumentSide.RIGHT, this.getOffsetX(), this.getOffsetY());
		}
		GLUtils.disableBlend();
		GLUtils.popMatrix();
	}

	@Override
	public void initGui() {
		super.initGui();
		pageUp.setPos(13, 151);
		pageDown.setPos(248, 151);
	}
	
	private void pageUp(MetaGuiScreen gui) {
		if(this.pageIndex < 2) return;
		this.pageIndex -= 2;
	}
	
	private void pageDown(MetaGuiScreen gui) {
		if(this.pageIndex + 2 >= renderer.getPages()) return;
		this.pageIndex += 2;
	}

	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		if(renderer != null)
			renderer.remove();
		GLUtils.deleteTempTexture();
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		if(renderer != null)
			renderer.onMouseClick(mouseX, mouseY, mouseButton, this.pageIndex);
	}
	
	protected void initDocument() {
		// 这里判断是否为null是为了防止在窗口大小改变时重复读取文档导致内存泄漏
		if(renderer == null) {
			renderer = new DocumentRenderer(17, 13, 149, 13, 115, 135, EOK.MODID + ":manual/index/index.edt");
		}
	}

}
