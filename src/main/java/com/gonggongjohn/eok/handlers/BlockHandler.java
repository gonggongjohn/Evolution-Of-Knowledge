package com.gonggongjohn.eok.handlers;

import com.gonggongjohn.eok.blocks.BlockStoneTable;
import com.gonggongjohn.eok.blocks.BlockTwoBarrelVacuumPump;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class BlockHandler {
    public static final List<Block> blocks = new ArrayList<Block>();

    public static final Block blockStone = new BlockStoneTable();
    public static final Block blockTwoBarrelVacuumPump = new BlockTwoBarrelVacuumPump();
}
