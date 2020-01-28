package com.gonggongjohn.eok.handlers;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.items.ModMetaItem;
import gregtech.api.items.metaitem.MetaItem;

public class MetaItemsHandler {
    public static MetaItem<?>.MetaValueItem CONVEX_LENS;
    public static MetaItem<?>.MetaValueItem CONCAVE_LENS;

    public static void init(){
        ModMetaItem metaItem = new ModMetaItem();
        metaItem.setRegistryName(EOK.MODID, "meta_item");
    }
}
