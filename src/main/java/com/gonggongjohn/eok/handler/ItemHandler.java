package com.gonggongjohn.eok.handler;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.item.*;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;

import java.util.ArrayList;
import java.util.List;

public class ItemHandler {
    public static final List<Item> ITEM_REGISTRY = new ArrayList<>();

    public static final Item itemEOKSymbol = register("eok_symbol", new EOKSymbolItem());
    public static final Item itemEOKManual = register("eok_manual", new EOKManualItem());
    public static final Item itemRedstoneApple = register("redstone_apple", new Item((new Item.Properties()).group(EOK.EOK_ITEMGROUP).rarity(Rarity.RARE).food(FoodHandler.REDSTONE_APPLE)));
    public static final Item itemRedstoneAxe = register("redstone_axe", new RedstoneAxeItem());
    public static final Item itemRedstoneHoe = register("redstone_hoe", new RedstoneHoeItem());
    public static final Item itemRedstoneShovel = register("redstone_shovel", new RedstoneShovelItem());
    public static final Item itemRedstoneSword = register("redstone_sword", new RedstoneSwordItem());
    public static final Item itemRedstonePickaxe = register("redstone_pickaxe", new RedstonePickaxeItem());

    private static Item register(String unlocalizedName, Item item) {
        item.setRegistryName(EOK.MODID, unlocalizedName);
        ITEM_REGISTRY.add(item);
        return item;
    }
}
