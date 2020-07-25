package com.gonggongjohn.eok.containers;

import com.gonggongjohn.eok.tileEntities.TEResearchTableAncient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerResearchTableAncient extends Container {
    private TEResearchTableAncient te;

    public ContainerResearchTableAncient(TEResearchTableAncient te, EntityPlayer player) {
        this.te = te;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return te.isUseableByPlayer(player);
    }
}
