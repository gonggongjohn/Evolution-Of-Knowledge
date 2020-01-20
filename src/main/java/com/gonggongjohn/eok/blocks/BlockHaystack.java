package com.gonggongjohn.eok.blocks;


import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.handlers.BlockHandler;
import com.gonggongjohn.eok.handlers.ItemHandler;
import com.gonggongjohn.eok.tile.TEHaystack;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.World;


public class BlockHaystack extends BlockContainer{
    public final String name="haystack";
    public BlockHaystack()
    {
        super(Material.GRASS);
        this.setUnlocalizedName(name);
        this.setCreativeTab(EOK.tabEOK);
        this.setRegistryName(name);
        this.setHardness(0.5F);
        this.setSoundType(SoundType.PLANT);
        BlockHandler.blocks.add(this);
        ItemHandler.items.add(new ItemBlock(this).setRegistryName(name));
    }
    @Override
    public TileEntity createNewTileEntity(World worldIn,int meta)
    {
        return new TEHaystack();
    }
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }
}