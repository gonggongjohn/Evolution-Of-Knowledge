package com.gonggongjohn.eok.api.item.meta.module;

import net.minecraft.item.ItemStack;

public interface IDurabilityBarProvider extends IItemModule {
    boolean showDurabilityBar(ItemStack stack);

    double getDurabilityForDisplay(ItemStack stack);

    int getRGBDurabilityForDisplay(ItemStack stack);
}