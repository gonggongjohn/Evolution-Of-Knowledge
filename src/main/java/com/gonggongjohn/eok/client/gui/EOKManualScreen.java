package com.gonggongjohn.eok.client.gui;

import com.github.zi_jing.cuckoolib.client.render.GLUtil;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;

public class EOKManualScreen extends Screen {

    public EOKManualScreen(ITextComponent title) {
        super(title);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        GLUtil.preRenderTexture();
        GLUtil.fill(matrixStack, 20, 20, 300, 300, 0xFFFFFFFF);
        GLUtil.drawString(matrixStack, "9 测试文本", 60, 60, 0);
        GLUtil.drawScaledString(new MatrixStack(), "15 测试文本", 60, 70, 0, 15);
        GLUtil.drawScaledString(new MatrixStack(), "20 测试文本", 60, 86, 0, 20);
        GLUtil.drawScaledString(new MatrixStack(), "25 测试文本", 60, 107, 0, 25);
        GLUtil.drawScaledString(new MatrixStack(), "30 测试文本", 60, 133, 0, 30);
        GLUtil.postRenderTexture();
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

}
