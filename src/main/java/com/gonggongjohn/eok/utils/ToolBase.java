package com.gonggongjohn.eok.utils;

import java.util.List;

import com.gonggongjohn.eok.EOK;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class ToolBase extends Item implements IHasModel{

	//默认值
	public float attackDamage() {
		return 1.0F;
	}

	public int maxDamage() {
		return 1;
	}

	public String name() {
		return "default_tool";
	}

	public int harvestLevel() {
		return 0;
	}

	public int stackLimit() {
		return 1;
	}

	public int burnTime() {
		return 0;
	}

	public int enchantAbility() {
		return 0;
	}


	public ToolBase() {
		this.setUnlocalizedName(name());
		this.setRegistryName(name());
		this.setCreativeTab(EOK.tabEOK);
		this.setHarvestLevel(this.getClass().getName(), harvestLevel());

	}

	@Override
	public void registerModel() {
		EOK.proxy.registerItemRenderer(this, 0, "inventory");
	}

	@Override
	public int getHarvestLevel(ItemStack stack, String toolClass, EntityPlayer player, IBlockState blockState) {
		return this.harvestLevel();
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		target.attackEntityFrom(DamageSource.causeMobDamage(attacker), this.attackDamage());
		return super.hitEntity(stack, target, attacker);
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		// TODO 自动生成的方法存根
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public boolean canHarvestBlock(IBlockState state, ItemStack stack) {
		// TODO 自动生成的方法存根
		return super.canHarvestBlock(state, stack);
	}

	@Override
	public float getDestroySpeed(ItemStack stack, IBlockState state) {
		// TODO 自动生成的方法存根
		return super.getDestroySpeed(stack, state);
	}

	@Override
	public int getItemStackLimit(ItemStack stack) {
		return this.stackLimit();
	}

	@Override
	public int getItemBurnTime(ItemStack itemStack) {
		return this.burnTime();
	}

	@Override
	public boolean isDamageable() {
		// TODO 自动生成的方法存根
		return super.isDamageable();
	}

	@Override
	public boolean isEnchantable(ItemStack stack) {
		// TODO 自动生成的方法存根
		return super.isEnchantable(stack);
	}

	@Override
	public int getMaxDamage(ItemStack stack) {
		return this.maxDamage();
	}

	@Override
	public int getItemEnchantability() {
		return this.enchantAbility();
	}

}
