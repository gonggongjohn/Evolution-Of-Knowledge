package com.gonggongjohn.eok.handler;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.item.EOKSymbolItem;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemHandler {
    public static final List<Item> ITEM_REGISTRY = new ArrayList<>();

    public static Item itemEOKSymbol = register("eok_symbol", new EOKSymbolItem());

    private static Item register(String unlocalizedName, Item item) {
        item.setRegistryName(EOK.MODID, unlocalizedName);
        ITEM_REGISTRY.add(item);
        return item;
    }
}
