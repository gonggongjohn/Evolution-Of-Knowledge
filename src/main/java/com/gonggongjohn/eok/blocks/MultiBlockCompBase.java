package com.gonggongjohn.eok.blocks;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.utils.IMultiBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MultiBlockCompBase extends Block implements IMultiBlock {
    private int[][] transformMatrix2D = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    private int[][] transformMatrix3D = {{1, 0, 0}, {-1, 0, 0}, {0, 1, 0}, {0, -1, 0}, {0, 0, 1}, {0, 0, -1}};

    public MultiBlockCompBase(Material materialIn) {
        super(materialIn);
    }

    @Override
    public int[] checkStructure(World worldIn, BlockPos pos, IBlockState state, int dimensionNum, String structureName) {
        if(dimensionNum == 1) return checkLinear(worldIn, pos, state, structureName);
        else if(dimensionNum == 2) return check2D(worldIn, pos, state, structureName);
        else if(dimensionNum == 3) return check3D(worldIn, pos, state, structureName);
        return new int[]{0, 0, 0, 0};
    }

    public void createMultiBlock(){
        EOK.getLogger().info("structure complete");
    }

    private int[] checkLinear(World worldIn, BlockPos pos, IBlockState state, String structureName){
        String[] structure = EOK.multiBlockDict.structureDictLinear.get(structureName);
        if(structure == null) return new int[]{0, 0, 0, 0};
        int arrayCur, facingCur = 0;
        //1是数组头，2是数组尾
        if(state.getBlock().getUnlocalizedName().equals(structure[0])) arrayCur = 1;
        else arrayCur = 2;
        int posTransX = 0, posTransZ = 0;
        int posY = pos.getY();
        boolean isBesideMatch = false;
        for(int i = 0; i < 4; i++){
            posTransX = pos.getX() + transformMatrix2D[i][0];
            posTransZ = pos.getZ() + transformMatrix2D[i][1];
            if(arrayCur == 1 && worldIn.getBlockState(new BlockPos(posTransX, posY, posTransZ)).getBlock().getUnlocalizedName().equals(structure[1])){
                facingCur = i;
                isBesideMatch = true;
                break;
            }
            else if(arrayCur == 2 && worldIn.getBlockState(new BlockPos(posTransX, posY, posTransZ)).getBlock().getUnlocalizedName().equals(structure[structure.length - 2])){
                facingCur = i;
                isBesideMatch = true;
                break;
            }
        }
        if (isBesideMatch){
            for(int i = 2; i < structure.length - 1; i++){
                posTransX = posTransX + transformMatrix2D[facingCur][0];
                posTransZ = posTransZ + transformMatrix2D[facingCur][1];
                if (arrayCur == 1 && !(worldIn.getBlockState(new BlockPos(posTransX, posY, posTransZ)).getBlock().getUnlocalizedName() == structure[i])) return new int[]{0, 0, 0, 0};
                else if (arrayCur == 2 && !(worldIn.getBlockState(new BlockPos(posTransX, posY, posTransZ)).getBlock().getUnlocalizedName() == structure[structure.length - 1 - i])) return new int[]{0, 0, 0, 0};
            }
            return new int[]{1, transformMatrix2D[facingCur][0], 0, transformMatrix2D[facingCur][1]};
        }
        return new int[]{0, 0, 0, 0};
    }

    private int[] check2D(World worldIn, BlockPos pos, IBlockState state, String structureName){
        String[][] structure = EOK.multiBlockDict.structureDict2D.get(structureName);
        if(structure == null) return new int[]{0, 0, 0, 0};
        //TODO
        return new int[]{0, 0, 0, 0};
    }

    private int[] check3D(World worldIn, BlockPos pos, IBlockState state, String structureName){
        String[][][] structure = EOK.multiBlockDict.structureDict3D.get(structureName);
        if(structure == null) return new int[]{0, 0, 0, 0};
        //TODO
        return new int[]{0, 0, 0, 0};
    }

}
