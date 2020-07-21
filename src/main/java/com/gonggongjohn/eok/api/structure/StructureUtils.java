package com.gonggongjohn.eok.api.structure;

import com.gonggongjohn.eok.EOK;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;


public class StructureUtils {
    public static boolean checkStructure(World worldIn, BlockPos pos, StructureData data) {
        if (data != null) {
            for (int i = 0; i < 4; i++) {
                boolean checkFlag = true;
                EnumFacing facing = EnumFacing.values()[2 + i];
                for (Vec3i index : data.getIndexList()) {
                    Vec3i transIndex = EOK.mathUtils.coordRotation(index.getY(), index.getX(), index.getZ(), facing);
                    BlockPos transPos = new BlockPos(pos.getX() + transIndex.getX(), pos.getY() + transIndex.getY(), pos.getZ() + transIndex.getZ());
                    if (!worldIn.getBlockState(transPos).getBlock().equals(data.query(index))) {
                        checkFlag = false;
                        break;
                    }
                }
                if (checkFlag) return true;
            }
        }
        return false;
    }
}
