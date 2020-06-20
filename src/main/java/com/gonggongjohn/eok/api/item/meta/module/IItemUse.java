package com.gonggongjohn.eok.api.item.meta.module;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IItemUse extends IItemModule {
	EnumAction getItemUseAction(ItemStack stack);

	int getMaxItemUseDuration(ItemStack stack);

	default EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX,
			float hitY, float hitZ, EnumHand hand) {
		return EnumActionResult.PASS;
	}

	default ActionResult<ItemStack> onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		return ActionResult.newResult(EnumActionResult.PASS, player.getHeldItem(hand));
	}

	default void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {

	}

	default void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase player, int timeLeft) {

	}

	default ItemStack onItemUseFinish(ItemStack stack, EntityLivingBase player) {
		return stack;
	}
}