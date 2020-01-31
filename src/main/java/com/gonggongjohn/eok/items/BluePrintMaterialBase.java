package com.gonggongjohn.eok.items;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.handlers.MetaItemsHandler;
import gregtech.api.items.materialitem.MaterialMetaItem;
import gregtech.api.items.metaitem.MetaItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.ArrayList;


public class BluePrintMaterialBase extends MaterialMetaItem implements IBluePrint{
    public static ArrayList<MetaItem<?>.MetaValueItem> BLUE_PRINTS=new ArrayList<MetaItem<?>.MetaValueItem>();

    public BluePrintMaterialBase() {
        super();
        this.setCreativeTab(EOK.tabEOK);
    }

    @Override
    public void registerSubItems() {
        MetaItemsHandler.WATER_WHEEL=addItem(300,"water_wheel");
        MetaItemsHandler.WIND_MILL=addItem(301,"wind_mill");

        BLUE_PRINTS.add(MetaItemsHandler.WATER_WHEEL);
        BLUE_PRINTS.add(MetaItemsHandler.WIND_MILL);

        for(MetaItem<?>.MetaValueItem metaValueItem:BLUE_PRINTS)
        {
            metaValueItem.setMaxStackSize(1);
        }
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if(tab != EOK.tabEOK){
            return;
        }
        for(MetaItem<?>.MetaValueItem metaItem : metaItems.valueCollection()){
            if(!metaItem.isVisible()){
                continue;
            }
            metaItem.getSubItemHandler().getSubItems(metaItem.getStackForm(), tab, items);
        }
    }
}
