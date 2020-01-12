package com.gonggongjohn.eok.tweakers;

import java.util.Arrays;

import com.gonggongjohn.eok.EOK;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemPickaxe;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(modid = EOK.MODID)
public class StoneTweaker {

	private static final Block[] STONE_BLOCKS = { 
			Blocks.STONE,
			Blocks.COBBLESTONE,
			Blocks.COBBLESTONE_WALL,
			Blocks.MOSSY_COBBLESTONE,
			Blocks.BRICK_BLOCK,
			Blocks.STONE_STAIRS,
			Blocks.STONE_SLAB,
			Blocks.STONE_SLAB2,
			Blocks.DOUBLE_STONE_SLAB,
			Blocks.DOUBLE_STONE_SLAB2,
			Blocks.BRICK_STAIRS,
			Blocks.STONEBRICK,
			Blocks.STONE_BRICK_STAIRS,
			Blocks.IRON_BARS,
			Blocks.IRON_DOOR,
			Blocks.IRON_TRAPDOOR
	};

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onDigging(BreakSpeed event) {
		if (event.getEntityPlayer().getHeldItemMainhand().getItem() instanceof ItemPickaxe) {
			return;
		}
		
		if (Arrays.asList(STONE_BLOCKS).contains(event.getState().getBlock())) {
			event.setNewSpeed(0.0F);
		}
	}
}
