package com.gonggongjohn.eok.api.structure;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.Vec3i;

import java.util.ArrayList;
import java.util.HashMap;

public class PrimaryStructureData {
    private int layerNum;
    private ArrayList<Vec3i> indexList;
    private HashMap<Vec3i, Block> structure;


    public PrimaryStructureData(){
        this.layerNum = 0;
        this.indexList = new ArrayList<Vec3i>();
        this.structure = new HashMap<Vec3i, Block>();
    }

    public PrimaryStructureData(NBTTagCompound compound){
        this.layerNum = 0;
        this.indexList = new ArrayList<Vec3i>();
        this.structure = new HashMap<Vec3i, Block>();
        if(compound != null && compound.hasKey("blueprint.category") && compound.hasKey("blueprint.structure")){
            if(compound.getString("blueprint.category").equals("primary")) {
                NBTTagList list = (NBTTagList) compound.getTag("blueprint.structure");
                for (NBTBase subUnit : list) {
                    int layer = ((NBTTagCompound) subUnit).getInteger("layer");
                    int row = ((NBTTagCompound) subUnit).getInteger("row");
                    int column = ((NBTTagCompound) subUnit).getInteger("column");
                    String blockName = ((NBTTagCompound) subUnit).getString("block");
                    if (layer > layerNum) layerNum++;
                    Vec3i index = new Vec3i(layer, row, column);
                    this.indexList.add(index);
                    this.structure.put(index, Block.getBlockFromName(blockName));
                }
                indexList.sort(new VectorComparator());
            }
        }
    }

    public void set(int layer, int row, int column, Block block){
        if(layer > layerNum) this.layerNum++;
        Vec3i index = new Vec3i(layer, row, column);
        this.indexList.add(index);
        indexList.sort(new VectorComparator());
        this.structure.put(index, block);
    }

    public Block query(int layer, int row, int column){
        Vec3i index = new Vec3i(layer, row, column);
        return this.structure.get(index);
    }

    public Block query(Vec3i vector){
        return this.structure.get(vector);
    }

    public int getLayerNum() {
        return this.layerNum;
    }

    public ArrayList<Vec3i> getLayerIndexList(int layer){
        ArrayList<Vec3i> result = new ArrayList<Vec3i>();
        for(Vec3i vec : this.indexList){
            if(vec.getX() == layer) result.add(vec);
        }
        return result;
    }

    public ArrayList<Vec3i> getIndexList() {
        return this.indexList;
    }
    
    public NBTTagCompound toNBT(){
        NBTTagCompound compound = new NBTTagCompound();
        NBTTagList list = new NBTTagList();
        for (Vec3i index : this.indexList) {
            NBTTagCompound subUnit = new NBTTagCompound();
            subUnit.setInteger("layer", index.getX());
            subUnit.setInteger("row", index.getY());
            subUnit.setInteger("column", index.getZ());
            subUnit.setString("block", structure.get(index).getRegistryName().toString());
            list.appendTag(subUnit);
        }
        compound.setTag("blueprint.structure", list);
        compound.setString("blueprint.category", "primary");
        return compound;
    }
}
