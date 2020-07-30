package com.gonggongjohn.eok.inventory;

import net.minecraft.nbt.NBTTagCompound;

public interface IClientCBTHandler {
    void onComponentPoolUpdate(NBTTagCompound componentPool);
}
