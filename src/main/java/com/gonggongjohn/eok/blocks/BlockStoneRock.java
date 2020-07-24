package com.gonggongjohn.eok.blocks;

import com.github.zi_jing.cuckoolib.item.ItemStackUtil;
import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.handlers.BlockHandler;
import com.gonggongjohn.eok.handlers.ItemHandler;
import com.gonggongjohn.eok.utils.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
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
public class BlockStoneRock extends Block implements IHasModel {
    public static final PropertyInteger MODELTYPE = PropertyInteger.create("model_type", 0, 7);
    private static final AxisAlignedBB EMPTY_AABB = new AxisAlignedBB(0D, 0D, 0D, 0D, 0D, 0D);

    private static final AxisAlignedBB STONE_ROCK_AABB_0 = new AxisAlignedBB(0D, 0D, 0.1875D, 0.5D, 0.125D, 0.6875D);
    private static final AxisAlignedBB STONE_ROCK_AABB_1 = new AxisAlignedBB(0.375D, 0D, 0.25D, 0.875D, 0.125D, 0.8125D);
    private static final AxisAlignedBB STONE_ROCK_AABB_2 = new AxisAlignedBB(0.25D, 0D, 0.25D, 0.875D, 0.125D, 0.8125D);
    private static final AxisAlignedBB STONE_ROCK_AABB_3 = new AxisAlignedBB(0.4375D, 0D, 0.1875D, 0.8125D, 0.125D, 0.8125D);
    private static final AxisAlignedBB STONE_ROCK_AABB_4 = new AxisAlignedBB(0.0625D, 0D, 0.5D, 0.625D, 0.0625D, 0.8125D);
    private static final AxisAlignedBB STONE_ROCK_AABB_5 = new AxisAlignedBB(0.25D, 0D, 0.125D, 0.75D, 0.125D, 0.6875D);
    private static final AxisAlignedBB STONE_ROCK_AABB_6 = new AxisAlignedBB(0.3125D, 0D, 0.0625D, 1D, 0.125D, 0.625D);
    private static final AxisAlignedBB STONE_ROCK_AABB_7 = new AxisAlignedBB(0.25D, 0D, 0.3125D, 0.875D, 0.125D, 0.75D);

    public BlockStoneRock() {
        super(Material.ROCK);
        this.setUnlocalizedName("eok.block_stone_rock");
        this.setRegistryName("block_stone_rock");
        this.setHardness(0.2F);
        BlockHandler.registerBlock(this, new ItemBlock(this).setRegistryName("block_stone_rock"));
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull IBlockState state, int fortune) {
        drops.add(new ItemStack(BlockHandler.blockStoneRock));
    }

    @Nonnull
    @Override
    public ItemStack getPickBlock(@Nonnull IBlockState state, @Nonnull RayTraceResult target, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull EntityPlayer player) {
        return new ItemStack(Item.getItemFromBlock(this));
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
        switch (this.getMetaFromState(state)) {
            case 0:
                return STONE_ROCK_AABB_0;
            case 1:
                return STONE_ROCK_AABB_1;
            case 2:
                return STONE_ROCK_AABB_2;
            case 3:
                return STONE_ROCK_AABB_3;
            case 4:
                return STONE_ROCK_AABB_4;
            case 5:
                return STONE_ROCK_AABB_5;
            case 6:
                return STONE_ROCK_AABB_6;
            case 7:
                return STONE_ROCK_AABB_7;
            default:
                return EMPTY_AABB;
        }
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

    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (worldIn.getBlockState(pos.down()).getBlock() == Blocks.AIR) {
            ItemStackUtil.dropItem(worldIn, pos, new ItemStack(BlockHandler.blockStoneRock));
            worldIn.setBlockToAir(pos);
        }
    }

    @Override
    public void registerModel() {
        EOK.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }
}