package com.gonggongjohn.eok.worldgen;

import com.gonggongjohn.eok.handlers.BlockHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class WorldGenRedstoneAppleTree implements IWorldGenerator {

    private static final IBlockState LOG = Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.OAK);
    private static final IBlockState LEAF = Blocks.LEAVES.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.OAK).withProperty(BlockOldLeaf.CHECK_DECAY, Boolean.FALSE);

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
                         IChunkProvider chunkProvider) {
        for (int i = 0; i < 3; i++) {

            BlockPos pos = new BlockPos(chunkX * 16 + 8 + random.nextInt(15), 0, chunkZ * 16 + 8 + random.nextInt(15));
            pos = world.getTopSolidOrLiquidBlock(pos).down();

            if (this.canSustainSt(world, pos)) {

                for (int i2 = pos.getY() - 3 + i; i2 <= pos.getY() + i; ++i2) {

                    int k2 = i2 - (pos.getY() + i);
                    int l2 = 1 - k2 / 2;

                    for (int i3 = pos.getX() - l2; i3 <= pos.getX() + l2; ++i3) {

                        for (int k1 = pos.getZ() - l2; k1 <= pos.getZ() + l2; ++k1) {

                            if (i2 + 5 > pos.getY() + 3 && i2 + 5 < pos.getY() + 5) {

                                BlockPos fruitPos = new BlockPos(i3, i2 + 5, k1);

                                if (random.nextInt(5) == 1) {

                                    world.setBlockState(fruitPos, BlockHandler.blockApple.getDefaultState());
                                }
                            }
                        }
                    }
                }

                for (int i2 = pos.getY() - 3 + i; i2 <= pos.getY() + i; ++i2) {
                    int k2 = i2 - (pos.getY() + i);
                    int l2 = 1 - k2 / 2;

                    for (int i3 = pos.getX() - l2; i3 <= pos.getX() + l2; ++i3) {

                        for (int k1 = pos.getZ() - l2; k1 <= pos.getZ() + l2; ++k1) {
                            BlockPos blockpos = new BlockPos(i3, i2 + 6, k1);

                            world.setBlockState(blockpos, LEAF);
                        }
                    }
                }

                for (int j = 0; j < 6; j++) {

                    world.setBlockState(pos.up(j), LOG);
                }
            }
        }
    }

    private boolean canSustainSt(World world, BlockPos pos) {
        Block block = world.getBlockState(pos).getBlock();
        boolean flag1 = world.isAirBlock(pos.up()) || world.getBlockState(pos.up()).getBlock() == Blocks.SNOW_LAYER;
        boolean flag2 = block == Blocks.GRASS;
        return flag1 & flag2;
    }
}