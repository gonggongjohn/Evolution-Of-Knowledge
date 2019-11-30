package com.gonggongjohn.eok.handlers;

import com.gonggongjohn.eok.EOK;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@Mod.EventBusSubscriber(modid = EOK.MODID)
public class EventHandler {
    @SideOnly(Side.CLIENT)
	@SubscribeEvent
    public static void Tooltip(ItemTooltipEvent event){
        ItemStack stack = event.getItemStack();
        if (stack != null && !stack.isEmpty() && stack.hasTagCompound()) {
            NBTTagCompound compound = stack.getTagCompound();
            if(compound != null && compound.hasKey("data.universe")){
                int value = compound.getInteger("data.universe");
                List<String> tooltip = event.getToolTip();
                String str = "";
                str += I18n.format("tooltip.data.universe", value);
                tooltip.add(str);
            }
        }
    }

}
