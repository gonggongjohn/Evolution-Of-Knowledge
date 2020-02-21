package com.gonggongjohn.eok.items;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.handlers.ItemHandler;
import com.gonggongjohn.eok.utils.IHasModel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ItemPapyrus extends Item implements IHasModel {
    private final String name = "papyrus";
    public ItemPapyrus(){
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setCreativeTab(EOK.tabEOK);
        ItemHandler.items.add(this);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        int meta = stack.getItemDamage();
        EnumPapyrus papyrus = EnumPapyrus.values()[meta];
        return "item." + papyrus.getName().toLowerCase();
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if(tab == EOK.tabEOK) {
            for(EnumPapyrus papyrus : EnumPapyrus.values()){
                items.add(new ItemStack(this, 1, papyrus.getMeta()));
            }
        }
    }

    @Override
    public void registerModel(){
        for(EnumPapyrus papyrus : EnumPapyrus.values()) {
            int meta = papyrus.getMeta();
            EOK.proxy.registerItemRenderer(this, meta, EOK.MODID + ":" + papyrus.getName().toLowerCase(),  "inventory");
        }
    }

    public enum EnumPapyrus{
        PAPYRUS_EMPTY(0, "papyrus"),
        PAPYRUS_USED(1, "papyrus_used"),
        PAPYRUS_PILE(2, "papyrus_pile");

        private final int meta;
        private final String name;

        private EnumPapyrus(int meta, String name){
            this.meta = meta;
            this.name=name;
        }

        public int getMeta() {
            return meta;
        }

        public String getName() {
            return name;
        }

    }
}
