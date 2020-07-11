package com.gonggongjohn.eok.inventory;

import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;

public interface ICBTHandler {
    void onWriteActive(NBTTagCompound compound);
}
