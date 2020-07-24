package com.gonggongjohn.eok.worldgen;

import com.gonggongjohn.eok.blocks.BlockStoneRock;
import com.gonggongjohn.eok.handlers.BlockHandler;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class WorldGenBlockStoneAndStick implements IWorldGenerator {

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        for (int i = 0; i < 3; i++)
            if (random.nextInt(13) < 7) {
                BlockPos pos = new BlockPos(chunkX * 16 + 8 + random.nextInt(15), 0, chunkZ * 16 + 8 + random.nextInt(15));
                pos = world.getTopSolidOrLiquidBlock(pos).down();
                if (this.canSustainSt(world, pos)) {
                    world.setBlockState(pos.up(), ((BlockStoneRock) BlockHandler.blockStoneRock).getRandomState());
                }
            }
        for (int i = 0; i < 2; i++)
            if (random.nextInt(13) < 7) {
                BlockPos pos = new BlockPos(chunkX * 16 + 8 + random.nextInt(15), 0, chunkZ * 16 + 8 + random.nextInt(15));
                pos = world.getTopSolidOrLiquidBlock(pos).down();
                if (this.canSustainSt(world, pos)) {
                    world.setBlockState(pos.up(), BlockHandler.blockStick.getDefaultState());
                }
            }
    }

    private boolean canSustainSt(World world, BlockPos pos) {
        Block block = world.getBlockState(pos).getBlock();
        boolean flag1 = world.isAirBlock(pos.up()) || world.getBlockState(pos.up()).getBlock() == Blocks.SNOW_LAYER || world.getBlockState(pos.up()).getBlock() == Blocks.TALLGRASS;
        boolean flag2 = block == Blocks.GRASS || block == Blocks.STONE || block == Blocks.SAND || block == Blocks.DIRT;
        return flag1 & flag2;
    }
}