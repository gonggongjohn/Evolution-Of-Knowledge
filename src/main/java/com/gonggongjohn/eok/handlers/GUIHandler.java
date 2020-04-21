package com.gonggongjohn.eok.handlers;

import com.gonggongjohn.eok.client.gui.GUIBluePrint;
import com.gonggongjohn.eok.client.gui.GUIEOKManualOld;
import com.gonggongjohn.eok.client.gui.GUIElementaryResearchTable;
import com.gonggongjohn.eok.client.gui.GUIFirstMachine;
import com.gonggongjohn.eok.client.gui.GUIHayTorchBase;
import com.gonggongjohn.eok.client.gui.GUIHayTorchBaseLit;
import com.gonggongjohn.eok.client.gui.GUIMerchant;
import com.gonggongjohn.eok.client.gui.GUIRefractingTelescope;
import com.gonggongjohn.eok.client.gui.GUITest2D;
import com.gonggongjohn.eok.inventory.ContainerElementaryResearchTable;
import com.gonggongjohn.eok.inventory.ContainerFirstMachine;
import com.gonggongjohn.eok.inventory.ContainerHayTorchBase;
import com.gonggongjohn.eok.inventory.ContainerHayTorchBaseLit;
import com.gonggongjohn.eok.inventory.ContainerMerchant;
import com.gonggongjohn.eok.inventory.ContainerRefractingTelescope;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GUIHandler implements IGuiHandler {

	public static final int GUIEOKManual = 0;
    public static final int GUIRefractingTelescope = 1;
    public static final int GUIElementaryResearchTable = 2;
	public static final int GUIMerchant = 3;
	public static final int GUIContainerTest = 4;
    public static final int GUIFirstMachine = 5;
    public static final int GUIHayTorchBase = 6;
    public static final int GUIHayTorchBaseLit = 7;
    public static final int GUIBluePrint = 8;
    public static final int GUITest2D = 9;

    @Override
    public Object getServerGuiElement(final int ID, final EntityPlayer player, final World world, final int x, final int y, final int z) {
    	
        switch (ID) {
        	case GUIEOKManual:
        		return new GUIEOKManualOld();
            case GUIRefractingTelescope:
                return new ContainerRefractingTelescope(player);
            case GUIElementaryResearchTable:
                return new ContainerElementaryResearchTable(player, world.getTileEntity(new BlockPos(x, y, z)));
            case GUIMerchant:
            	return new ContainerMerchant(player);
            case GUIFirstMachine:
                return new ContainerFirstMachine(player);
            case GUIHayTorchBase:
                return new ContainerHayTorchBase(player, world.getTileEntity(new BlockPos(x, y, z))); 
            case GUIHayTorchBaseLit:
                return new ContainerHayTorchBaseLit(player, world.getTileEntity(new BlockPos(x, y, z)));
            case GUIBluePrint:
                return new GUIBluePrint();
            case GUITest2D:
                return new GUITest2D();
            default:
				return null;
		}
	}

    @Override
    public Object getClientGuiElement(final int ID, final EntityPlayer player, final World world, final int x, final int y, final int z) {
    	
        switch (ID){
        
        	case GUIEOKManual:
        		return new GUIEOKManualOld();
            case GUIRefractingTelescope:
                return new GUIRefractingTelescope(new ContainerRefractingTelescope(player));
            case GUIElementaryResearchTable:
                return new GUIElementaryResearchTable(new ContainerElementaryResearchTable(player, world.getTileEntity(new BlockPos(x, y, z))));
            case GUIMerchant:
            	return new GUIMerchant(new ContainerMerchant(player));
            case GUIFirstMachine:
                return new GUIFirstMachine(new ContainerFirstMachine(player));
            case GUIHayTorchBase:
                return new GUIHayTorchBase(new ContainerHayTorchBase(player, world.getTileEntity(new BlockPos(x, y, z)))); 
            case GUIHayTorchBaseLit:
                return new GUIHayTorchBaseLit(new ContainerHayTorchBaseLit(player, world.getTileEntity(new BlockPos(x, y, z))));
            case GUIBluePrint:
                return new GUIBluePrint();
            case GUITest2D:
                return new GUITest2D();
            default:
                return null;
        }
    }
}
