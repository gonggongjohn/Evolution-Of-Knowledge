package com.gonggongjohn.eok.items;

import com.gonggongjohn.eok.handlers.MetaItemsHandler;
import gregtech.api.items.materialitem.MaterialMetaItem;
import gregtech.api.unification.ore.OrePrefix;

public class ModMetaItem extends MaterialMetaItem {

    public ModMetaItem() {
        super(OrePrefix.plate, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null);
    }

    @Override
    public void registerSubItems() {
        MetaItemsHandler.CONVEX_LENS = this.addItem(0, "convex.lens");
        MetaItemsHandler.CONCAVE_LENS = this.addItem(1, "concave.lens");
    }

}
