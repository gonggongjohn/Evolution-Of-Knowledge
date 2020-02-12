package com.gonggongjohn.eok.utils;

public enum EnumTool {
    PICKAXE(0, "pickaxe"),
    AXE(1, "axe"),
    SWORD(2, "sword"),
    SHOVEL(3, "shovel");

    private final int meta;
    private final String name;

    private EnumTool(int meta, String name) {
        this.meta = meta;
        this.name = name;
    }

    public int getMeta(){
        return this.meta;
    }

    public String getName() {
        return this.name;
    }
}
