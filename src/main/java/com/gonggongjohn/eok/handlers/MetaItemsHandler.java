package com.gonggongjohn.eok.handlers;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.items.BluePrintMaterialBase;
import com.gonggongjohn.eok.items.ModMetaItem;
import gregtech.api.items.metaitem.MetaItem;

public class MetaItemsHandler {
    public static MetaItem<?>.MetaValueItem CONVEX_LENS;
    public static MetaItem<?>.MetaValueItem CONCAVE_LENS;

    public static void init(){
        ModMetaItem metaItem = new ModMetaItem();
        metaItem.setRegistryName(EOK.MODID, "meta_item");

        BluePrintMaterialBase bpMetaItem=new BluePrintMaterialBase();
        bpMetaItem.setRegistryName(EOK.MODID,"blue_print_meta_item");
    }

    //edited by molybdenum
    public static MetaItem<?>.MetaValueItem WATER_WHEEL;//水车
    public static MetaItem<?>.MetaValueItem WIND_MILL;//风车
}
