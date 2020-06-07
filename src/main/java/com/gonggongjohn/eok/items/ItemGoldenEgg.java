package com.gonggongjohn.eok.items;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.handlers.ItemHandler;
import com.gonggongjohn.eok.utils.IHasModel;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemGoldenEgg extends Item implements IHasModel {

    private final String name = "golden_egg";

    public ItemGoldenEgg(){
        this.setUnlocalizedName("eok." + name);
        this.setRegistryName(name);
        this.setCreativeTab(EOK.tabEOK);
        ItemHandler.ITEM_REGISTRY.add(this);
    }

    @Override
    public void registerModel(){
        EOK.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
