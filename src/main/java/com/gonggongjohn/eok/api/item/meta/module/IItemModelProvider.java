package com.gonggongjohn.eok.api.item.meta.module;

import net.minecraft.item.ItemStack;

public interface IItemModelProvider extends IItemModule {
	int getModelIndex(ItemStack stack);
}