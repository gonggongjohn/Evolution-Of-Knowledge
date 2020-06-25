package com.gonggongjohn.eok.handlers;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.utils.DocumentRenderer.GuiLoading;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.event.GuiScreenEvent.DrawScreenEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@SideOnly(Side.CLIENT)
@EventBusSubscriber(modid = EOK.MODID, value = Side.CLIENT)
public class EventHandlerClient {
	@SubscribeEvent
	public static void onTooltip(ItemTooltipEvent event) {
		ItemStack stack = event.getItemStack();
		if (stack != null && !stack.isEmpty() && stack.hasTagCompound()) {
			NBTTagCompound compound = stack.getTagCompound();
			if (compound != null && compound.hasKey("data.universe")) {
				int value = compound.getInteger("data.universe");
				List<String> tooltip = event.getToolTip();
				String str = "";
				str += I18n.format("eok.tooltip.data.universe", value);
				tooltip.add(str);
			}
			if (compound != null && compound.hasKey("data.research")) {
				int[] researchIDs = compound.getIntArray("data.research");
				List<String> tooltip = event.getToolTip();
				String str = "";
				str += I18n.format("eok.tooltip.data.research");
				tooltip.add(str);
                for (int researchID : researchIDs) {
                    String strResearch = "";
                    strResearch += I18n
                            .format("research." + EOK.researchDict.researchNameDict.get(researchID) + ".name");
                    tooltip.add(strResearch);
                }
			}
		}
	}
	/*
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void tweakGamma(GuiOpenEvent event) {
		BrightnessTweaker.tweak();
	}
	*/// TODO 重写
	static final GuiLoading loading = new GuiLoading();
	/**
	 * 仅用作调试，请勿更改
	 * 在这里添加断点和查看表达式来进行调试
	 */
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void renderTest(DrawScreenEvent event) {
		
	}
	
	/**
	 * 仅用作调试，请勿更改
	 */
	public static void debugMethod(int arg) {
		Minecraft.getMinecraft().displayGuiScreen(loading);
	}
}