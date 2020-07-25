package com.gonggongjohn.eok.blocks;

import com.github.zi_jing.cuckoolib.item.ItemStackUtil;
import com.gonggongjohn.eok.api.render.ICustomModel;
import com.gonggongjohn.eok.handlers.BlockHandler;
import com.gonggongjohn.eok.handlers.ItemHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
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

    public final static AxisAlignedBB APPLE_AABB = new AxisAlignedBB(0.3125D, 0.625D, 0.3125D, 0.6875D, 1D, 0.6875D);
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

    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (worldIn.getBlockState(pos.up()).getBlock() != Blocks.LEAVES) {
            ItemStackUtil.dropItem(worldIn, pos, new ItemStack(ItemHandler.Redstone_Apple));
            worldIn.setBlockToAir(pos);
        }
    }
}