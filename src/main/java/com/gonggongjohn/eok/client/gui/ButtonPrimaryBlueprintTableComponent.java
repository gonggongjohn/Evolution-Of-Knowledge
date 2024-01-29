package com.gonggongjohn.eok.client.gui;

import com.github.zi_jing.cuckoolib.gui.widget.EasyButton;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ButtonPrimaryBlueprintTableComponent extends EasyButton {
    private Block content;

    public ButtonPrimaryBlueprintTableComponent(int buttonId) {
        super(buttonId);
        this.setActive(false);
    }

    @Override
    public void updateButton(Minecraft mc, int mouseX, int mouseY) {
        if(content != null) {
            ItemStack stack = new ItemStack(Item.getItemFromBlock(this.content));
            setItemStack(stack, x + 1, y + 1);
            String name = I18n.format("eok.blueprint.component.pre") + Items.IRON_INGOT.getUnlocalizedName();
            setHoverTips(name, mouseX + 5, mouseY + 5, 0xFF0000);
        }
        else{
            setNullItemStack();
            setNullHoverTips();
        }
    }

    public void setContent(Block content) {
        this.content = content;
    }

    public Block getContent() {
        return content;
    }
}
