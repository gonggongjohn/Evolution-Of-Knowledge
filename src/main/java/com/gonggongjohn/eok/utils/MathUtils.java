package com.gonggongjohn.eok.utils;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3i;

import javax.vecmath.Matrix3d;

public class MathUtils {
    private Matrix3d transEast = new Matrix3d(0, 0, -1, 0, 1, 0, 1, 0, 0);
    private Matrix3d transNorth = new Matrix3d(-1, 0, 0, 0, 1, 0, 0, 0, -1);
    private Matrix3d transWest = new Matrix3d(0, 0, 1, 0, 1, 0, -1, 0, 0);

    public MathUtils() {
    }

    public Vec3i coordRotation(int x, int y, int z, EnumFacing facing){
        Matrix3d matOrigin = new Matrix3d(x, y, z, 0, 0, 0, 0, 0, 0);
        Matrix3d matResult = new Matrix3d();
        switch (facing){
            case SOUTH: matResult = matOrigin; break;
            case NORTH: matResult.mul(matOrigin, transNorth);break;
            case EAST: matResult.mul(matOrigin, transEast);break;
            case WEST: matResult.mul(matOrigin, transWest);break;
            default: return new Vec3i(0, 0, 0);
        }
        return new Vec3i(matResult.m00, matResult.m01, matResult.m02);
    }
}
