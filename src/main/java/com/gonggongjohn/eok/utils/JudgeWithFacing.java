package com.gonggongjohn.eok.utils;

import net.minecraft.util.EnumFacing;

import javax.annotation.Nullable;

public class JudgeWithFacing {
    protected boolean judge;
    protected EnumFacing facing;

    public JudgeWithFacing(boolean judge, @Nullable EnumFacing facing) {
        this.judge = judge;
        this.facing = facing;
    }

    public boolean isTrue() {
        return this.judge;
    }

    public EnumFacing getFacing() {
        return this.facing;
    }
}
