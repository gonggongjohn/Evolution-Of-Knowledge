package com.gonggongjohn.eok.api.item.meta.module;

import net.minecraft.item.ItemStack;

@FunctionalInterface
public interface IItemNameProvider extends IItemModule {
    String getItemStackDisplayName(ItemStack stack);
}