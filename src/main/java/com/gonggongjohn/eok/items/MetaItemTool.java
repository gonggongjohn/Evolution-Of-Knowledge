package com.gonggongjohn.eok.items;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.handlers.ItemHandler;
import com.gonggongjohn.eok.utils.EnumTool;
import com.gonggongjohn.eok.utils.IHasModel;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class MetaItemTool extends Item implements IHasModel {
    private String name = "meta_tool";

    public MetaItemTool(){
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setCreativeTab(EOK.tabEOK);
        ItemHandler.ITEM_REGISTRY.add(this);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        int meta = stack.getItemDamage();
        EnumTool tool = EnumTool.values()[meta];
        return "item." + tool.getName().toLowerCase();
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if(tab == EOK.tabEOK) {
            for (EnumTool tool : EnumTool.values()) {
                items.add(new ItemStack(this, 1, tool.getMeta()));
            }
        }
    }

    @Override
    public void registerModel() {
        for(EnumTool tool : EnumTool.values()) {
            int meta = tool.getMeta();
            EOK.proxy.registerItemRenderer(this, meta, EOK.MODID + ":" + tool.getName().toLowerCase(), "inventory");
        }
    }
}
