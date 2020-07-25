package com.gonggongjohn.eok.api;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class HeatIndex {
	public float specificHeat;
	public float melt;
	public ItemStack input, output;

	public HeatIndex(ItemStack input, HeatRaw raw) {
		this.input = input;
		this.specificHeat = raw.specificHeat;
		melt = raw.melt;
	}

	public HeatIndex(ItemStack input, HeatRaw raw, ItemStack output) {
		this(input, raw);
		this.output = output;
	}

	public boolean hasOutput() {
		return output != null;
	}

	public boolean match(ItemStack stack) {
		if (stack == null) {
			return false;
		}
		Item item = stack.getItem();
		if (item != input.getItem()) {
			return false;
		}
		if (item.getHasSubtypes() && stack.getItemDamage() != input.getItemDamage()) {
			return false;
		}
		return true;
	}
}