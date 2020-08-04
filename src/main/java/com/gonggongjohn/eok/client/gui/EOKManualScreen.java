package com.gonggongjohn.eok.client.gui;

import com.gonggongjohn.eok.EOK;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class EOKManualScreen extends Screen {

    private int tick = 0;
    private boolean tickIncr = true;
    private int d0 = 0;
    private int d1 = 0;
    private int d2 = 0;
    private int d3 = 0;

    public EOKManualScreen(ITextComponent title) {
        super(title);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public void tick() {
        if (this.tick >= 0 && this.tick <= 250) {
            this.tick += this.tickIncr ? 25 : -25;
        } else if (this.tick >= 0) {
            this.tickIncr = false;
            this.tick -= 25;
        } else {
            this.tickIncr = true;
            this.tick += 25;
        }
        EOK.getLogger().debug(this.tick);
        EOK.getLogger().debug(this.tickIncr);
    }

    @Override
    public void render(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
        // drawDefaultBackground
        this.renderBackground(p_230430_1_);
        int x = this.tick;
        GlStateManager.pushMatrix();
        GlStateManager.color4f((float) this.tick / 250, 1f - (float) this.tick / 250, (float) this.tick / 400, 1f);
        GlStateManager.rotatef((float) this.tick / 500 * 360, 50, 50, 0);
        Minecraft.getInstance().textureManager.bindTexture(new ResourceLocation("minecraft:textures/gui/container/stonecutter.png"));
        GlStateManager.popMatrix();
        this.blit(p_230430_1_, tick, 50, 0, 0, 176, 166);
        super.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
    }

}
