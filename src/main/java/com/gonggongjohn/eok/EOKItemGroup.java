package com.gonggongjohn.eok;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class EOKItemGroup extends ItemGroup {
    public EOKItemGroup() {
        super("testmod_group");
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(Items.STONE_SWORD);
    }
}
