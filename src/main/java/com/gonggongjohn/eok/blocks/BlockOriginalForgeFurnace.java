package com.gonggongjohn.eok.blocks;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.tileEntities.TEOriginalForgeFurnace;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockOriginalForgeFurnace extends BlockContainer {
	public BlockOriginalForgeFurnace() {
		super(Material.rock);
		this.setBlockName("originalForgeFurnace");
		this.setHardness(2.0F);
		this.setHarvestLevel("pickaxe", 1);
		this.setCreativeTab(EOK.tabEOK);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float lx, float ly,
			float lz) {
		if (world.isRemote) {
			return true;
		}
		player.openGui(EOK.instance, 2, world, x, y, z);
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TEOriginalForgeFurnace();
	}
}