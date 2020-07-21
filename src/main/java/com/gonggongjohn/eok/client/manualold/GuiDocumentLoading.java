package com.gonggongjohn.eok.client.manualold;

import com.github.zi_jing.cuckoolib.client.gui.Colors;
import com.github.zi_jing.cuckoolib.client.gui.modulargui.GuiControl;
import com.github.zi_jing.cuckoolib.client.gui.modulargui.ModularGuiConstants;
import com.github.zi_jing.cuckoolib.client.gui.modulargui.ModularGuiScreen;
import com.github.zi_jing.cuckoolib.client.render.GLUtils;
import net.minecraft.client.resources.I18n;

final class GuiDocumentLoading extends ModularGuiScreen {

    protected final GuiControl.ProgressBar progress;
    protected String status;

    public GuiDocumentLoading() {
        super(ModularGuiConstants.GUI_NOT_PAUSE_GAME | ModularGuiConstants.GUI_HAS_CUSTOM_BACKGROUND);
        this.setTitle(I18n.format("eok.message.documentrenderer.loadingscreen.title"));
        this.setWindowSize(250, 70);
        this.setPreRenderFunction((gui) -> GLUtils.drawRect(gui.getOffsetX(), gui.getOffsetY(), gui.getOffsetX() + gui.getWindowWidth(), gui.getOffsetY() + gui.getWindowHeight(), 0x90E8ECEC));
        this.progress = this.controlFactory.createProgressBar(220, 10);
    }

    @Override
    public void initGui() {
        super.initGui();
        this.progress.setPos(15, 45);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        GLUtils.drawString(this.status, this.getOffsetX() + 17, this.getOffsetY() + 33, Colors.DEFAULT_BLACK);
    }

    protected void setProgress(int progress, String status) {
        this.progress.setProgress(progress);
        this.status = status;
    }

    protected int getProgress() {
        return this.progress.getProgress();
    }

    protected void reset() {
        this.progress.setProgress(0);
        this.status = "";
    }

}
