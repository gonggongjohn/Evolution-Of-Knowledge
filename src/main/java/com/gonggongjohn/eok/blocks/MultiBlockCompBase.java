package com.gonggongjohn.eok.blocks;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.utils.ComponentRelation;
import com.gonggongjohn.eok.utils.IMultiBlock;
import com.gonggongjohn.eok.utils.JudgeWithFacing;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

import java.util.ArrayList;

public class MultiBlockCompBase extends Block implements IMultiBlock {
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    public MultiBlockCompBase(Material materialIn) {
        super(materialIn);
    }

    @Override
    public JudgeWithFacing checkStructure(World worldIn, BlockPos pos, IBlockState state, int dimensionNum, String structureName) {
        if (dimensionNum == 1) return checkLinear(worldIn, pos, state, structureName);
        else if (dimensionNum == 2) return check2D(worldIn, pos, state, structureName);
        else if (dimensionNum == 3) return check3D(worldIn, pos, state, structureName);
        return new JudgeWithFacing(false, null);
    }

    public void createMultiBlock(World worldIn, BlockPos pos, String structureName, EnumFacing facing) {
        Block replaceBlock = EOK.multiBlockDict.structureReplaceDict.get(structureName);
        if (replaceBlock != null) {
            ArrayList<ComponentRelation> relations = EOK.multiBlockDict.structureDictLinear.get(structureName);
            for (ComponentRelation relation : relations) {
                Vec3i transRela = EOK.mathUtils.coordRotation(relation.getX(), relation.getY(), relation.getZ(), facing);
                BlockPos transPos2 = new BlockPos(pos.getX() + transRela.getX(), pos.getY() + transRela.getY(), pos.getZ() + transRela.getZ());
                worldIn.setBlockToAir(transPos2);
            }
            worldIn.setBlockState(pos, replaceBlock.getDefaultState().withProperty(FACING, facing.getOpposite()));
            EOK.getLogger().info("structure complete");
        }
    }

    private JudgeWithFacing checkLinear(World worldIn, BlockPos pos, IBlockState state, String structureName) {
        ArrayList<ComponentRelation> relations = EOK.multiBlockDict.structureDictLinear.get(structureName);
        if (relations != null) {
            for (EnumFacing facing : new EnumFacing[]{EnumFacing.EAST, EnumFacing.WEST, EnumFacing.NORTH, EnumFacing.SOUTH}) {
                int tempX = pos.getX() + facing.getDirectionVec().getX();
                int tempY = pos.getY() + facing.getDirectionVec().getY();
                int tempZ = pos.getZ() + facing.getDirectionVec().getZ();
                BlockPos transPos1 = new BlockPos(tempX, tempY, tempZ);
                if (worldIn.getBlockState(transPos1).getBlock().getUnlocalizedName().equals(relations.get(0).getBlockUnlocalizedName())) {
                    boolean checkFlag = true;
                    for (int i = 1; i < relations.size(); i++) {
                        Vec3i transRela = EOK.mathUtils.coordRotation(relations.get(i).getX(), relations.get(i).getY(), relations.get(i).getZ(), facing);
                        BlockPos transPos2 = new BlockPos(pos.getX() + transRela.getX(), pos.getY() + transRela.getY(), pos.getZ() + transRela.getZ());
                        if (!worldIn.getBlockState(transPos2).getBlock().getUnlocalizedName().equals(relations.get(i).getBlockUnlocalizedName()))
                            checkFlag = false;
                    }
                    if (checkFlag) return new JudgeWithFacing(true, facing);
                }
            }
        }
        return new JudgeWithFacing(false, null);
    }

    private JudgeWithFacing check2D(World worldIn, BlockPos pos, IBlockState state, String structureName) {
        ArrayList<ComponentRelation> relations = EOK.multiBlockDict.structureDict2D.get(structureName);
        if (relations != null) {
            EnumFacing facing = state.getValue(FACING).getOpposite();
            if (!facing.equals(EnumFacing.DOWN) && !facing.equals(EnumFacing.UP)) {
                boolean checkFlag = true;
                for (ComponentRelation relation : relations) {
                    Vec3i transRela = EOK.mathUtils.coordRotation(relation.getX(), relation.getY(), relation.getZ(), facing);
                    BlockPos transPos = new BlockPos(pos.getX() + transRela.getX(), pos.getY() + transRela.getY(), pos.getZ() + transRela.getZ());
                    if (!worldIn.getBlockState(transPos).getBlock().getUnlocalizedName().equals(relation.getBlockUnlocalizedName()))
                        checkFlag = false;
                }
                if (checkFlag) return new JudgeWithFacing(true, facing);
            }
        }
        return new JudgeWithFacing(false, null);
    }

    private JudgeWithFacing check3D(World worldIn, BlockPos pos, IBlockState state, String structureName) {
        ArrayList<ComponentRelation> relations = EOK.multiBlockDict.structureDict3D.get(structureName);
        if (relations != null) {
            EnumFacing facing = state.getValue(FACING).getOpposite();
            if (!facing.equals(EnumFacing.DOWN) && !facing.equals(EnumFacing.UP)) {
                boolean checkFlag = true;
                for (ComponentRelation relation : relations) {
                    Vec3i transRela = EOK.mathUtils.coordRotation(relation.getX(), relation.getY(), relation.getZ(), facing);
                    BlockPos transPos = new BlockPos(pos.getX() + transRela.getX(), pos.getY() + transRela.getY(), pos.getZ() + transRela.getZ());
                    if (!worldIn.getBlockState(transPos).getBlock().getUnlocalizedName().equals(relation.getBlockUnlocalizedName()))
                        checkFlag = false;
                }
                if (checkFlag) return new JudgeWithFacing(true, facing);
            }
        }
        return new JudgeWithFacing(false, null);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing facing = EnumFacing.getHorizontal(meta & 3);
        return this.getDefaultState().withProperty(FACING, facing);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int facing = state.getValue(FACING).getHorizontalIndex();
        return facing;
    }

}
