package com.gonggongjohn.eok.api.item.meta.module;

import net.minecraft.item.ItemStack;

public class ItemContainerModule implements IItemModule {

	/**
	 * @see net.minecraft.item.Item#getContainerItem
	 */
	public ItemStack getContainerItem(ItemStack itemStack) {
		if (!itemStack.getItem().hasContainerItem(itemStack)) {
			return ItemStack.EMPTY;
		}
		return new ItemStack(itemStack.getItem().getContainerItem());
	}
}
