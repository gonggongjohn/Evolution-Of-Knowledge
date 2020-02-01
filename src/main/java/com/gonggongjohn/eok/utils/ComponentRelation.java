package com.gonggongjohn.eok.utils;

public class ComponentRelation {
    protected int x;
    protected int y;
    protected int z;
    protected String blockUnlocalizedName;

    public ComponentRelation(int x, int y, int z, String blockUnlocalizedName) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.blockUnlocalizedName = blockUnlocalizedName;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }

    public String getBlockUnlocalizedName() {
        return this.blockUnlocalizedName;
    }
}
