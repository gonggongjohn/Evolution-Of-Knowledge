package com.gonggongjohn.eok.handlers;

import com.gonggongjohn.eok.EOK;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = EOK.MODID)
public class EventHandler {
    @SubscribeEvent
    public static void Tooltip(ItemTooltipEvent event){
        ItemStack stack = event.getItemStack();
        if (stack != null && !stack.isEmpty() && stack.hasTagCompound()) {
            //TODO
        }
    }
}
