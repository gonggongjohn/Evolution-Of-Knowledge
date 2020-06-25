package com.gonggongjohn.eok.api.item.meta.module;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;

public class ItemEnchantmentModule implements IItemModule {

    /**
     * @see net.minecraft.item.Item#isEnchantable
     */
    public boolean isEnchantable(ItemStack stack) {
        return stack.getItem().getItemStackLimit(stack) == 1 && stack.getItem().isDamageable();
    }

    /**
     * @see net.minecraft.item.Item#canApplyAtEnchantingTable
     */
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment.type.canEnchantItem(stack.getItem());
    }
}
