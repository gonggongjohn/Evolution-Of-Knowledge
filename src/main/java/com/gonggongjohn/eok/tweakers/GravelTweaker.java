package com.gonggongjohn.eok.tweakers;

import java.util.Random;

import com.gonggongjohn.eok.handlers.ItemHandler;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GravelTweaker {
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void HarvestDropsEvent(HarvestDropsEvent event) {
		if (event.getWorld().isRemote)
			return;

		if (event.getState().getBlock() != Blocks.GRAVEL)
			return;

		if (event.isSilkTouching())
			return;

		Random rand = new Random();
		int i = rand.nextInt(100);
		if (i < 40) {
			event.getWorld().spawnEntity(new EntityItem(event.getWorld(), event.getPos().getX(), event.getPos().getY(),
					event.getPos().getZ(), new ItemStack(ItemHandler.flintFragment, 1)));
			event.setDropChance(0.0F);
		}
	}
	
	public GravelTweaker() {
		MinecraftForge.EVENT_BUS.register(this);
	}

}
