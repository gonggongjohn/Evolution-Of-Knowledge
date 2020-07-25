package com.gonggongjohn.eok.containers;

import com.gonggongjohn.eok.tileEntities.TEMainReservoir;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerMainReservoir extends Container{
    private TEMainReservoir te;

    public ContainerMainReservoir(TEMainReservoir te, EntityPlayer player) {
        this.te = te;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return te.isUseableByPlayer(player);
    }
}
