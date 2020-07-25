package com.gonggongjohn.eok.blocks;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.tileEntities.TEMainReservoir;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Random;

public class BlockMainReservoir extends BlockContainer {
	private final Random rand = new Random();
	private int[][] checkA = {{-1,0,0},{-1,-1,0},{-1,-2,0},{-1,0,1},{-1,-1,1},{-1,-2,1},{-1,0,2},{-1,-1,2},{-1,-2,2},{0,0,1},{0,0,2},{0,-1,0},{0,-2,0},{0,-2,1},{0,-2,2},{1,0,0},{1,-1,0},{1,-2,0},{1,0,1},{1,-1,1},{1,-2,1},{1,0,2},{1,-1,2},{1,-2,2},{0,-1,1},{0,-1,2}};
	private int[][] checkB = {{-1,0,0},{-1,1,0},{-1,2,0},{-1,0,1},{-1,1,1},{-1,2,1},{-1,0,2},{-1,1,2},{-1,2,2},{0,0,1},{0,0,2},{0,1,0},{0,2,0},{0,2,1},{0,2,2},{1,0,0},{1,1,0},{1,2,0},{1,0,1},{1,1,1},{1,2,1},{1,0,2},{1,1,2},{1,2,2},{0,1,1},{0,1,2}};
	private int[][] checkC = {{0,-1,0},{-1,-1,0},{-2,-1,0},{0,-1,1},{-1,-1,1},{-2,-1,1},{0,-1,2},{-1,-1,2},{-2,-1,2},{0,0,1},{0,0,2},{-1,0,0},{-2,0,0},{-2,0,1},{-2,0,2},{0,1,0},{-1,1,0},{-2,1,0},{0,1,1},{-1,1,1},{-2,1,1},{0,1,2},{-1,1,2},{-2,1,2},{-1,0,1},{-1,0,2}};
	private int[][] checkD = {{0,-1,0},{1,-1,0},{2,-1,0},{0,-1,1},{1,-1,1},{2,-1,1},{0,-1,2},{1,-1,2},{2,-1,2},{0,0,1},{0,0,2},{1,0,0},{2,0,0},{2,0,1},{2,0,2},{0,1,0},{1,1,0},{2,1,0},{0,1,1},{1,1,1},{2,1,1},{0,1,2},{1,1,2},{2,1,2},{1,0,1},{1,0,2}};

	public BlockMainReservoir() {
		super(Material.ground);
	}


	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float lx, float ly, float lz){
		if(world.isRemote) return true;
		TileEntity te = world.getTileEntity(x, y, z);
		if(te != null && te instanceof TEMainReservoir){
			if(checkStructure(world, x, y, z)) player.openGui(EOK.instance, 1, world, x, y, z);
			else{
				player.addChatMessage(new ChatComponentTranslation("message.structureNotComplete"));
			}
			return true;
		}
		return false;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int par6){
		if(world.isRemote) return;
		ArrayList drops = new ArrayList();
		TileEntity teRaw = world.getTileEntity(x, y, z);
		if(teRaw != null && teRaw instanceof TEMainReservoir){
			TEMainReservoir te = (TEMainReservoir) teRaw;
			for(int i = 0; i < te.getSizeInventory(); i++){
				ItemStack stack = te.getStackInSlot(i);
				if(stack != null) drops.add(stack.copy());
			}
		}
		for (int i = 0;i < drops.size();i++)
		{
			EntityItem item = new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, (ItemStack) drops.get(i));
			item.setVelocity((rand.nextDouble() - 0.5) * 0.25, rand.nextDouble() * 0.5 * 0.25, (rand.nextDouble() - 0.5) * 0.25);
			world.spawnEntityInWorld(item);
		}
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TEMainReservoir();
	}

	private boolean checkStructure(World world, int x, int y, int z){
		boolean pat1 = true,pat2 = true,pat3 = true,pat4 = true;
		for(int i=0; i < 24; i++){
			System.out.println(world.getBlock(x + checkA[i][0], y + checkA[i][2], z + checkA[i][1]).getUnlocalizedName());
			if(!world.getBlock(x + checkA[i][0], y + checkA[i][2], z + checkA[i][1]).getUnlocalizedName().equals("tile.stone")){pat1 = false; break;}
		}
		for(int j=24;j <= 25;j++){
			if(!world.getBlock(x + checkA[j][0], y + checkA[j][2], z + checkA[j][1]).getUnlocalizedName().equals("tile.air")){pat1 = false; break;}
		}
		for(int i=0; i < 24; i++){
			if(!world.getBlock(x + checkB[i][0], y + checkB[i][2], z + checkB[i][1]).getUnlocalizedName().equals("tile.stone")){pat2 = false; break;}
		}
		for(int j=24;j <= 25;j++){
			if(!world.getBlock(x + checkB[j][0], y + checkB[j][2], z + checkB[j][1]).getUnlocalizedName().equals("tile.air")){pat2 = false; break;}
		}
		for(int i=0; i < 24; i++){
			if(!world.getBlock(x + checkC[i][0], y + checkC[i][2], z + checkC[i][1]).getUnlocalizedName().equals("tile.stone")){pat3 = false; break;}
		}
		for(int j=24;j <= 25;j++){
			if(!world.getBlock(x + checkC[j][0], y + checkC[j][2], z + checkC[j][1]).getUnlocalizedName().equals("tile.air")){pat3 = false; break;}
		}
		for(int i=0; i < 24; i++){
			if(!world.getBlock(x + checkD[i][0], y + checkD[i][2], z + checkD[i][1]).getUnlocalizedName().equals("tile.stone")){pat4 = false; break;}
		}
		for(int j=24;j <= 25;j++){
			if(!world.getBlock(x + checkD[j][0], y + checkD[j][2], z + checkD[j][1]).getUnlocalizedName().equals("tile.air")){pat4 = false; break;}
		}
		if(pat1 || pat2 || pat3 || pat4) return true;
		else return false;
	}
}
