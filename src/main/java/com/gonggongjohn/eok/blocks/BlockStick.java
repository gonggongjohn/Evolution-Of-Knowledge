package com.gonggongjohn.eok.blocks;

import com.github.zi_jing.cuckoolib.item.ItemStackUtil;
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
public class BlockStick extends Block implements IHasModel {
    public static final PropertyInteger MODELTYPE = PropertyInteger.create("model_type", 0, 7);
    private static final AxisAlignedBB EMPTY_AABB = new AxisAlignedBB(0D, 0D, 0D, 0D, 0D, 0D);

    private static final AxisAlignedBB STICK_AABB_0 = new AxisAlignedBB(0.000000D, 0.000000D, 0.000000D, 1.000000D, 0.062500D, 0.062500D);
    private static final AxisAlignedBB STICK_AABB_1 = new AxisAlignedBB(0.000000D, 0.000000D, 0.937500D, 1.000000D, 0.062500D, 1.000000D);
    private static final AxisAlignedBB STICK_AABB_2 = new AxisAlignedBB(0.937500D, 0.000000D, 0.000000D, 1.000000D, 0.062500D, 1.000000D);
    private static final AxisAlignedBB STICK_AABB_3 = new AxisAlignedBB(0.000000D, 0.000000D, 0.000000D, 0.062500D, 0.062500D, 1.000000D);
    private static final AxisAlignedBB STICK_AABB_4 = new AxisAlignedBB(0.312500D, 0.000000D, 0.000000D, 0.375000D, 0.062500D, 1.000000D);
    private static final AxisAlignedBB STICK_AABB_5 = new AxisAlignedBB(0.625000D, 0.000000D, 0.000000D, 0.687500D, 0.062500D, 1.000000D);
    private static final AxisAlignedBB STICK_AABB_6 = new AxisAlignedBB(0.000000D, 0.000000D, 0.250000D, 1.000000D, 0.062500D, 0.312500D);
    private static final AxisAlignedBB STICK_AABB_7 = new AxisAlignedBB(0.000000D, 0.000000D, 0.750000D, 1.000000D, 0.062500D, 0.812500D);

    public BlockStick() {
        super(Material.WOOD);
        this.setUnlocalizedName("eok.block_stick");
        this.setRegistryName("block_stick");
        this.setHardness(0.2F);
        BlockHandler.registerBlock(this, new ItemBlock(this).setRegistryName("block_stick"));
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull IBlockState state, int fortune) {
        drops.add(new ItemStack(Items.STICK));
    }

    @Nonnull
    @Override
    public ItemStack getPickBlock(@Nonnull IBlockState state, @Nonnull RayTraceResult target, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull EntityPlayer player) {
        return new ItemStack(Items.STICK);
    }

    public void neighborChanged(@Nonnull IBlockState state, World worldIn, BlockPos pos, @Nonnull Block blockIn, @Nonnull BlockPos fromPos) {
        if (worldIn.getBlockState(pos.down()).getBlock() == Blocks.AIR) {
            ItemStackUtil.dropItem(worldIn, pos, new ItemStack(Items.STICK));
            worldIn.setBlockToAir(pos);
        }
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
                return STICK_AABB_0;
            case 1:
                return STICK_AABB_1;
            case 2:
                return STICK_AABB_2;
            case 3:
                return STICK_AABB_3;
            case 4:
                return STICK_AABB_4;
            case 5:
                return STICK_AABB_5;
            case 6:
                return STICK_AABB_6;
            case 7:
                return STICK_AABB_7;
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

    @Override
    public void registerModel() {
        EOK.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }
}