package com.gonggongjohn.eok.blocks;


import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.handlers.BlockHandler;
import com.gonggongjohn.eok.handlers.ItemHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockHaystack extends Block {
    public final String name="haystack";

    public BlockHaystack()
    {
        super(Material.GRASS);
        this.setUnlocalizedName(name);
        this.setCreativeTab(EOK.tabEOK);
        this.setRegistryName(name);
        this.setHardness(1.0F);
        BlockHandler.blocks.add(this);
        ItemHandler.items.add(new ItemBlock(this).setRegistryName(name));
    }
    private boolean canDry()
    {
        World worldIn=Minecraft.getMinecraft().world;
        int y=posy+1;
        for(BlockPos tmppos=new BlockPos(posx,y,posz);y<=256;y++)
        {
            if(!worldIn.isAirBlock(tmppos)||!worldIn.isDaytime()||worldIn.isRainingAt(tmppos)){return false;}
            else tmppos=null;
        }
        return true;
    }

    private int posx,posy,posz;

    @Override
    public void onBlockPlacedBy (World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        posx=pos.getX();
        posy=pos.getY();
        posz=pos.getZ();
    }
}
