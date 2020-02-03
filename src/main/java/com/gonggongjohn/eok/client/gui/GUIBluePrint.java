package com.gonggongjohn.eok.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class GUIBluePrint extends GuiScreen {
    private int bluePrintIndex;//蓝图编号，与BluePrintMetaItem里静态数组下标一致
    private int page;//页码

    public GUIBluePrint()
    {
        super();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void initGui() {
        super.initGui();
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
    }

}
