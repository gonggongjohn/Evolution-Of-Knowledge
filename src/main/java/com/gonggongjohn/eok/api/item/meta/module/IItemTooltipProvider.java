package com.gonggongjohn.eok.api.item.meta.module;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

@FunctionalInterface
public interface IItemTooltipProvider extends IItemModule {
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag);
}