package com.gonggongjohn.eok;

import com.gonggongjohn.eok.handlers.ItemHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class EOKTab extends CreativeTabs {

    public EOKTab() {
        super("eoktab");
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(ItemHandler.refractingTelescope);
    }
}
