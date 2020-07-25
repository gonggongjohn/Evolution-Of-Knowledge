package com.gonggongjohn.eok.handlers;

import com.gonggongjohn.eok.blocks.*;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber
public class BlockHandler {
    public static final List<Block> BLOCK_REGISTRY = new ArrayList<>();

    public static final Block blockStoneTable = new BlockStoneTable();
    public static final Block blockTwoBarrelVacuumPump = new BlockTwoBarrelVacuumPump();
    public static final Block blockElementaryResearchTable = new BlockElementaryResearchTable();
    public static final Block blockPrimaryBlueprintTable = new BlockPrimaryBlueprintTable();
    public static final Block blockHaystack = new BlockHaystack();
    public static final Block blockDriedHaystack = new BlockDriedHaystack();
    public static final Block blockDecomposedHaystack = new BlockDecomposedHaystack();
    public static final Block blockStoneRock = new BlockStoneRock();
    public static final Block blockStick = new BlockStick();
    public static final Block blockBasketBall = new BlockBasketBall();
    public static final Block blockStoneMill = new BlockStoneMill();
    public static final Block blockApple = new BlockApple();
    public static final Block blockRedstoneLog = new BlockRedstoneLog();
    public static final Block blockSecondaryBlueprintTable = new BlockSecondaryBlueprintTable();

    public static void registerBlock(Block block, Item item) {
        BLOCK_REGISTRY.add(block);
        ItemHandler.ITEM_REGISTRY.add(item);
    }
}