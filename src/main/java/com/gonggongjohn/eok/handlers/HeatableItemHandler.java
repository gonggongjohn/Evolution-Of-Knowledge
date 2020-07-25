package com.gonggongjohn.eok.handlers;

import com.gonggongjohn.eok.api.HeatIndex;
import com.gonggongjohn.eok.api.HeatRaw;
import com.gonggongjohn.eok.api.HeatRegistry;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class HeatableItemHandler {
	public static final Item melt = new Item().setUnlocalizedName("meltedMetal");

	public static void setup() {
		HeatRegistry registry = HeatRegistry.getInstance();
		HeatRaw rawIron = new HeatRaw(460, 1535);
		HeatRaw rawGold = new HeatRaw(128, 1064);
		HeatRaw rawSand = new HeatRaw(920, 870);
		registry.addIndex(new HeatIndex(new ItemStack(Items.iron_ingot, 1), rawIron, new ItemStack(melt, 1)));
		registry.addIndex(new HeatIndex(new ItemStack(Items.gold_ingot, 1), rawGold, new ItemStack(melt, 1)));
		registry.addIndex(new HeatIndex(new ItemStack(Blocks.sand, 1), rawSand, new ItemStack(Blocks.glass, 1)));
	}

	public static void register() {
		GameRegistry.registerItem(melt, "meltedMetal");
	}
}