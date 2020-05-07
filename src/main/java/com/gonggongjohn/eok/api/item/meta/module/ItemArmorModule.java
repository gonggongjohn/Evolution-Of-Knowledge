package com.gonggongjohn.eok.api.item.meta.module;

import javax.annotation.Nullable;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemArmorModule implements IItemModule {

	/**
	 * @see net.minecraft.item.Item#onArmorTick
	 */
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {

	}

	/**
	 * @see net.minecraft.item.Item#isValidArmor
	 */
	public boolean isValidArmor(ItemStack stack, EntityEquipmentSlot armorType, Entity entity) {
		return EntityLiving.getSlotForItemStack(stack) == armorType;
	}

	/**
	 * @see net.minecraft.item.Item#getEquipmentSlot
	 */
	@Nullable
	public EntityEquipmentSlot getEquipmentSlot(ItemStack stack) {
		return null;
	}

	/**
	 * @see net.minecraft.item.Item#getArmorTexture
	 */
	@Nullable
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		return null;
	}

	/**
	 * @see net.minecraft.item.Item#getArmorModel
	 */
	@SideOnly(Side.CLIENT)
	@Nullable
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default) {
		return null;
	}

	/**
	 * @see net.minecraft.item.Item#renderHelmetOverlay
	 */
	@SideOnly(Side.CLIENT)
	public void renderHelmetOverlay(ItemStack stack, EntityPlayer player, ScaledResolution resolution, float partialTicks) {
		
	}
}
