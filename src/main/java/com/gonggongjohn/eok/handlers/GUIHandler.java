package com.gonggongjohn.eok.handlers;

import com.gonggongjohn.eok.containers.ContainerMainReservoir;
import com.gonggongjohn.eok.containers.ContainerOriginalForgeFurnace;
import com.gonggongjohn.eok.containers.ContainerResearchTableAncient;
import com.gonggongjohn.eok.gui.GUIMainReservoir;
import com.gonggongjohn.eok.gui.GUIOriginalForgeFurnace;
import com.gonggongjohn.eok.gui.GUIResearchTableAncient;
import com.gonggongjohn.eok.tileEntities.TEMainReservoir;
import com.gonggongjohn.eok.tileEntities.TEOriginalForgeFurnace;
import com.gonggongjohn.eok.tileEntities.TEResearchTableAncient;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);
		if (te != null) {
			switch (ID) {
			case 0:
				return new ContainerResearchTableAncient((TEResearchTableAncient) te, player);
			case 1:
				return new ContainerMainReservoir((TEMainReservoir) te, player);
			case 2:
				return new ContainerOriginalForgeFurnace((TEOriginalForgeFurnace) te, player);
			}
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);
		if (te != null) {
			switch (ID) {
			case 0:
				return new GUIResearchTableAncient((TEResearchTableAncient) te, player);
			case 1:
				return new GUIMainReservoir((TEMainReservoir) te, player);
			case 2:
				return new GUIOriginalForgeFurnace(
						new ContainerOriginalForgeFurnace((TEOriginalForgeFurnace) te, player));
			}
		}
		return null;
	}
}
