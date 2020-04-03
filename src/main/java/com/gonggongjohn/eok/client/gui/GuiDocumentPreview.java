package com.gonggongjohn.eok.client.gui;

import java.io.File;

import org.lwjgl.opengl.GL11;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.utils.DocumentRenderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

/**
 * Want to load a document from your file system? Use this.
 * 
 * @see GuiScreenTest
 * @see DocumentRenderer
 */
public class GuiDocumentPreview extends MetaGuiScreen {

	private GuiControl.GuiButton pageUp;
	private GuiControl.GuiButton pageDown;
	private DocumentRenderer renderer;
	private int pages;
	private int pageIndex;

	public GuiDocumentPreview(String path) {
		super(true);
		this.setTexture(new ResourceLocation(EOK.MODID + ":textures/gui/screen/test_screen.png"));
		this.setTextureSize(422, 800);
		this.setWindowSize(401, 281);
		this.setTitle("");
		renderer = new DocumentRenderer(18, 18, 210, 18, 170, 230, new File(Minecraft.getMinecraft().mcDataDir.getAbsolutePath() + File.separator + path));
		pages = renderer.getPages();
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(this.getTexture());
		renderer.draw(pageIndex, DocumentRenderer.Side.LEFT, this.getOffsetX(), this.getOffsetY());
		if (pageIndex + 1 < pages)
			renderer.draw(pageIndex + 1, DocumentRenderer.Side.RIGHT, this.getOffsetX(), this.getOffsetY());
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}

	@Override
	public void initGui() {
		super.initGui();
		pageUp = builder.new GuiButton(12, 235, 54, 36, 0, 450, 0, 318, this::pageUp);
		pageDown = builder.new GuiButton(331, 234, 54, 36, 0, 450, 0, 282, this::pageDown);
		this.addControl(pageUp);
		this.addControl(pageDown);
	}

	private void pageUp(MetaGuiScreen gui) {
		if (this.pageIndex < 2)
			return;
		this.pageIndex -= 2;
	}

	private void pageDown(MetaGuiScreen gui) {
		if (this.pageIndex + 2 >= this.pages)
			return;
		this.pageIndex += 2;
	}
}
