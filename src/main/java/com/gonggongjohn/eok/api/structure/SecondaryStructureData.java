package com.gonggongjohn.eok.api.structure;

import net.minecraft.item.Item;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.Vec3i;

import java.util.ArrayList;
import java.util.HashMap;

public class SecondaryStructureData {
    private int layerNum;
    private ArrayList<Vec3i> indexList;
    private HashMap<Vec3i, Item> structure;

    public SecondaryStructureData(){
        this.layerNum = 0;
        this.indexList = new ArrayList<Vec3i>();
        this.structure = new HashMap<Vec3i, Item>();
    }

    public SecondaryStructureData(NBTTagCompound compound){
        this.layerNum = 0;
        this.indexList = new ArrayList<Vec3i>();
        this.structure = new HashMap<Vec3i, Item>();
        if(compound != null && compound.hasKey("blueprint.category") && compound.hasKey("blueprint.structure")) {
            if (compound.getString("blueprint.category").equals("secondary")) {
                NBTTagList list = (NBTTagList) compound.getTag("blueprint.structure");
                for (NBTBase subUnit : list) {
                    int layer = ((NBTTagCompound) subUnit).getInteger("layer");
                    int row = ((NBTTagCompound) subUnit).getInteger("row");
                    int column = ((NBTTagCompound) subUnit).getInteger("column");
                    String itemName = ((NBTTagCompound) subUnit).getString("item");
                    if (layer > layerNum) layerNum++;
                    Vec3i index = new Vec3i(layer, row, column);
                    this.indexList.add(index);
                    this.structure.put(index, Item.getByNameOrId(itemName));
                }
                indexList.sort(new VectorComparator());
            }
        }
    }

    public void set(int layer, int row, int column, Item item){
        if(layer > layerNum) this.layerNum++;
        Vec3i index = new Vec3i(layer, row, column);
        if (!structure.containsKey(index)) {
            this.indexList.add(index);
            indexList.sort(new VectorComparator());
        }
        structure.put(index, item);
    }

    public Item query(int layer, int row, int column){
        Vec3i index = new Vec3i(layer, row, column);
        return this.structure.get(index);
    }

    public Item query(Vec3i vector){
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
            subUnit.setString("item", structure.get(index).getRegistryName().toString());
            list.appendTag(subUnit);
        }
        compound.setTag("blueprint.structure", list);
        compound.setString("blueprint.category", "secondary");
        return compound;
    }
}
