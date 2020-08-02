package com.gonggongjohn.eok;

import com.gonggongjohn.eok.handler.ItemHandler;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class EOKItemGroup extends ItemGroup {
    public EOKItemGroup() {
        super("eok_group");
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(ItemHandler.itemEOKSymbol);
    }
}
