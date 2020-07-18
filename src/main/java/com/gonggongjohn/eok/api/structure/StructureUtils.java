package com.gonggongjohn.eok.api.structure;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.utils.ComponentRelation;
import com.gonggongjohn.eok.utils.JudgeWithFacing;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;


public class StructureUtils {
    private JudgeWithFacing checkStructure(World worldIn, BlockPos pos, IBlockState state, PropertyDirection property, StructureData data) {
        if (data != null) {
            EnumFacing facing = state.getValue(property).getOpposite();
            if (!facing.equals(EnumFacing.DOWN) && !facing.equals(EnumFacing.UP)) {
                boolean checkFlag = true;
                for (Vec3i index : data.getIndexList()) {
                    Vec3i transRela = EOK.mathUtils.coordRotation(index.getX(), index.getY(), index.getZ(), facing);
                    BlockPos transPos = new BlockPos(pos.getX() + transRela.getX(), pos.getY() + transRela.getY(), pos.getZ() + transRela.getZ());
                    if (!worldIn.getBlockState(transPos).getBlock().equals(data.query(index)))
                        checkFlag = false;
                }
                if (checkFlag) return new JudgeWithFacing(true, facing);
            }
        }
        return new JudgeWithFacing(false, null);
    }
}
