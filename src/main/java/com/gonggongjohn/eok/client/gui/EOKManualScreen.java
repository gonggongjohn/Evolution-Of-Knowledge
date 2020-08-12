package com.gonggongjohn.eok.client.gui;

import com.github.zi_jing.cuckoolib.client.render.GLUtil;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
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
        GLUtil.fill(matrixStack, 50, 50, 100, 100, 0xFFFFFFFF);
        GLUtil.drawTexturedRectWithDefaultUV(matrixStack, 70, 30, 60, 60, new ResourceLocation("textures/gui/container/stonecutter.png"));
        GLUtil.postRenderTexture();
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

}
