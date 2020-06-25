package com.gonggongjohn.eok.api.item.meta.module;

import net.minecraft.item.ItemStack;

@FunctionalInterface
public interface IItemFuel extends IItemModule {
    int getItemBurnTime(ItemStack stack);
}