package com.gonggongjohn.eok.client.gui;

import com.github.zi_jing.cuckoolib.client.gui.widget.EasyButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ButtonSecondaryBlueprintTableComponent extends EasyButton {
    private ItemStack content;

    public ButtonSecondaryBlueprintTableComponent(int buttonId) {
        super(buttonId);
        this.setActive(false);
    }

    @Override
    public void updateButton(Minecraft mc, int mouseX, int mouseY) {
        if(content != null){
            setItemStack(content, x + 1, y + 1);
            String name = I18n.format("eok.blueprint.component.pre") + content.getDisplayName();
            setHoverTips(name, mouseX + 5, mouseY + 5, 0xFF0000);
        }
        else{
            setNullItemStack();
            setNullHoverTips();
        }
    }

    public void setContent(ItemStack content) {
        this.content = content;
    }

    public ItemStack getContent() {
        return content;
    }
}
