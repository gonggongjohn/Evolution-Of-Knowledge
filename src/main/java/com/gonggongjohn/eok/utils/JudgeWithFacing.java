package com.gonggongjohn.eok.utils;

import net.minecraft.util.EnumFacing;

import javax.annotation.Nullable;

public class JudgeWithFacing {
    protected final boolean judge;
    protected final EnumFacing facing;

    public JudgeWithFacing(boolean judge, @Nullable EnumFacing facing) {
        this.judge = judge;
        this.facing = facing;
    }

    public boolean isComplete() {
        return this.judge;
    }

    public EnumFacing getFacing() {
        return this.facing;
    }
}
