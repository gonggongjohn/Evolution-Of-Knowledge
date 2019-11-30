package com.gonggongjohn.eok.handlers;

import com.gonggongjohn.eok.utils.MerchantTradeData;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class TradeHandler {
	public static void setup() {
		// 几个用来占位的测试交易
		MerchantTradeData.registerTrade(new MerchantTradeData(new ItemStack(Blocks.COBBLESTONE, 23),
				new ItemStack(Items.STICK, 5), new ItemStack(ItemHandler.eokSymbol, 1), 1));
		MerchantTradeData.registerTrade(new MerchantTradeData(new ItemStack(ItemHandler.MACHINE_GUN, 1),
				ItemStack.EMPTY, new ItemStack(ItemHandler.Torcherino, 10), 1));
		MerchantTradeData.registerTrade(new MerchantTradeData(new ItemStack(ItemHandler.concaveLens, 1),
				ItemStack.EMPTY, new ItemStack(ItemHandler.convexLens, 1), 1));
		MerchantTradeData.registerTrade(new MerchantTradeData(new ItemStack(ItemHandler.convexLens, 1), ItemStack.EMPTY,
				new ItemStack(ItemHandler.concaveLens, 1), 1));
		MerchantTradeData.registerTrade(new MerchantTradeData(new ItemStack(ItemHandler.refractingTelescope, 1),
				new ItemStack(Items.COMPASS, 1), new ItemStack(ItemHandler.papyrus, 1), 1));
		MerchantTradeData.registerTrade(new MerchantTradeData(new ItemStack(ItemHandler.tube, 5),
				new ItemStack(Items.DIAMOND, 1), new ItemStack(Items.COAL, 64), 1));
		MerchantTradeData.registerTrade(new MerchantTradeData(new ItemStack(ItemHandler.shortStick, 2), ItemStack.EMPTY,
				new ItemStack(Items.STICK), 1));
	}
}
