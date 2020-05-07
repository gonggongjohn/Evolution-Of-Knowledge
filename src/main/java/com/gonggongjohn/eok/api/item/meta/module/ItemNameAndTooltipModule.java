package com.gonggongjohn.eok.api.item.meta.module;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings("deprecation")
public class ItemNameAndTooltipModule implements IItemModule {

	/**
	 * @see net.minecraft.item.Item#getHighlightTip
	 */
	public String getHighlightTip(ItemStack item, String displayName) {
		return displayName;
	}

	/**
	 * @see net.minecraft.item.Item#getItemStackDisplayName
	 */
	public String getItemStackDisplayName(ItemStack stack) {
		return I18n.translateToLocal(stack.getItem().getUnlocalizedNameInefficiently(stack) + ".name").trim();
	}

	/**
	 * @see net.minecraft.item.Item#addInformation
	 */
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {

	}
}
