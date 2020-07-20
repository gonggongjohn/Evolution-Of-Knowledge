package com.gonggongjohn.eok.api.structure;

import net.minecraft.util.math.Vec3i;

import java.util.Comparator;

public class VectorComparator implements Comparator<Vec3i> {
    @Override
    public int compare(Vec3i o1, Vec3i o2) {
        int x1 = o1.getX();
        int y1 = o1.getY();
        int z1 = o1.getZ();
        int x2 = o2.getX();
        int y2 = o2.getY();
        int z2 = o2.getZ();
        if(x1 == x2 && y1 == y2 && z1 == z2) return 0;
        if(x1 < x2) return -1;
        else if(x1 > x2) return 1;
        if(y1 < y2) return -1;
        else if(y1 > y2) return 1;
        if(z1 < z2) return -1;
        else return 1;
    }
}
