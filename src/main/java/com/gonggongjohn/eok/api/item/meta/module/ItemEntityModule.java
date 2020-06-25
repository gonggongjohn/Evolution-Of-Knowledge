package com.gonggongjohn.eok.api.item.meta.module;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ItemEntityModule implements IItemModule {

    /**
     * @see net.minecraft.item.Item#getEntityLifespan
     */
    public int getEntityLifespan(ItemStack itemStack, World world) {
        return 6000;
    }

    /**
     * @see net.minecraft.item.Item#hasCustomEntity
     */
    public boolean hasCustomEntity(ItemStack stack) {
        return false;
    }

    /**
     * @see net.minecraft.item.Item#createEntity
     */
    @Nullable
    public Entity createEntity(World world, Entity location, ItemStack itemstack) {
        return null;
    }

    /**
     * @see net.minecraft.item.Item#onEntityItemUpdate
     */
    public boolean onEntityItemUpdate(EntityItem entityItem) {
        return false;
    }
}
