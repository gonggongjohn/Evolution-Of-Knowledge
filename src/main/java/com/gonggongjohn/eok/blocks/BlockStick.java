package com.gonggongjohn.eok.blocks;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.handlers.BlockHandler;
import com.gonggongjohn.eok.handlers.ItemHandler;
import com.gonggongjohn.eok.utils.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockStick extends Block implements IHasModel {
	
    //咕咕咕
    public final String name = "block_stick";
    public final static AxisAlignedBB Block_Stick_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 0.125D);
    
    public BlockStick() {
    	
        super(Material.WOOD);
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        this.setCreativeTab(EOK.tabEOK);
        this.setHardness(0.2F);
        
        BlockHandler.blocks.add(this);
        ItemHandler.items.add(new ItemBlock(this).setRegistryName(name));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    	
        return Block_Stick_AABB;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
    	
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
    	
        return false;
    }

    @Override
    public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
    	
        return true;
    }

	@Override
	public void registerModel() {
		
		EOK.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}
}
