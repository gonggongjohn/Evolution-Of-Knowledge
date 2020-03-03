package com.gonggongjohn.eok.items;

import gregtech.api.items.metaitem.stats.IItemDurabilityManager;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class DurabilityStone implements IItemDurabilityManager {
	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("eok.stone")) {
			NBTTagCompound nbt = stack.getTagCompound().getCompoundTag("eok.stone");
			if (nbt.hasKey("damage")) {
				return ((double) nbt.getInteger("damage")) / 80000;
			}
		}
		return 0;
	}

	@Override
	public int getRGBDurabilityForDisplay(ItemStack stack) {
		if (stack.getTagCompound().hasKey("eok.stone")) {
			NBTTagCompound nbt = stack.getTagCompound().getCompoundTag("eok.stone");
			float durability = (float) this.getDurabilityForDisplay(stack);
			if (0.9 <= durability && durability < 1) {
				return 0xff0000;
			}
			if (0.7 <= durability && durability < 0.9) {
				return 0xffff00;
			}
		}
		return 0x00ff00;
	}

	@Override
	public boolean showsDurabilityBar(ItemStack stack) {
		return this.getDurabilityForDisplay(stack) > 0;
	}
}