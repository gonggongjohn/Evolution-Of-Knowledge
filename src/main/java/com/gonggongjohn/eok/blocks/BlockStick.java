package com.gonggongjohn.eok.blocks;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.handlers.BlockHandler;
import com.gonggongjohn.eok.utils.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Random;

@SuppressWarnings("deprecation")
public class BlockStick extends Block implements IHasModel {
    public static final PropertyInteger MODELTYPE = PropertyInteger.create("model_type", 0, 3);
    private static final AxisAlignedBB STICK_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 0.125D);

    public BlockStick() {
        super(Material.WOOD);
        this.setUnlocalizedName("eok.block_stick");
        this.setRegistryName("block_stick");
        this.setHardness(0.2F);
        BlockHandler.registerBlock(this, new ItemBlock(this).setRegistryName("block_stick"));
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull IBlockState state, int fortune) {
        drops.add(new ItemStack(BlockHandler.blockStick));
    }

    @Nonnull
    @Override
    public ItemStack getPickBlock(@Nonnull IBlockState state, @Nonnull RayTraceResult target, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull EntityPlayer player) {
        return new ItemStack(Items.STICK);
    }

    @Override
    public boolean isOpaqueCube(@Nonnull IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(@Nonnull IBlockState state) {
        return false;
    }

    @Nonnull
    @Override
    public AxisAlignedBB getBoundingBox(@Nonnull IBlockState state, @Nonnull IBlockAccess source, @Nonnull BlockPos pos) {
        return STICK_AABB;
    }

    @Override
    public boolean isPassable(@Nonnull IBlockAccess worldIn, @Nonnull BlockPos pos) {
        return true;
    }

    @Override
    public boolean canEntitySpawn(@Nonnull IBlockState state, @Nonnull Entity entityIn) {
        return false;
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, MODELTYPE);
    }

    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta) {
        IBlockState state = this.getDefaultState();
        return state.withProperty(MODELTYPE, meta);
    }

    @Override
    public int getMetaFromState(@Nonnull IBlockState state) {
        return (Integer) state.getProperties().get(MODELTYPE);
    }

    @Nonnull
    @Override
    public IBlockState getStateForPlacement(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ, int meta, @Nonnull EntityLivingBase placer, @Nonnull EnumHand hand) {
        return this.getRandomState();
    }

    public IBlockState getRandomState() {
        return this.getDefaultState().withProperty(MODELTYPE, new Random().nextInt(8));
    }

    @Override
    public void registerModel() {
        EOK.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }
}