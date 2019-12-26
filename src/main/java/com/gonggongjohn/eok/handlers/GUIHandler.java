package com.gonggongjohn.eok.handlers;

import com.gonggongjohn.eok.client.gui.GUIElementaryResearchTable;
import com.gonggongjohn.eok.client.gui.GUIRefractingTelescope;
import com.gonggongjohn.eok.client.gui.GUIMerchant;
import com.gonggongjohn.eok.inventory.ContainerElementaryResearchTable;
import com.gonggongjohn.eok.inventory.ContainerMerchant;
import com.gonggongjohn.eok.inventory.ContainerRefractingTelescope;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GUIHandler implements IGuiHandler {
    public static final int GUIRefractingTelescope = 1;
    public static final int GUIElementaryResearchTable = 2;
    public static final int GUIMerchant = 3;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID){
            case GUIRefractingTelescope:
                return new ContainerRefractingTelescope(player);
            case GUIElementaryResearchTable:
                return new ContainerElementaryResearchTable(player, world.getTileEntity(new BlockPos(x, y, z)));
            case GUIMerchant:
            	return new ContainerMerchant(player);
            default:
                return null;

        }
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID){
            case GUIRefractingTelescope:
                return new GUIRefractingTelescope(new ContainerRefractingTelescope(player));
            case GUIElementaryResearchTable:
                return new GUIElementaryResearchTable(new ContainerElementaryResearchTable(player, world.getTileEntity(new BlockPos(x, y, z))));
            case GUIMerchant:
            	return new GUIMerchant(new ContainerMerchant(player));
            default:
                return null;
        }
    }
}
