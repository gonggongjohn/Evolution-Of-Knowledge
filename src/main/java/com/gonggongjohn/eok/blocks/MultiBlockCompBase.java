package com.gonggongjohn.eok.blocks;

import com.gonggongjohn.eok.utils.IMultiBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class MultiBlockCompBase extends Block implements IMultiBlock {

    public MultiBlockCompBase(Material materialIn) {
        super(materialIn);
    }

    @Override
    public boolean checkStructure(int dimensionNum, String structureName) {
        //TODO
        return true;
    }

    public void createMultiBlock(){
        //TODO
    }
}
