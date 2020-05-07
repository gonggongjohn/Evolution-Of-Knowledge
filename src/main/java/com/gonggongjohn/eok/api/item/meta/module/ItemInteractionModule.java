package com.gonggongjohn.eok.api.item.meta.module;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class ItemInteractionModule implements IItemModule {

	/**
	 * @see net.minecraft.item.Item#itemInteractionForEntity
	 */
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
		return false;
	}

	/**
	 * @see net.minecraft.item.Item#getMaxItemUseDuration
	 */
	public int getMaxItemUseDuration(ItemStack stack) {
		return 0;
	}

	/**
	 * @see net.minecraft.item.Item#doesSneakBypassUse
	 */
	public boolean doesSneakBypassUse(ItemStack stack, IBlockAccess world, BlockPos pos, EntityPlayer player) {
		return false;
	}

	/**
	 * @see net.minecraft.item.Item#canHarvestBlock
	 */
	public boolean canHarvestBlock(IBlockState state, ItemStack stack) {
		return stack.getItem().canHarvestBlock(state);
	}

	/**
	 * @see net.minecraft.item.Item#onItemRightClick
	 */
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
	}

	/**
	 * @see net.minecraft.item.Item#onItemUse
	 */
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		return EnumActionResult.PASS;
	}

	/**
	 * @see net.minecraft.item.Item#onItemUseFinish
	 */
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		return stack;
	}

	/**
	 * @see net.minecraft.item.Item#hitEntity
	 */
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		return false;
	}

	/**
	 * @see net.minecraft.item.Item#onBlockDestroyed
	 */
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
		return false;
	}

	/**
	 * @see net.minecraft.item.Item#onPlayerStoppedUsing
	 */
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {

	}

	/**
	 * @see net.minecraft.item.Item#onItemUseFirst
	 */
	public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
		return EnumActionResult.PASS;
	}

	/**
	 * @see net.minecraft.item.Item#onBlockStartBreak
	 */
	public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player) {
		return false;
	}

	/**
	 * @see net.minecraft.item.Item#onUsingTick
	 */
	public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {

	}

	/**
	 * @see net.minecraft.item.Item#onLeftClickEntity
	 */
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		return false;
	}

	/**
	 * @see net.minecraft.item.Item#onEntitySwing
	 */
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
		return false;
	}
}
