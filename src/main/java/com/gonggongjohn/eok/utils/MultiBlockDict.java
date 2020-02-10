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
    /** Block's Unlocalized Name -> Structure Name */
    public HashMap<String, String> structureNameDict = new HashMap<String, String>();
    /** Structure Name -> Dimension Number */
    public HashMap<String, Integer> structureDimensionDict = new HashMap<String, Integer>();
    /** Structure Name -> Replacement Block Instance */
    public HashMap<String, Block> structureReplaceDict = new HashMap<String, Block>();

    public ArrayList<ComponentRelation> STRElementaryResearchTable;
    public ArrayList<ComponentRelation> STRTest2D;
    public ArrayList<ComponentRelation> STRTest3D;

    private Block stoneTable = BlockHandler.blockStoneTable;
    private Block stone = Blocks.STONE;

    private String SNElementaryResearchTable = "structure_elementary_research_table";
    private String SNTest2D = "structure_test_2d";
    private String SNTest3D = "structure_test_3d";

    public MultiBlockDict(){

    }

    public void initStructure(){
        STRElementaryResearchTable = createStructure(crwb(0, 0, 1, stoneTable));
        STRTest2D = createStructure(crwb(1, 0, 0, stone), crwb(-1, 0, 0, stone),
                crwb(0, 0, 1, stone), crwb(0, 0, -1, stone),
                crwb(1, 0, 1, stone), crwb(-1, 0, -1, stone),
                crwb(1, 0, -1, stone), crwb(-1, 0, 1, stone));
        STRTest3D = createStructure(crwb(1, 0, 0, stone), crwb(-1, 0, 0, stone),
                crwb(0, 0, 1, Blocks.AIR), crwb(1, 0, 1, stone),
                crwb(-1, 0, 1, stone), crwb(0, 0, 2, stone),
                crwb(1, 0, 2, stone), crwb(-1, 0, 2, stone),
                crwb(1, -1, 0, stone), crwb(-1, -1, 0, stone),
                crwb(0, -1, 1, stone), crwb(1, -1, 1, stone),
                crwb(-1, -1, 1, stone), crwb(0, -1, 2, stone),
                crwb(1, -1, 2, stone), crwb(-1, -1, 2, stone),
                crwb(0, -1, 0, stone), crwb(0, 1, 0, stone),
                crwb(1, 1, 0, stone), crwb(-1, 1, 0, stone),
                crwb(0, 1, 1, stone), crwb(1, 1, 1, stone),
                crwb(-1, 1, 1, stone), crwb(0, 1, 2, stone),
                crwb(1, 1, 2, stone), crwb(-1, 1, 2, stone));
    }


    public void initDict(){
        structureNameDict.put(stoneTable.getUnlocalizedName(), SNElementaryResearchTable);
        structureNameDict.put(BlockHandler.blockTest2DCore.getUnlocalizedName(), SNTest2D);
        structureNameDict.put(BlockHandler.blockTest3DCore.getUnlocalizedName(), SNTest3D);

        structureDimensionDict.put(SNElementaryResearchTable, 1);
        structureDimensionDict.put(SNTest2D, 2);
        structureDimensionDict.put(SNTest3D, 3);

        structureDictLinear.put(SNElementaryResearchTable, STRElementaryResearchTable);
        structureDict2D.put(SNTest2D, STRTest2D);
        structureDict3D.put(SNTest3D, STRTest3D);

        structureReplaceDict.put(SNElementaryResearchTable, BlockHandler.blockElementaryResearchTable);
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
