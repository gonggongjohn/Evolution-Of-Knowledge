package com.gonggongjohn.eok;

import com.gonggongjohn.eok.handlers.ItemHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTabEOK extends CreativeTabs {
    public CreativeTabEOK() {
        super(EOK.MODID);
    }

    @Override
    public Item getTabIconItem() {
        return ItemHandler.itemEOKSymbol;
    }
}
