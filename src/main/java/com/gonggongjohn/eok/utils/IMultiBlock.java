package com.gonggongjohn.eok.utils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IMultiBlock {
    public JudgeWithFacing checkStructure(World worldIn, BlockPos pos, IBlockState state, int dimensionNum, String structureName);
}
