package com.gonggongjohn.eok.utils;

import com.gonggongjohn.eok.handlers.BlockHandler;
import net.minecraft.block.Block;

import java.util.HashMap;

public class MultiBlockDict {
    public HashMap<String, String[][]> structureDict2D = new HashMap<String, String[][]>();
    public HashMap<String, String[][][]> structureDict3D = new HashMap<String, String[][][]>();

    public String[][] STRElementaryResearchTable;

    public MultiBlockDict(){

    }

    public void initStructure(){
        STRElementaryResearchTable = createStructure2D(new Block[][]{{BlockHandler.blockStone},{BlockHandler.blockStone}});
    }


    public void initDict(){
        structureDict2D.put("structrue_elementary_research_table", STRElementaryResearchTable);
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
