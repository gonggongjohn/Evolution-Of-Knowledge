package com.gonggongjohn.eok.items;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.handlers.ItemHandler;
import com.gonggongjohn.eok.utils.IHasModel;
import net.minecraft.item.Item;

public class ItemBluePrint extends Item implements IHasModel {
    private String name = "blue_print";

    public ItemBluePrint(){
        this.setUnlocalizedName("eok." + this.name);
        this.setRegistryName(this.name);
        this.setCreativeTab(EOK.tabEOK);
        ItemHandler.ITEM_REGISTRY.add(this);
    }

    @Override
    public void registerModel() {
        EOK.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
