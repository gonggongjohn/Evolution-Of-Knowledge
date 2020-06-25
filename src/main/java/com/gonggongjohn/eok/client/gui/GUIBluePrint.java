package com.gonggongjohn.eok.client.gui;

import com.gonggongjohn.eok.EOK;
import gregtech.api.items.metaitem.MetaItem;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GUIBluePrint extends GuiScreen {
    private static final int NEXT_BUTTON_ID = 1;
    private static final int PREVIOUS_BUTTON_ID = 0;
    public static MetaItem<?>.MetaValueItem blueprintHolding;
    private final ResourceLocation backgroundLocation = new ResourceLocation(EOK.MODID + ":" + "textures/gui/screen/blueprint/blueprint_default_background.png");
    private final int xSize;
    private final int ySize;
    private GuiButton previous;
    private GuiButton next;
    private int page;
    private int offsetX;
    private int offsetY;

    public GUIBluePrint() {
        super();
        this.xSize = 256;
        this.ySize = 200;
        this.page = 0;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        offsetX = (this.width - this.xSize) / 2;
        offsetY = (this.height - this.ySize) / 2;
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        drawDefaultBackground();
        //draw background
        mc.getTextureManager().bindTexture(backgroundLocation);
        drawTexturedModalRect(offsetX, offsetY, 0, 0, 256, 200);
        int maxpage = EOK.bluePrintDict.maxPageSize.get(blueprintHolding);
        //draw the button of the previous page
        if (page > 0) {
            drawTexturedModalRect(offsetX, offsetY + 85, 0, 200, 24, 30);
        }
        //draw the button of the next page
        if (page < maxpage - 1) {
            drawTexturedModalRect(offsetX + 226, offsetY + 85, 24, 200, 24, 30);
        }
        //draw the pictures on the left
        String[] texturepack = EOK.bluePrintDict.textureMap.get(blueprintHolding);
        ResourceLocation location = new ResourceLocation(EOK.MODID + ":" + "textures/gui/screen/blueprint/" + blueprintHolding.unlocalizedName + "/" + texturepack[page]);
        mc.renderEngine.bindTexture(location);
        drawTexturedModalRect(offsetX, offsetY, 0, 0, 128, 200);
        GL11.glPopMatrix();
    }

    @Override
    public void initGui() {
        buttonList.clear();
        offsetX = (this.width - this.xSize) / 2;
        offsetY = (this.height - this.ySize) / 2;
        super.initGui();
        buttonList.add(setPrevious(new GuiButton(0, offsetX, offsetY + 85, 24, 30, "")));
        buttonList.add(setNext(new GuiButton(1, offsetX + 226, offsetY + 85, 24, 30, "")));
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case PREVIOUS_BUTTON_ID: {
                if (page > 0) page--;
                break;
            }
            case NEXT_BUTTON_ID: {
                if (page < EOK.bluePrintDict.maxPageSize.get(blueprintHolding) - 1) page++;
                break;
            }
            default: {
                break;
            }
        }
    }

    private GuiButton setPrevious(GuiButton button) {
        this.previous = button;
        return button;
    }

    private GuiButton setNext(GuiButton button) {
        this.next = button;
        return button;
    }

    public GuiButton getPrevious() {
        return this.previous;
    }

    public GuiButton getNext() {
        return this.next;
    }

}
