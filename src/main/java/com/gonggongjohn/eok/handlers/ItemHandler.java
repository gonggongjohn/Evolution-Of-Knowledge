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
    //public static final Item Redstone_Sword = new ItemRedstoneSword();
    //public static final Item Redstone_Pickaxe = new ItemRedstonePickaxe();
    //public static final Item Redstone_Axe = new ItemRedstoneAxe();
    //public static final Item Redstone_Hoe = new ItemRedstoneHoe();
    //public static final Item Redstone_Spade = new ItemRedstoneSpade();
    //public static final Item Redstone_Helmet = new ItemRedstoneHelmet();
    //public static final Item Redstone_Chestplate = new ItemRedstoneChestplate();
    //public static final Item Redstone_Leggings = new ItemRedstoneLeggings();
    //public static final Item Redstone_Boots = new ItemRedstoneBoots();
    public static final Item bluePrint = new ItemBluePrint();
    public static final Item testMath = new ItemTestMath();
    public static final Item bighammer80 = new ItemBigHammer80();
    public static final Item smallhammer40 = new ItemSmallHammer40();

    public static void registerItem(Item item) {
        ITEM_REGISTRY.add(item);
    }
}