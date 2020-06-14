package com.gonggongjohn.eok.api.item.meta.module;

import net.minecraft.item.ItemStack;

public interface IToolDamage extends IItemModule {
	void damageItem(ItemStack stack, int damage);

	int getItemDamage(ItemStack stack);

	int getItemMaxDamage(ItemStack stack);
}