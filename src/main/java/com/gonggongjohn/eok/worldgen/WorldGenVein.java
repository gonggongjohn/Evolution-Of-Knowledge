package com.gonggongjohn.eok.worldgen;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.registry.ForgeRegistries;


import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.HashMap;

public class WorldGenVein implements IWorldGenerator {

    public static int VEIN_TOTAL_SEED = 0;
    public final static int ORE_GENERATE_DENSITY_REFERENCE = 100;   //the reference density for ores.

    public static class Vein{
        public final String name;
        //the weight for generating the vein. (for vein!)
        public final int generateWeight;

        //the ores and their weights in a vein. (for ores!)
        //the weights can be reference by the const value ORE_GENERATE_DENSITY_REFERENCE
        public final Map<String, Integer> oreContained;
        public final int ID;

        public final int yMax, yMin;

        public Vein(String name,
                    int id,
                    int yMax,
                    int yMin,
                    int generateWeight,
                    Map<String, Integer> oreContained) {
            this.name = name;
            this.ID = id;
            this.generateWeight = generateWeight;
            this.oreContained = oreContained;
            this.yMax = yMax;
            this.yMin = yMin;
        }
    }
    public final ArrayList<Vein> VeinList = new ArrayList<>();
    public final HashMap<String, Integer> veinIDMap = new HashMap<>();
    public void addVeinList(String veinName,
                            int id,
                            int yMax,
                            int yMin,
                            int veinGenerateWeight,
                            Map<String, Integer> oreContained)
    {
        Vein vein = new Vein(veinName, id, yMax, yMin, veinGenerateWeight, oreContained);
        VeinList.add(vein);
        veinIDMap.put(veinName, id);
    }

    public WorldGenVein(){
        StringBuilder laststr= new StringBuilder();
        FileSystem json = null;
        try {
            URI sample = WorldGenVein.class.getResource("/assets/eok/assets.root").toURI();
            Path file;
            json = FileSystems.newFileSystem(sample, Collections.emptyMap());
            file = json.getPath("/assets/eok/database/vein.json");
            BufferedReader reader = Files.newBufferedReader(file);;
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                laststr.append(tempString);
            }
            String veinData = laststr.toString();
            JsonParser parser = new JsonParser();
            JsonObject veinObject = parser.parse(veinData).getAsJsonObject();
            JsonArray veinArray = veinObject.getAsJsonArray("vein");
            for(int i=0;i<veinArray.size();i++)
            {
                JsonObject vein = veinArray.get(i).getAsJsonObject();
                String vein_name = vein.get("name").getAsString();
                int vein_id = vein.get("id").getAsInt();
                int vein_ymax = vein.get("ymax").getAsInt();
                int vein_ymin = vein.get("ymin").getAsInt();
                int vein_generate_weight = vein.get("weight").getAsInt();
                VEIN_TOTAL_SEED = VEIN_TOTAL_SEED + vein_generate_weight;
                JsonArray oreContained = vein.get("ore_contained").getAsJsonArray();
                Map<String,Integer> oreContain = new HashMap<String, Integer>();
                for(int j=0;j<oreContained.size();j=j+1)
                {
                    JsonObject ore = oreContained.get(j).getAsJsonObject();
                    String oreName = ore.get("ore_name").getAsString();
                    int oreWeight = ore.get("weight").getAsInt();
                    oreContain.put(oreName,oreWeight);
                }
                this.addVeinList(vein_name,vein_id,vein_ymax,vein_ymin,vein_generate_weight,oreContain);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        //triple chunk generate method
        if(Math.abs(chunkX%3)==2&&Math.abs(chunkZ%3)==1) {
            //determine the vein that generates
            int generateSeed = random.nextInt(VEIN_TOTAL_SEED);
            Vein veinChosen = chooseVein(generateSeed);
            if (veinChosen != null) {
                int yCenter = random.nextInt(veinChosen.yMax-veinChosen.yMin)+ veinChosen.yMin;
                BlockPos centerPos = new BlockPos(chunkX * 16 + 8, yCenter, chunkZ * 16 + 8);
                //generate ore:

                ArrayList<BlockPos> orePosList = chooseOrePos(veinChosen, centerPos, random);
                for(BlockPos orePos : orePosList)
                {
                    int oreGenerateSeed = random.nextInt(ORE_GENERATE_DENSITY_REFERENCE);
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
        int oreWeight;
        if(world.getBlockState(pos).getBlock() ==Blocks.STONE) {
            for(String oreName : vein.oreContained.keySet()) {
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
        int xBound = 24;
        int zBound = 24;
        int yBound = random.nextInt(5)+5;
        double maxdistance = Math.sqrt(xBound*xBound+
                yBound*yBound+
                zBound*zBound);
        for(int y = centerPos.getY()-yBound;y< centerPos.getY()+yBound;y++)
            for(int x= centerPos.getX()-xBound;x< centerPos.getX()+xBound;x++)
                for(int z = centerPos.getZ()-zBound;z<centerPos.getZ()+zBound;z++)
                {
                    double distance = Math.sqrt((x-centerPos.getX())*(x-centerPos.getX())+
                            (y-centerPos.getY())*(y-centerPos.getY())+
                            (z-centerPos.getZ())*(z-centerPos.getZ()));
                    if(random.nextDouble()>(distance/maxdistance)&&y>vein.yMin&&y<vein.yMax)
                    {
                        BlockPos orePos = new BlockPos(x,y,z);
                        orePosList.add(orePos);
                    }
                }


        return orePosList;
    }
}
