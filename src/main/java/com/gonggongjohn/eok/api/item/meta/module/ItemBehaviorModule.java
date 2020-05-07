package com.gonggongjohn.eok.api.item.meta.module;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBehaviorModule implements IItemModule {

	/**
	 * @see net.minecraft.item.Item#onUpdate
	 */
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {

	}

	/**
	 * @see net.minecraft.item.Item#onCreated
	 */
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {

	}

	/**
	 * @see net.minecraft.item.Item#onDroppedByPlayer
	 */
	public boolean onDroppedByPlayer(ItemStack item, EntityPlayer player) {
		return true;
	}
}
