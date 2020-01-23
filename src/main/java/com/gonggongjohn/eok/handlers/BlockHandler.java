package com.gonggongjohn.eok.handlers;

import com.gonggongjohn.eok.blocks.*;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class BlockHandler {
    public static final List<Block> blocks = new ArrayList<Block>();

    public static final Block blockStoneTable = new BlockStoneTable();
    public static final Block blockTwoBarrelVacuumPump = new BlockTwoBarrelVacuumPump();
    public static final Block blockElementaryResearchTable = new BlockElementaryResearchTable();
    public static final Block blockFirstMachine = new BlockFirstMachine();
    public static final Block blockHaystack=new BlockHaystack();
    public static final Block blockDriedHaystack=new BlockDriedHaystack();
    public static final Block blockDecomposedHaystack= new BlockDecomposedHaystack();
    public static final Block blockHayTorchBase = new BlockHayTorchBase();
    public static final Block blockHayTorchBaseLit = new BlockHayTorchBaseLit();

    public static final Block blockTest2DCore = new BlockTest2DCore();
    public static final Block blockTest3DCore = new BlockTest3DCore();
}
