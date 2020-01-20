package com.gonggongjohn.eok.utils;

import com.gonggongjohn.eok.handlers.BlockHandler;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

import java.util.HashMap;

public class MultiBlockDict {
    public HashMap<String, String[]> structureDictLinear = new HashMap<String, String[]>();
    public HashMap<String, String[][]> structureDict2D = new HashMap<String, String[][]>();
    public HashMap<String, String[][][]> structureDict3D = new HashMap<String, String[][][]>();

    public String[] STRElementaryResearchTable;
    public String[][] STRTest2D;
    public String[][][] STRTest3D;

    private Block stoneTable = BlockHandler.blockStoneTable;

    public MultiBlockDict(){

    }

    public void initStructure(){
        STRElementaryResearchTable = createStructureLinear(new Block[]{stoneTable, stoneTable});
        STRTest2D = createStructure2D(new Block[][]{{stoneTable, stoneTable, stoneTable},
                {stoneTable, BlockHandler.blockTest2DCore, stoneTable},
                {stoneTable, stoneTable, stoneTable}});
        STRTest3D = createStructure3D(new Block[][][]{
                {{stoneTable, stoneTable, stoneTable},
                        {stoneTable, stoneTable, stoneTable},
                        {stoneTable, stoneTable, stoneTable}},
                {{stoneTable, BlockHandler.blockTest3DCore, stoneTable},
                        {stoneTable, Blocks.AIR, stoneTable},
                        {stoneTable, stoneTable, stoneTable}},
                {{stoneTable, stoneTable, stoneTable},
                        {stoneTable, stoneTable, stoneTable},
                        {stoneTable, stoneTable, stoneTable}}
        });
    }


    public void initDict(){
        structureDictLinear.put("structure_elementary_research_table", STRElementaryResearchTable);
        structureDict2D.put("structure_test_2d", STRTest2D);
        structureDict3D.put("structure_test_3d", STRTest3D);
    }

    private String[] createStructureLinear(Block[] origin){
        String[] temp = new String[origin.length];
        for(int i = 0; i < origin.length; i++){
            temp[i] = origin[i].getUnlocalizedName();
        }
        return temp;
    }

    private String[][] createStructure2D(Block[][] origin){
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
    }
}
