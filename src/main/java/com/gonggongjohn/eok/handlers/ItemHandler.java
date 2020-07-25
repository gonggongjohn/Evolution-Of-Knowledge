package com.gonggongjohn.eok.handlers;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.api.HeatableTool;
import com.gonggongjohn.eok.items.ItemFlintFragment;
import com.gonggongjohn.eok.items.ItemGun;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class ItemHandler {
	public static Item itemEOKSymbol;
	public static Item itemPlantFiber;
	public static Item itemFlintFragment;
	public static Item itemChippedFlintFragment;
	public static Item itemGunRevolver;
	public static Item itemAirGun;
	public static Item itemAutomaticRifle;

	public static void setupItem() {
		itemEOKSymbol = new Item().setUnlocalizedName("eokSymbol").setTextureName(EOK.MODID + ":itemEOKSymbol")
				.setCreativeTab(EOK.tabEOK);
		itemPlantFiber = new Item().setUnlocalizedName("plantFiber").setTextureName(EOK.MODID + ":itemPlantFiber")
				.setCreativeTab(EOK.tabEOK);
		itemFlintFragment = new ItemFlintFragment().setUnlocalizedName("flintFragment")
				.setTextureName(EOK.MODID + ":itemFlintFragment").setCreativeTab(EOK.tabEOK);
		itemChippedFlintFragment = new Item().setUnlocalizedName("chippedFlintFragment")
				.setTextureName(EOK.MODID + ":itemChippedFlintFragment").setCreativeTab(EOK.tabEOK);
		itemGunRevolver = new ItemGun("revolver", "pistolBullet", 6, 40);
		itemAirGun = new ItemGun("airGun", "airBullet", 12, 20);
		itemAutomaticRifle = new ItemGun("automaticRifle", "rifleBullet", 40, 4);
	}

	public static void registerItem() {
		GameRegistry.registerItem(itemEOKSymbol, "itemEOKSymbol");
		GameRegistry.registerItem(itemPlantFiber, "itemPlantFiber");
		GameRegistry.registerItem(itemFlintFragment, "itemFlintFragment");
		GameRegistry.registerItem(itemChippedFlintFragment, "itemChippedFlintFragment");
		GameRegistry.registerItem(itemGunRevolver, "gun_revolver");
		GameRegistry.registerItem(itemAirGun, "gun_airGun");
		GameRegistry.registerItem(itemAutomaticRifle, "gun_automaticRifle");
	}
}