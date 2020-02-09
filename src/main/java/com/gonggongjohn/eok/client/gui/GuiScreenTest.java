package com.gonggongjohn.eok.client.gui;

import org.lwjgl.opengl.GL11;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.utils.Colors;

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
	
	public GuiScreenTest() {
		super(true);
		this.setTexture(new ResourceLocation(EOK.MODID + ":textures/gui/screen/test_screen.png"));
		this.setTextureSize(422, 800);
		this.setWindowSize(400, 280);
		this.setTitle("");
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(this.getTexture());
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
		buttonList.isEmpty();
	}

	@Override
	public void initGui() {
		super.initGui();
		pageUp = builder.new GuiButton(this.getOffsetX() + 12, this.getOffsetY() + 240, 54, 36, 0, 450, 0, 281, this, this::pageUp);
		pageDown = builder.new GuiButton(this.getOffsetX() + 120, this.getOffsetY() + 20, 54, 36, 0, 450, 0, 333, this, this::pageDown);
		pageUp.setText("114514", Colors.DEFAULT_BLACK);
		pageDown.setText("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq", 0xFFFFFFFF);
		this.addControl(pageUp);
		this.addControl(pageDown);
	}
	
	private void pageUp(MetaGuiScreen gui) {
		EOK.getLogger().info("1");
	}
	
	private void pageDown(MetaGuiScreen gui) {
		
	}
	
}
