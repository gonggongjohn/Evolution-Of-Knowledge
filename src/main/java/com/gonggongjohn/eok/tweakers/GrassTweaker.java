package com.gonggongjohn.eok.tweakers;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.handlers.ItemHandler;

import net.minecraft.block.BlockTallGrass;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = EOK.MODID)
public class GrassTweaker {
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void des(BlockEvent.HarvestDropsEvent event) {
		if (!event.getWorld().isRemote) {
			if (event.getState().getBlock() instanceof BlockTallGrass) {
				event.getDrops().add(new ItemStack(Blocks.TALLGRASS, 1, 1));
				event.setDropChance(1.0F);
				event.getWorld().spawnEntity(new EntityItem(event.getWorld(), event.getPos().getZ(),
						event.getPos().getX(), event.getPos().getY(), new ItemStack(Blocks.TALLGRASS, 1, 1)));
			}
		}
	}
}
