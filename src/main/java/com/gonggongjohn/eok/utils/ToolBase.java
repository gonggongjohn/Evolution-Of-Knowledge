package com.gonggongjohn.eok.utils;

import java.util.List;

import com.gonggongjohn.eok.EOK;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import com.gonggongjohn.eok.utils.EOKToolMaterial;

public class ToolBase extends Item implements IHasModel {

	public String materialName = "NULL";
	public float attackDamage = 1.0F;
	public int maxDamage = 1;
	public String name = "default_tool_axe";
	public int harvestLevel = 0;
	public int stackLimit = 1;
	public int burnTime = 0;
	public int enchantAbility = 0;
	public float efficiency = 1.0F;

	public ToolBase() {
		
		
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(EOK.tabEOK);
		this.setHarvestLevel(this.getClass().getName(), this.harvestLevel);
	}


	
	
	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		
		NBTTagCompound nbt = stack.getTagCompound();
		if(nbt == null) nbt = new NBTTagCompound();
		nbt.setString("EOKToolMaterial", this.materialName);
		nbt.setFloat("attackDamage", this.attackDamage);
		nbt.setInteger("maxDamage", this.maxDamage);
		nbt.setInteger("harvestLevel", this.harvestLevel);
		nbt.setInteger("enchantAbility", this.enchantAbility);
		nbt.setFloat("efficiency", this.efficiency);
		super.onCreated(stack, worldIn, playerIn);
	}




	@Override
	public void registerModel() {
		EOK.proxy.registerItemRenderer(this, 0, "inventory");
	}

	@Override
	public int getHarvestLevel(ItemStack stack, String toolClass, EntityPlayer player, IBlockState blockState) {
		return this.harvestLevel;
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		target.attackEntityFrom(DamageSource.causeMobDamage(attacker), this.attackDamage);
		return super.hitEntity(stack, target, attacker);
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(I18n.format("tooltip.toolbase.material_name",this.materialName));
		tooltip.add(I18n.format("tooltip.toolbase.harvest_level", this.harvestLevel));
		tooltip.add(I18n.format("tooltip.toolbase.attack_damage", this.attackDamage,this.attackDamage/2));
		tooltip.add(I18n.format("tooltip.toolbase.durability", this.maxDamage-this.getDamage(stack),this.maxDamage));
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
		return this.stackLimit;
	}

	@Override
	public int getItemBurnTime(ItemStack itemStack) {
		return this.burnTime;
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
		return this.maxDamage;
	}

	@Override
	public int getItemEnchantability() {
		return this.enchantAbility;
	}

}
