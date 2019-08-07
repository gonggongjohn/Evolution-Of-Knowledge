package com.gonggongjohn.eok.items;

import java.util.List;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.handlers.ItemHandler;
import com.gonggongjohn.eok.utils.IHasModel;
import com.google.common.collect.Multimap;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/*
 * 警告：
 * 这只是一段临时代码，以后要重写
 * 大多数内容都是直接从net.minecraft.item.ItemSword里复制来的
 */

public class ItemRoughFlintSpear extends Item implements IHasModel {

	private final String name = "rough_flint_spear";

	private final float attackDamage = 4.0F;

	public ItemRoughFlintSpear() {
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(EOK.tabEOK);
		this.maxStackSize = 1;
		this.setMaxDamage(47);
		ItemHandler.items.add(this);
	}

	public float getAttackDamage() {
		return this.attackDamage;
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(I18n.format("tooltip.item.rough_flint_spear", this.attackDamage, this.attackDamage / 2));
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		stack.damageItem(1, attacker);
		target.attackEntityFrom(DamageSource.causeMobDamage(attacker), 4.0F);
		return true;
	}

	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos,
			EntityLivingBase entityLiving) {
		if ((double) state.getBlockHardness(worldIn, pos) != 0.0D) {
			stack.damageItem(2, entityLiving);
		}

		return true;
	}

	@SideOnly(Side.CLIENT)
	public boolean isFull3D() {
		return true;
	}

	public int getItemEnchantability() {
		return 5;
	}

	@Override
	public void registerModel() {
		EOK.proxy.registerItemRenderer(this, 0, "inventory");

	}

}
