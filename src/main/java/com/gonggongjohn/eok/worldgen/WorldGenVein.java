package com.gonggongjohn.eok.worldgen;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockStateBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.*;
import java.util.HashMap;

public class WorldGenVein implements IWorldGenerator {

    public final static int VEIN_TOTAL_SEED = 2000;
    public final static int ORE_GENERATE_DENSITY_REFERENCE = 100;   //the reference density for ores.

    public static class Vein{
        public final String name;
        //the weight for generating the vein. (for vein!)
        public final int generateWeight;

        //the ores and their weights in a vein. (for ores!)
        //the weights can be reference by the const value ORE_GENERATE_DENSITY_REFERENCE
        public final Dictionary<String, Integer> oreContained;
        public final int ID;

        public final int yMax, yMin;
        public int yCenterDefault;

        public Vein(String name,
                    int id,
                    int yMax,
                    int yMin,
                    int generateWeight,
                    Dictionary<String, Integer> oreContained) {
            this.name = name;
            this.ID = id;
            this.generateWeight = generateWeight;
            this.oreContained = oreContained;
            this.yMax = yMax;
            this.yMin = yMin;
            this.yCenterDefault = (yMax + yMin)/2;
        }
        public void setYCenter(int yCenter)
        {
            this.yCenterDefault = yCenter;
        }
    }
    public final ArrayList<Vein> VeinList = new ArrayList<>();
    public final HashMap<String, Integer> veinIDMap = new HashMap<>();
    public void addVeinList(String veinName,
                            int id,
                            int yMax,
                            int yMin,
                            int veinGenerateWeight,
                            Dictionary<String, Integer> oreContained)
    {
        Vein vein = new Vein(veinName, id, yMax, yMin, veinGenerateWeight, oreContained);
        VeinList.add(vein);
        veinIDMap.put(veinName, id);
    }
    public void addVeinList(String veinName,
                            int id,
                            int yMax,
                            int yMin,
                            int veinGenerateWeight,
                            Dictionary<String, Integer> oreContained,
                            int yCenter)
    {
        Vein vein = new Vein(veinName, id, yMax, yMin, veinGenerateWeight, oreContained);
        vein.setYCenter(yCenter);
        VeinList.add(vein);
        veinIDMap.put(veinName, id);
    }

    public WorldGenVein()
    {
        //以后这里使用zijing的documentrender从json文件里读取所有矿脉数据组成一个list

    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        //triple chunk generate method
        if(chunkX%3==2&&chunkZ%3==1) {
            //the position of the center of a ore vein

            //determine the vein that generates
            int generateSeed = random.nextInt(VEIN_TOTAL_SEED);
            Vein veinChosen = chooseVein(generateSeed);
            if (veinChosen != null) {
                BlockPos centerPos = new BlockPos(chunkX * 16 + 8, veinChosen.yCenterDefault, chunkZ * 16 + 8);
                int oreGenerateSeed = random.nextInt(ORE_GENERATE_DENSITY_REFERENCE);
                //generate ore:

                ArrayList<BlockPos> orePosList = chooseOrePos(veinChosen, centerPos, random);
                for(int i=0;i<orePosList.size();i++)
                {
                    BlockPos orePos = orePosList.get(i);
                    generateOre(oreGenerateSeed, veinChosen, world, orePos);
                }

                //generate oreCore:
                /**
                 * the rule of naming orecore:
                 * registryName: "orecore+'name of the vein'"(cautious: name of vein, 意味着一个矿脉对应一种矿脉核心）
                 * */
                Block blockOreCore = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("orecore"+veinChosen.name));
                if(blockOreCore != null && world.getBlockState(centerPos).getBlock()== Blocks.STONE) {
                    world.setBlockState(centerPos, blockOreCore.getDefaultState());
                    world.setBlockState(centerPos.up(), blockOreCore.getDefaultState());
                    world.setBlockState(centerPos.down(), blockOreCore.getDefaultState());
                    world.setBlockState(centerPos.east(), blockOreCore.getDefaultState());
                    world.setBlockState(centerPos.west(), blockOreCore.getDefaultState());
                    world.setBlockState(centerPos.north(), blockOreCore.getDefaultState());
                    world.setBlockState(centerPos.south(), blockOreCore.getDefaultState());
                }
            }
        }
    }

    private Vein chooseVein(int generateSeed)
    {
        for(int i=0;i<VeinList.size();i++)
        {
            Vein tmpVein=VeinList.get(i);
            if(generateSeed>tmpVein.generateWeight)
                generateSeed = generateSeed - tmpVein.generateWeight;
            else
                return tmpVein;
        }
        return null;   //no vein being chosen.
    }
    private boolean generateOre(int seed, Vein vein, World world, BlockPos pos)
    {
        Enumeration<String> oreNameContained = vein.oreContained.keys();
        String oreName;
        int oreWeight;
        if(world.getBlockState(pos).getBlock() ==Blocks.STONE) {
            while (oreNameContained.hasMoreElements()) {
                oreName = oreNameContained.nextElement();
                oreWeight = vein.oreContained.get(oreName);
                Block oreGenerate = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(oreName));
                if(oreWeight>seed&&oreGenerate!=null)
                {
                    world.setBlockState(pos, oreGenerate.getDefaultState());
                    return true;   //generate an ore on current position
                }
                else seed = seed - oreWeight;
            }
        }
        return false;   //cannot generate any ores
    }

    private ArrayList<BlockPos> chooseOrePos(Vein vein, BlockPos centerPos, Random random)
    {
        ArrayList<BlockPos> orePosList = new ArrayList<>(200);

        return orePosList;
    }
}
