package com.gonggongjohn.eok.client.gui;

import com.github.zi_jing.cuckoolib.client.gui.widget.EasyButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class ButtonSecondaryBlueprintTableCenter extends EasyButton {
    private Item content;

    public ButtonSecondaryBlueprintTableCenter(int buttonId) {
        super(buttonId);
    }

    @Override
    public void updateButton(Minecraft mc, int mouseX, int mouseY) {
        if(content != null){
            ItemStack stack = new ItemStack(content);
            setItemStack(stack, x + 1, y + 1);
            String name = I18n.format("eok.blueprint.component.pre") + this.content.getUnlocalizedName();
            setHoverTips(name, mouseX + 5, mouseY + 5, 0xFF0000);
        }
        else{
            setNullItemStack();
            setNullHoverTips();
        }
    }

    public void setContent(Item content) {
        this.content = content;
    }

    public Item getContent() {
        return this.content;
    }
}
