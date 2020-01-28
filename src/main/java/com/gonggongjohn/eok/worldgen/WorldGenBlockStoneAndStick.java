package com.gonggongjohn.eok.worldgen;

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
        for(int i=0;i<6;i++) {
            BlockPos randomStonePos = new BlockPos(chunkX * 16 + 8 + random.nextInt(15), 0, chunkZ * 16 + 8 + random.nextInt(15));
            BlockPos randomStickPos = new BlockPos(chunkX*16+8+random.nextInt(15),0,chunkZ*16+8+random.nextInt(15));
            randomStonePos=world.getTopSolidOrLiquidBlock(randomStonePos).down();
            randomStickPos=world.getTopSolidOrLiquidBlock(randomStickPos).down();
            if(this.canSustainSt(world,randomStonePos))
                world.setBlockState(randomStonePos.up(), BlockHandler.blockStoneRock.getDefaultState());
            if(this.canSustainSt(world,randomStickPos))
                world.setBlockState(randomStickPos.up(),BlockHandler.blockStick.getDefaultState());
        }
    }
    private boolean canSustainSt(World world,BlockPos pos)
    {
        boolean flag1=world.isAirBlock(pos.up())||world.getBlockState(pos).getBlock()==Blocks.SNOW_LAYER;
        Block block=world.getBlockState(pos).getBlock();
        boolean flag2=block==Blocks.GRASS||block==Blocks.STONE||block==Blocks.SAND;
        return flag1&flag2;
    }
}
