package com.gonggongjohn.eok.api.item.meta.module;

import net.minecraft.item.ItemStack;

public interface IContainerItemProvider extends IItemModule {
	ItemStack getContainerItem(ItemStack stack);
}