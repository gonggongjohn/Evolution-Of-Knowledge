package com.gonggongjohn.eok.client.gui;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class ButtonBlueprintTableCenter extends GuiButton {
    private Block content;
    private GUIBlueprintTable fatherInstance;

    public ButtonBlueprintTableCenter(int buttonId, int x, int y, int widthIn, int heightIn, GUIBlueprintTable fatherInstance) {
        super(buttonId, x, y, widthIn, heightIn, "");
        this.fatherInstance = fatherInstance;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if(this.visible) {
            GL11.glPushMatrix();
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            if(content != null) {
                ItemStack stack = new ItemStack(Item.getItemFromBlock(content));
                fatherInstance.drawItemStack(stack, this.x + 2, this.y + 2, "");
                int relx = mouseX - this.x, rely = mouseY - this.y;
                if (relx >= 0 && rely >= 0 && relx < this.width && rely < this.height){
                    String name = I18n.format("eok.blueprint.component.pre") + this.content.getLocalizedName();
                    mc.fontRenderer.drawString(name, mouseX + 5, mouseY + 5, 0xFF0000);
                }
            }
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glPopMatrix();
        }
    }

    public void setContent(Block content) {
        this.content = content;
    }

    public Block getContent() {
        return this.content;
    }
}
