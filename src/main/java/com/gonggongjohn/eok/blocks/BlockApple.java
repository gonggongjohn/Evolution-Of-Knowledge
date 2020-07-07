package com.gonggongjohn.eok.blocks;

import com.gonggongjohn.eok.api.render.ICustomModel;
import com.gonggongjohn.eok.handlers.BlockHandler;
import com.gonggongjohn.eok.handlers.ItemHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;

public class BlockApple extends Block implements ICustomModel {

    public final static AxisAlignedBB APPLE_AABB = new AxisAlignedBB(0.25D, 0.55D, 0.25D, 0.75D, 1.0D, 0.75D);
    public final String name = "apple";

    public BlockApple() {

        super(Material.PLANTS);
        this.setUnlocalizedName("eok." + name);
        this.setRegistryName(name);
        this.setHardness(0.1F);
        BlockHandler.registerBlock(this, new ItemBlock(this).setRegistryName(name));
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
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state,
                         int fortune) {
        drops.add(new ItemStack(ItemHandler.Redstone_Apple));
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos,
                                  EntityPlayer player) {
        return new ItemStack(ItemHandler.Redstone_Apple);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return APPLE_AABB;
    }

    @Override
    public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
        return true;
    }

    @Override
    public ModelResourceLocation getBlockModel(ModelRegistryEvent e) {
        return new ModelResourceLocation(name, "inventory");
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public int getMetaData(ModelRegistryEvent e) {
        return 0;
    }
}