package com.gonggongjohn.eok.api.item.meta.module;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

public class ItemDurabilityModule implements IItemModule {

	/**
	 * @see net.minecraft.item.Item#getDamage
	 */
	public int getDamage(ItemStack stack) {
		return 0;
	}

	/**
	 * @see net.minecraft.item.Item#showDurabilityBar
	 */
	public boolean showDurabilityBar(ItemStack stack) {
		return stack.isItemDamaged();
	}

	/**
	 * @see net.minecraft.item.Item#getDurabilityForDisplay
	 */
	public double getDurabilityForDisplay(ItemStack stack) {
		return (double)stack.getItemDamage() / (double)stack.getMaxDamage();
	}

	/**
	 * @see net.minecraft.item.Item#getRGBDurabilityForDisplay
	 */
	public int getRGBDurabilityForDisplay(ItemStack stack) {
		return MathHelper.hsvToRGB(Math.max(0.0F, (float)(1.0F - getDurabilityForDisplay(stack))) / 3.0F, 1.0F, 1.0F);
	}

	/**
	 * @see net.minecraft.item.Item#getRGBDurabilityForDisplay
	 */
	public int getMaxDamage(ItemStack stack) {
		return 0;
	}
}
