package com.gonggongjohn.eok.api;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;

/**
 * @author Os-Ir
 * 
 *         Tool class for heatable items
 * 
 *         Reference from TFC
 */
public class HeatableTool {
	public static final String[] NUMBER = { "I", "II", "III", "IV", "V" };
	public static final String[] TEMPNAME = { "warming", "hot", "veryHot", "faintRed", "darkRed", "brightRed", "orange",
			"yellow", "yellowWhite", "white", "brilliantWhite" };
	public static final EnumChatFormatting[] TEMPCOLOR = { EnumChatFormatting.GRAY, EnumChatFormatting.GRAY,
			EnumChatFormatting.GRAY, EnumChatFormatting.DARK_RED, EnumChatFormatting.DARK_RED, EnumChatFormatting.RED,
			EnumChatFormatting.GOLD, EnumChatFormatting.YELLOW, EnumChatFormatting.YELLOW, EnumChatFormatting.WHITE,
			EnumChatFormatting.WHITE };
	public static final float[] TEMPVALUE = { 20, 80, 210, 480, 580, 730, 930, 1100, 1300, 1400, 1500 };

	public static String getHeatColor(ItemStack stack) {
		if (!hasTemperature(stack)) {
			return null;
		}
		String str = "";
		int i, j;
		float temperature = getTemperature(stack);
		if (temperature < TEMPVALUE[TEMPVALUE.length - 1]) {
			for (i = 0; i < TEMPVALUE.length - 1; i++) {
				if (temperature >= TEMPVALUE[i + 1]) {
					continue;
				}
				str = TEMPCOLOR[i] + I18n.format("item.heatable." + TEMPNAME[i]) + " "
						+ NUMBER[(int) ((temperature - TEMPVALUE[i]) / (TEMPVALUE[i + 1] - TEMPVALUE[i]) * 5)];
				break;
			}
		} else {
			str = TEMPCOLOR[TEMPVALUE.length - 1] + I18n.format("item.heatable." + TEMPNAME[TEMPVALUE.length - 1]);
		}
		return str;
	}

	public static ItemStack updateTemperature(ItemStack stack) {
		if (hasTemperature(stack)) {
			float temperature = getTemperature(stack);
			temperature -= getIncreaseTemperature(stack, 0.1F, 20);
			NBTTagCompound nbt = stack.getTagCompound();
			stack = setTemperature(stack, temperature);
		}
		return stack;
	}

	public static float getSpecificHeat(ItemStack stack) {
		HeatRegistry registry = HeatRegistry.getInstance();
		if (stack == null || registry == null) {
			return 1F;
		}
		HeatIndex index = registry.findIndex(stack);
		if (index == null) {
			return 1F;
		}
		return index.specificHeat;
	}

	public static float getMeltHeat(ItemStack stack) {
		HeatRegistry registry = HeatRegistry.getInstance();
		if (stack == null || registry == null) {
			return 20F;
		}
		HeatIndex index = registry.findIndex(stack);
		if (index == null) {
			return 20F;
		}
		return index.melt;
	}

	public static boolean hasTemperature(ItemStack stack) {
		if (stack == null) {
			return false;
		}
		if (stack.hasTagCompound()) {
			if (stack.getTagCompound().hasKey("temperature")) {
				return true;
			}
		}
		return false;
	}

	public static float getTemperature(ItemStack stack) {
		if (hasTemperature(stack)) {
			return stack.getTagCompound().getFloat("temperature");
		}
		return 20F;
	}

	public static ItemStack setTemperature(ItemStack stack, float temperature) {
		NBTTagCompound nbt;
		if (hasTemperature(stack)) {
			nbt = stack.getTagCompound();
		} else {
			nbt = new NBTTagCompound();
		}
		if (temperature <= 20) {
			nbt.removeTag("temperature");
		} else {
			nbt.setFloat("temperature", temperature);
		}
		stack.setTagCompound(nbt);
		return stack;
	}

	public static boolean isWorkable(ItemStack stack) {
		if (!hasTemperature(stack)) {
			return false;
		}
		if (isMelt(stack)) {
			return false;
		}
		return getTemperature(stack) > getMeltHeat(stack) * 0.6;
	}

	public static boolean isWeldable(ItemStack stack) {
		if (!hasTemperature(stack)) {
			return false;
		}
		if (isMelt(stack)) {
			return false;
		}
		return getTemperature(stack) > getMeltHeat(stack) * 0.8;
	}

	public static boolean isDanger(ItemStack stack) {
		if (!hasTemperature(stack)) {
			return false;
		}
		if (isMelt(stack)) {
			return false;
		}
		return getTemperature(stack) > getMeltHeat(stack) * 0.9;
	}

	public static boolean isMelt(ItemStack stack) {
		if (!hasTemperature(stack)) {
			return false;
		}
		return getTemperature(stack) > getMeltHeat(stack);
	}

	public static float getIncreaseTemperature(ItemStack stack, float velo, float fire) {
		return velo * 50 / getSpecificHeat(stack) * Math.abs(fire - getTemperature(stack)) / 200;
	}
}