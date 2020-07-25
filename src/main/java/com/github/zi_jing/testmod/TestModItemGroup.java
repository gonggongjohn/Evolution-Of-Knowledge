package com.github.zi_jing.testmod;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class TestModItemGroup extends ItemGroup {
    public TestModItemGroup() {
        super("testmod_group");
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(Items.STONE_SWORD);
    }
}
