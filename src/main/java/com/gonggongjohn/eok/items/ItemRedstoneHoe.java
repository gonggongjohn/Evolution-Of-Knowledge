package com.gonggongjohn.eok.items;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.handlers.ItemHandler;
import com.gonggongjohn.eok.utils.IHasModel;
import com.gonggongjohn.eok.utils.ToolMaterials;
import net.minecraft.item.ItemHoe;

public class ItemRedstoneHoe extends ItemHoe implements IHasModel {

    private final String name = "redstone_hoe";

    public ItemRedstoneHoe() {
        super(ToolMaterials.REDSTONE);
        this.setUnlocalizedName("eok." + name);
        this.setRegistryName(name);
        this.setCreativeTab(EOK.tabEOK);
        this.setMaxStackSize(1);
        ItemHandler.ITEM_REGISTRY.add(this);
    }

    @Override
    public void registerModel() {
        EOK.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
