package com.gonggongjohn.eok.api.item.meta.module;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.HorseArmorType;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemHorseArmorModule implements IItemModule {

    /**
     * @see net.minecraft.item.Item#getHorseArmorType
     */
    public HorseArmorType getHorseArmorType(ItemStack stack) {
        return HorseArmorType.NONE;
    }

    /**
     * @see net.minecraft.item.Item#getHorseArmorTexture
     */
    public String getHorseArmorTexture(EntityLiving wearer, ItemStack stack) {
        return getHorseArmorType(stack).getTextureName();
    }

    /**
     * @see net.minecraft.item.Item#onHorseArmorTick
     */
    public void onHorseArmorTick(World world, EntityLiving horse, ItemStack armor) {

    }
}
