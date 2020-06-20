package com.gonggongjohn.eok.api.item.meta.module;

import net.minecraft.item.ItemStack;

public interface IDurabilityBarProvider extends IItemModule {
	public boolean showDurabilityBar(ItemStack stack);

	public double getDurabilityForDisplay(ItemStack stack);

	public int getRGBDurabilityForDisplay(ItemStack stack);
}