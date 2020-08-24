package com.gonggongjohn.eok.util;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;

public class RedstoneTier implements IItemTier {
    @Override
    public int getMaxUses() {
        return 222;
    }

    @Override
    public float getEfficiency() {
        return 13.5F;
    }

    @Override
    public float getAttackDamage() {
        return 2.2F;
    }

    @Override
    public int getHarvestLevel() {
        return 3;
    }

    @Override
    public int getEnchantability() {
        return 12;
    }

    @Override
    public Ingredient getRepairMaterial() {
        return Ingredient.fromItems(Items.REDSTONE);
    }
}
