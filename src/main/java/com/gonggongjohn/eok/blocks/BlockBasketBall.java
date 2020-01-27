package com.gonggongjohn.eok.blocks;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.handlers.BlockHandler;
import com.gonggongjohn.eok.handlers.ItemHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockBasketBall extends Block {
	
    public final String name = "basket_ball";
    public static final AxisAlignedBB BASKET_BALL_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);

    public BlockBasketBall() {
    	
    	//纪念我们尊敬的科比-布莱恩特，2020年，1月27日
    	
        super(Material.WOOD);
        
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        this.setCreativeTab(EOK.tabEOK);
        this.setHardness(7.0F);
        
        BlockHandler.blocks.add(this);
        ItemHandler.items.add(new ItemBlock(this).setRegistryName(name));
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
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    	
        return BASKET_BALL_AABB;
    }
    
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
    	
        return EnumBlockRenderType.MODEL;
    }
}
