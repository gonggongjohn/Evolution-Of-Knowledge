package com.gonggongjohn.eok.items;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.handlers.ItemHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemPapyrus extends Item implements IHasModel{
    private final String name = "papyrus";
    public ItemPapyrus(){
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        this.setCreativeTab(EOK.tabEOK);
        ItemHandler.items.add(this);
    }

   @Override
    public void registerModel(){
        EOK.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
