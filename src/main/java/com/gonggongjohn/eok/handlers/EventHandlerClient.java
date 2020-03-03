package com.gonggongjohn.eok.handlers;

import java.util.List;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.tweakers.BrightnessTweaker;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@EventBusSubscriber(modid = EOK.MODID, value = Side.CLIENT)
public class EventHandlerClient {
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onTooltip(ItemTooltipEvent event) {
		ItemStack stack = event.getItemStack();
		if (stack != null && !stack.isEmpty() && stack.hasTagCompound()) {
			NBTTagCompound compound = stack.getTagCompound();
			if (compound != null && compound.hasKey("data.universe")) {
				int value = compound.getInteger("data.universe");
				List<String> tooltip = event.getToolTip();
				String str = "";
				str += I18n.format("tooltip.data.universe", value);
				tooltip.add(str);
			}
			if (compound != null && compound.hasKey("data.research")) {
				int[] researchIDs = compound.getIntArray("data.research");
				List<String> tooltip = event.getToolTip();
				String str = "";
				str += I18n.format("tooltip.data.research");
				tooltip.add(str);
				for (int i = 0; i < researchIDs.length; i++) {
					String strResearch = "";
					strResearch += I18n
							.format("research." + EOK.researchDict.researchNameDict.get(researchIDs[i]) + ".name");
					tooltip.add(strResearch);
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void tweakGamma(GuiOpenEvent event) {
		BrightnessTweaker.tweak();
	}
}