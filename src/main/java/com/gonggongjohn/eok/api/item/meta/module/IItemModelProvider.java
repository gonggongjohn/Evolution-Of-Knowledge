package com.gonggongjohn.eok.api.item.meta.module;

import net.minecraft.item.ItemStack;

@FunctionalInterface
public interface IItemModelProvider extends IItemModule {
    int getModelIndex(ItemStack stack);
}