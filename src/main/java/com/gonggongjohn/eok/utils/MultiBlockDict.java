package com.gonggongjohn.eok.utils;

import com.gonggongjohn.eok.handlers.BlockHandler;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MultiBlockDict {
    public HashMap<String, ArrayList<ComponentRelation>> structureDictLinear = new HashMap<String, ArrayList<ComponentRelation>>();
    public HashMap<String, ArrayList<ComponentRelation>> structureDict2D = new HashMap<String, ArrayList<ComponentRelation>>();
    public HashMap<String, ArrayList<ComponentRelation>> structureDict3D = new HashMap<String, ArrayList<ComponentRelation>>();

    public ArrayList<ComponentRelation> STRElementaryResearchTable;
    public ArrayList<ComponentRelation> STRTest2D;
    public ArrayList<ComponentRelation> STRTest3D;

    private Block stoneTable = BlockHandler.blockStoneTable;

    public MultiBlockDict(){

    }

    public void initStructure(){
        STRElementaryResearchTable = createStructure(crwb(1, 0, 0, stoneTable));
        STRTest2D = createStructure(crwb(1, 0, 0, stoneTable), crwb(-1, 0, 0, stoneTable),
                crwb(0, 0, 1, stoneTable), crwb(0, 0, -1, stoneTable),
                crwb(1, 0, 1, stoneTable), crwb(-1, 0, -1, stoneTable),
                crwb(1, 0, -1, stoneTable), crwb(-1, 0, 1, stoneTable));
        STRTest3D = createStructure(crwb(1, 0, 0, stoneTable), crwb(-1, 0, 0, stoneTable),
                crwb(0, 0, 1, Blocks.AIR), crwb(1, 0, 1, stoneTable),
                crwb(-1, 0, 1, stoneTable), crwb(0, 0, 2, stoneTable),
                crwb(1, 0, 2, stoneTable), crwb(-1, 0, 2, stoneTable));
    }


    public void initDict(){
        structureDictLinear.put("structure_elementary_research_table", STRElementaryResearchTable);
        structureDict2D.put("structure_test_2d", STRTest2D);
        structureDict3D.put("structure_test_3d", STRTest3D);
    }

    private ArrayList<ComponentRelation> createStructure(ComponentRelation... relations){
        ArrayList<ComponentRelation> list = new ArrayList<ComponentRelation>(Arrays.asList(relations));
        return list;
    }

    private ComponentRelation crwb(int x, int y, int z, Block block){
        return new ComponentRelation(x, y, z, block.getUnlocalizedName());
    }


    /*private String[][] createStructure2D(Block[][] origin){
        String[][] temp = new String[origin.length][origin[0].length];
        for(int i = 0; i < origin.length; i++){
            for(int j = 0; j < origin[0].length; j++){
                temp[i][j] = origin[i][j].getUnlocalizedName();
            }
        }
        return temp;
    }


    private String[][][] createStructure3D(Block[][][] origin){
        String[][][] temp = new String[origin.length][origin[0].length][origin[0][0].length];
        for(int i = 0; i < origin.length; i++){
            for(int j = 0; j < origin[0].length; j++){
                for(int k = 0; k < origin[0][0].length; k++){
                    temp[i][j][k] = origin[i][j][k].getUnlocalizedName();
                }
            }
        }
        return temp;
    }*/
}
