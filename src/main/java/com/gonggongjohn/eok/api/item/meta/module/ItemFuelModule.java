package com.gonggongjohn.eok.api.item.meta.module;

import net.minecraft.item.ItemStack;

public class ItemFuelModule implements IItemModule {

	/**
	 * @see net.minecraft.item.Item#getItemBurnTime
	 */
	public int getItemBurnTime(ItemStack itemStack) {
		return -1;
	}
}
