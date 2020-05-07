package com.gonggongjohn.eok.api.item.meta.module;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemAttributesModule implements IItemModule {

	/**
	 * @see net.minecraft.item.Item#hasEffect
	 */
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack) {
		return stack.isItemEnchanted();
	}

	/**
	 * @see net.minecraft.item.Item#getSmeltingExperience
	 */
	public float getSmeltingExperience(ItemStack item) {
		return -1; // -1 will default to the old lookups.
	}

	/**
	 * @see net.minecraft.item.Item#isBeaconPayment
	 */
	public boolean isBeaconPayment(ItemStack stack) {
		return stack.getItem() == Items.EMERALD || stack.getItem() == Items.DIAMOND || stack.getItem() == Items.GOLD_INGOT || stack.getItem() == Items.IRON_INGOT;
	}
}
