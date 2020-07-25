package com.gonggongjohn.eok.data;

import java.util.List;

import com.gonggongjohn.eok.api.HeatRegistry;
import com.gonggongjohn.eok.api.HeatableTool;
import com.gonggongjohn.eok.handlers.HeatableItemHandler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public class EventTweaker {
	public EventTweaker() {
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void tempUpdateTweaker(LivingUpdateEvent e) {
		if (!(e.entityLiving instanceof EntityPlayer)) {
			return;
		}
		EntityPlayer player = (EntityPlayer) e.entityLiving;
		World world = player.worldObj;
		ItemStack stack = null;
		int i, tmp;
		for (i = 0; i < 36; i++) {
			stack = player.inventory.mainInventory[i];
			if (stack == null) {
				continue;
			}
			if (stack != null && stack.stackSize <= 0) {
				stack = null;
				continue;
			}
			player.inventory.mainInventory[i] = HeatableTool.updateTemperature(stack);
		}
	}

	@SubscribeEvent
	public void tooltipTweaker(ItemTooltipEvent e) {
		ItemStack stack = e.itemStack;
		EntityPlayer player = e.entityPlayer;
		List tooltip = e.toolTip;
		if (HeatableTool.hasTemperature(stack)) {
			String str = "";
			HeatRegistry registry = HeatRegistry.getInstance();
			ItemStack output = registry.findIndex(stack).output;
			if (output != null && output.getItem() == HeatableItemHandler.melt) {
				if (HeatableTool.isMelt(stack)) {
					str += EnumChatFormatting.WHITE + I18n.format("item.heatable.melt");
				} else {
					if (HeatableTool.isDanger(stack)) {
						if (player.worldObj.getTotalWorldTime() % 20 < 10) {
							str += EnumChatFormatting.WHITE;
						} else {
							str += EnumChatFormatting.RED;
						}
						str += I18n.format("item.heatable.danger") + EnumChatFormatting.WHITE + " | ";
					}
					if (HeatableTool.isWeldable(stack)) {
						str += EnumChatFormatting.WHITE + I18n.format("item.heatable.weldable") + " | ";
					}
					if (HeatableTool.isWorkable(stack)) {
						str += EnumChatFormatting.WHITE + I18n.format("item.heatable.workable");
					}
				}
				if (!str.equals("")) {
					tooltip.add(str);
				}
			}
			tooltip.add(I18n.format("item.heatable.temperature", (int) HeatableTool.getTemperature(stack)) + " | "
					+ HeatableTool.getHeatColor(stack));
		}
	}
}