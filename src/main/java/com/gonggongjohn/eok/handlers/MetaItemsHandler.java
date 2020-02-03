package com.gonggongjohn.eok.handlers;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.items.BluePrintMetaItem;
import com.gonggongjohn.eok.items.ModMetaItem;
import gregtech.api.items.metaitem.MetaItem;

public class MetaItemsHandler {
    public static MetaItem<?>.MetaValueItem CONVEX_LENS;
    public static MetaItem<?>.MetaValueItem CONCAVE_LENS;

    public static void init(){
        ModMetaItem metaItem = new ModMetaItem();
        metaItem.setRegistryName(EOK.MODID, "meta_item");

        BluePrintMetaItem bpMetaItem=new BluePrintMetaItem();
        bpMetaItem.setRegistryName(EOK.MODID,"blue_print_meta_item");
    }

    //edited by molybdenum
    public static MetaItem<?>.MetaValueItem BLUE_PRINT_WATER_WHEEL;//水车
    public static MetaItem<?>.MetaValueItem BLUE_PRINT_WIND_MILL;//风车
    public static MetaItem<?>.MetaValueItem BLUE_PRINT_TEST_2D_CORE;
    public static MetaItem<?>.MetaValueItem BLUE_PRINT_ELEMENTARY_RESEARCH_TABLE;
}
