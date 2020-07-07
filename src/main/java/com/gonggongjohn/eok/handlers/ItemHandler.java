package com.gonggongjohn.eok.handlers;

import com.gonggongjohn.eok.items.*;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber
public class ItemHandler {
    public static final List<Item> ITEM_REGISTRY = new ArrayList<>();

    public static final Item papyrus = new ItemPapyrus();
    public static final Item refractingTelescope = new ItemRefractingTelescope();
    public static final Item flintFragment = new ItemFlintFragment();
    public static final Item chippedFlintFragment = new ItemChippedFlintFragment();
    public static final Item polishedFlintFragment = new ItemPolishedFlintFragment();
    public static final Item Torcherino = new Torcherino();
    public static final Item MACHINE_GUN = new ItemMachineGun();
    public static final Item fireStick = new ItemFireStick();
    public static final Item EOKManual = new ItemEOKManual();
    public static final Item Golden_Egg = new ItemGoldenEgg();
    public static final Item Redstone_Apple = new ItemRedstoneApple();
    public static final Item Redstone_Sword = new ItemRedstoneSword();
    public static final Item Redstone_Pickaxe = new ItemRedstonePickaxe();
    public static final Item Redstone_Axe = new ItemRedstoneAxe();
    public static final Item Redstone_Hoe = new ItemRedstoneHoe();
    public static final Item Redstone_Spade = new ItemRedstoneSpade();
    public static final Item Redstone_Helmet = new ItemRedstoneHelmet();
    public static final Item Redstone_Chestplate = new ItemRedstoneChestplate();
    public static final Item Redstone_Leggings = new ItemRedstoneLeggings();
    public static final Item Redstone_Boots = new ItemRedstoneBoots();

    public static final Item metaTool = new MetaItemTool();
    public static final Item eokSymbol = new ItemMaterialBase("eok_symbol");
    public static final Item plantFiber = new ItemMaterialBase("plant_fiber");
    public static final Item shortStick = new ItemMaterialBase("short_stick");
    public static final Item tube = new ItemMaterialBase("tube");
    public static final Item rubberMat = new ItemMaterialBase("rubber_mat");
    public static final Item simple_hemp_rope = new ItemMaterialBase("simple_hemp_rope");
    public static final Item strong_hemp_rope = new ItemMaterialBase("strong_hemp_rope");

    public static void registerItem(Item item) {
        ITEM_REGISTRY.add(item);
    }
}