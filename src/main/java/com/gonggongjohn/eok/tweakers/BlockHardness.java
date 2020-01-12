package com.gonggongjohn.eok.tweakers;

import com.gonggongjohn.eok.handlers.ConfigHandler;

import net.minecraft.init.Blocks;



public class BlockHardness {
	//硬度示例:
	//=========================
	//石头硬度：8
	//圆石硬度：5
	//原木硬度：6
	//木板硬度：5
	//草，作物硬度：0.5
	//泥土硬度：5
	//大多数机器硬度为5
	
	public static void setup() {
		if(ConfigHandler.increaseBlockHardness == false)
			return;
		tweakModBlocks();
		tweakVanillaBlocks();
	}
	
	//修改mod方块硬度
	public static void tweakModBlocks() {
		
	}
	
	//修改原版方块硬度
	public static void tweakVanillaBlocks() {
		Blocks.STONE.setHardness(8.0F);
		Blocks.GRASS.setHardness(5.2F);
		Blocks.DIRT.setHardness(5.0F);
		Blocks.COBBLESTONE.setHardness(5.0F);
		Blocks.PLANKS.setHardness(5.0F);
		Blocks.SAPLING.setHardness(0.5F);
		Blocks.SAND.setHardness(5.0F);
		Blocks.GRAVEL.setHardness(5.0F);
		Blocks.GOLD_ORE.setHardness(10.0F);
		Blocks.IRON_ORE.setHardness(9.0F);
		Blocks.COAL_ORE.setHardness(8.5F);
		Blocks.LOG.setHardness(6.0F);
		Blocks.LEAVES.setHardness(5.0F);
		Blocks.SPONGE.setHardness(4.0F);
		Blocks.GLASS.setHardness(1.5F);
		Blocks.LAPIS_ORE.setHardness(9.0F);
		Blocks.LAPIS_BLOCK.setHardness(5.0F);
		Blocks.DISPENSER.setHardness(5.0F);
		Blocks.SANDSTONE.setHardness(5.0F);
		Blocks.NOTEBLOCK.setHardness(5.0F);
		Blocks.BED.setHardness(5.0F);
		//Blocks.GOLDEN_RAIL 不变
		//Blocks.DETECTOR_RAIL 不变
		Blocks.STICKY_PISTON.setHardness(5.0F);
		//Blocks.WEB 不变
		Blocks.TALLGRASS.setHardness(0.5F);
		Blocks.DEADBUSH.setHardness(0.5F);
		Blocks.PISTON.setHardness(5.0F);
		Blocks.PISTON_HEAD.setHardness(5.0F);
		Blocks.WOOL.setHardness(4.0F);
		Blocks.YELLOW_FLOWER.setHardness(0.5F);
		Blocks.RED_FLOWER.setHardness(0.5F);
		Blocks.BROWN_MUSHROOM.setHardness(0.5F);
		Blocks.RED_MUSHROOM.setHardness(0.5F);
		Blocks.GOLD_BLOCK.setHardness(5.0F);
		Blocks.IRON_BLOCK.setHardness(5.0F);
		Blocks.DOUBLE_STONE_SLAB.setHardness(5.0F);
		Blocks.STONE_SLAB.setHardness(5.0F);
		Blocks.BRICK_BLOCK.setHardness(5.0F);
		Blocks.TNT.setHardness(1.0F);
		Blocks.BOOKSHELF.setHardness(5.0F);
		Blocks.MOSSY_COBBLESTONE.setHardness(5.0F);
		Blocks.OBSIDIAN.setHardness(60.0F);
		//Blocks.TORCH 不变
		//Blocks.FIRE 不变
		//Blocks.MOB_SPAWNER 不变
		Blocks.OAK_STAIRS.setHardness(5.0F);
		Blocks.CHEST.setHardness(5.0F);
		Blocks.REDSTONE_WIRE.setHardness(0.5F);
		Blocks.DIAMOND_ORE.setHardness(11.0F);
		Blocks.DIAMOND_BLOCK.setHardness(5.0F);
		Blocks.CRAFTING_TABLE.setHardness(5.0F);
		Blocks.WHEAT.setHardness(0.5F);
		Blocks.FARMLAND.setHardness(5.0F);
		Blocks.FURNACE.setHardness(5.0F);
		Blocks.LIT_FURNACE.setHardness(5.0F);
		//Blocks.STANDING_SIGN 不变
		Blocks.OAK_DOOR.setHardness(5.0F);
		//Blocks.LADDER 不变
		//Blocks.RAIL 不变
		Blocks.STONE_STAIRS.setHardness(5.0F);
		//Blocks.WALL_SIGN 不变
		Blocks.LEVER.setHardness(0.5F);
		Blocks.STONE_PRESSURE_PLATE.setHardness(5.0F);
		Blocks.IRON_DOOR.setHardness(5.0F);
		Blocks.WOODEN_PRESSURE_PLATE.setHardness(5.0F);
		Blocks.REDSTONE_ORE.setHardness(9.0F);
		Blocks.LIT_REDSTONE_ORE.setHardness(9.0F);
		Blocks.SNOW_LAYER.setHardness(4.0F);
		Blocks.SNOW.setHardness(5.0F);
		Blocks.ICE.setHardness(5.0F);
		//Blocks.CACTUS 不变
		Blocks.CLAY.setHardness(5.0F);
		Blocks.REEDS.setHardness(0.5F);
		Blocks.JUKEBOX.setHardness(5.0F);
		Blocks.OAK_FENCE.setHardness(5.0F);
		Blocks.PUMPKIN.setHardness(4.0F);
		Blocks.NETHERRACK.setHardness(4.0F);
		Blocks.SOUL_SAND.setHardness(5.0F);
		Blocks.GLOWSTONE.setHardness(1.5F);
		Blocks.LIT_PUMPKIN.setHardness(4.0F);
		//Blocks.CAKE 不变
		Blocks.UNPOWERED_REPEATER.setHardness(0.5F);
		Blocks.POWERED_REPEATER.setHardness(0.5F);
		Blocks.STAINED_GLASS.setHardness(1.5F);
		Blocks.TRAPDOOR.setHardness(5.0F);
		//Blocks.MONSTER_EGG 不变
		Blocks.STONEBRICK.setHardness(5.0F);
		//Blocks.BROWN_MUSHROOM_BLOCK 不变
		//Blocks.RED_MUSHROOM_BLOCK 不变
		Blocks.IRON_BARS.setHardness(5.0F);
		Blocks.GLASS_PANE.setHardness(1.5F);
		Blocks.MELON_BLOCK.setHardness(4.0F);
		Blocks.PUMPKIN_STEM.setHardness(0.5F);
		Blocks.MELON_STEM.setHardness(0.5F);
		//Blocks.VINE 不变
		Blocks.DARK_OAK_FENCE_GATE.setHardness(5.0F);
		Blocks.BRICK_STAIRS.setHardness(5.0F);
		Blocks.STONE_BRICK_STAIRS.setHardness(5.0F);
		Blocks.MYCELIUM.setHardness(5.2F);
		Blocks.WATERLILY.setHardness(0.5F);
		Blocks.NETHER_BRICK.setHardness(5.0F);
		Blocks.NETHER_BRICK_FENCE.setHardness(5.0F);
		Blocks.NETHER_BRICK_STAIRS.setHardness(5.0F);
		Blocks.NETHER_WART.setHardness(0.5F);
		Blocks.ENCHANTING_TABLE.setHardness(5.0F);
		Blocks.BREWING_STAND.setHardness(5.0F);
		Blocks.CAULDRON.setHardness(5.0F);
		Blocks.END_STONE.setHardness(7.0F);
		Blocks.REDSTONE_LAMP.setHardness(1.5F);
		Blocks.LIT_REDSTONE_LAMP.setHardness(1.5F);
		Blocks.DOUBLE_WOODEN_SLAB.setHardness(5.0F);
		Blocks.WOODEN_SLAB.setHardness(5.0F);
		Blocks.COCOA.setHardness(0.5F);
		Blocks.SANDSTONE_STAIRS.setHardness(5.0F);
		Blocks.EMERALD_ORE.setHardness(9.0F);
		Blocks.ENDER_CHEST.setHardness(5.0F);
		Blocks.TRIPWIRE_HOOK.setHardness(0.5F);
		Blocks.TRIPWIRE.setHardness(0.5F);
		Blocks.EMERALD_BLOCK.setHardness(5.0F);
		Blocks.SPRUCE_STAIRS.setHardness(5.0F);
		Blocks.BIRCH_STAIRS.setHardness(5.0F);
		Blocks.JUNGLE_STAIRS.setHardness(5.0F);
		Blocks.BEACON.setHardness(5.0F);
		Blocks.COBBLESTONE_WALL.setHardness(5.0F);
		Blocks.FLOWER_POT.setHardness(0.5F);
		Blocks.CARROTS.setHardness(0.5F);
		Blocks.POTATOES.setHardness(0.5F);
		//Blocks.WOODEN_BUTTON 不变
		//Blocks.SKULL 不变
		Blocks.ANVIL.setHardness(5.0F);
		Blocks.TRAPPED_CHEST.setHardness(5.0F);
		Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE.setHardness(5.0F);
		Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE.setHardness(5.0F);
		Blocks.UNPOWERED_COMPARATOR.setHardness(0.5F);
		Blocks.POWERED_COMPARATOR.setHardness(0.5F);
		Blocks.DAYLIGHT_DETECTOR.setHardness(5.0F);
		Blocks.REDSTONE_BLOCK.setHardness(5.0F);
		Blocks.QUARTZ_ORE.setHardness(9.0F);
		Blocks.HOPPER.setHardness(5.0F);
		Blocks.QUARTZ_BLOCK.setHardness(5.0F);
		Blocks.QUARTZ_STAIRS.setHardness(5.0F);
		//Blocks.ACTIVATOR_RAIL 不变
		Blocks.DROPPER.setHardness(5.0F);
		Blocks.STAINED_HARDENED_CLAY.setHardness(5.0F);
		Blocks.STAINED_GLASS_PANE.setHardness(1.5F);
		Blocks.LEAVES2.setHardness(5.0F);
		Blocks.LOG2.setHardness(6.0F);
		Blocks.ACACIA_STAIRS.setHardness(5.0F);
		Blocks.DARK_OAK_STAIRS.setHardness(5.0F);
		Blocks.SLIME_BLOCK.setHardness(0.5F);
		Blocks.IRON_TRAPDOOR.setHardness(5.0F);
		Blocks.PRISMARINE.setHardness(5.0F);
		Blocks.SEA_LANTERN.setHardness(1.5F);
		Blocks.HAY_BLOCK.setHardness(1.5F);
		//Blocks.CARPET 不变
		Blocks.HARDENED_CLAY.setHardness(5.0F);
		Blocks.COAL_BLOCK.setHardness(5.0F);
		Blocks.PACKED_ICE.setHardness(5.0F);
		Blocks.DOUBLE_PLANT.setHardness(0.5F);
		//Blocks.STANDING_BANNER 不变
		//Blocks.WALL_BANNER 不变
		Blocks.DAYLIGHT_DETECTOR_INVERTED.setHardness(5.0F);
		Blocks.RED_SANDSTONE.setHardness(5.0F);
		Blocks.RED_SANDSTONE_STAIRS.setHardness(5.0F);
		Blocks.DOUBLE_STONE_SLAB2.setHardness(5.0F);
		Blocks.STONE_SLAB2.setHardness(5.0F);
		
		Blocks.SPRUCE_FENCE_GATE.setHardness(5.0F);
		Blocks.BIRCH_FENCE_GATE.setHardness(5.0F);
		Blocks.JUNGLE_FENCE_GATE.setHardness(5.0F);
		Blocks.DARK_OAK_FENCE_GATE.setHardness(5.0F);
		Blocks.ACACIA_FENCE_GATE.setHardness(5.0F);
		
		Blocks.SPRUCE_FENCE.setHardness(5.0F);
		Blocks.BIRCH_FENCE.setHardness(5.0F);
		Blocks.JUNGLE_FENCE.setHardness(5.0F);
		Blocks.DARK_OAK_FENCE.setHardness(5.0F);
		Blocks.ACACIA_FENCE.setHardness(5.0F);
		
		Blocks.SPRUCE_DOOR.setHardness(5.0F);
		Blocks.BIRCH_DOOR.setHardness(5.0F);
		Blocks.JUNGLE_DOOR.setHardness(5.0F);
		Blocks.DARK_OAK_DOOR.setHardness(5.0F);
		Blocks.ACACIA_DOOR.setHardness(5.0F);
		
		Blocks.END_ROD.setHardness(0.5F);
		Blocks.CHORUS_PLANT.setHardness(0.5F);
		Blocks.CHORUS_FLOWER.setHardness(0.5F);
		Blocks.PURPUR_BLOCK.setHardness(5.0F);
		Blocks.PURPUR_PILLAR.setHardness(5.0F);
		Blocks.PURPUR_STAIRS.setHardness(0.5F);
		Blocks.PURPUR_DOUBLE_SLAB.setHardness(5.0F);
		Blocks.PURPUR_SLAB.setHardness(5.0F);
		Blocks.END_BRICKS.setHardness(5.0F);
		Blocks.BEETROOTS.setHardness(0.5F);
		Blocks.GRASS_PATH.setHardness(5.0F);
		Blocks.FROSTED_ICE.setHardness(5.0F);
		Blocks.MAGMA.setHardness(5.0F);
		Blocks.NETHER_WART_BLOCK.setHardness(5.0F);
		Blocks.RED_NETHER_BRICK.setHardness(5.0F);
		Blocks.BONE_BLOCK.setHardness(5.0F);
		Blocks.OBSERVER.setHardness(5.0F);
		Blocks.WHITE_GLAZED_TERRACOTTA.setHardness(5.0F);
		Blocks.ORANGE_GLAZED_TERRACOTTA.setHardness(5.0F);
		Blocks.MAGENTA_GLAZED_TERRACOTTA.setHardness(5.0F);
		Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA.setHardness(5.0F);
		Blocks.YELLOW_GLAZED_TERRACOTTA.setHardness(5.0F);
		Blocks.LIME_GLAZED_TERRACOTTA.setHardness(5.0F);
		Blocks.PINK_GLAZED_TERRACOTTA.setHardness(5.0F);
		Blocks.GRAY_GLAZED_TERRACOTTA.setHardness(5.0F);
		Blocks.CYAN_GLAZED_TERRACOTTA.setHardness(5.0F);
		Blocks.PURPLE_GLAZED_TERRACOTTA.setHardness(5.0F);
		Blocks.BLUE_GLAZED_TERRACOTTA.setHardness(5.0F);
		Blocks.BROWN_GLAZED_TERRACOTTA.setHardness(5.0F);
		Blocks.GREEN_GLAZED_TERRACOTTA.setHardness(5.0F);
		Blocks.RED_GLAZED_TERRACOTTA.setHardness(5.0F);
		Blocks.BLACK_GLAZED_TERRACOTTA.setHardness(5.0F);
		Blocks.CONCRETE.setHardness(5.0F);
		Blocks.CONCRETE_POWDER.setHardness(5.0F);
	}
}