package com.gonggongjohn.eok.blocks;

import javax.annotation.Nullable;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.handlers.BlockHandler;
import com.gonggongjohn.eok.handlers.GUIHandler;
import com.gonggongjohn.eok.handlers.ItemHandler;
import com.gonggongjohn.eok.tile.TEElementaryResearchTable;
import com.gonggongjohn.eok.utils.IHasModel;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockElementaryResearchTable extends BlockContainer implements IHasModel {
    public final String name = "elementary_research_table";
    private AxisAlignedBB ELEMENTARY_RESEARCH_TABLE_AABB_S = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 2.0D);
    private AxisAlignedBB ELEMENTARY_RESEARCH_TABLE_AABB_N = new AxisAlignedBB(0.0D, 0.0D, -1.0D, 1.0D, 1.0D, 1.0D);
    private AxisAlignedBB ELEMENTARY_RESEARCH_TABLE_AABB_E = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 2.0D, 1.0D, 1.0D);
    private AxisAlignedBB ELEMENTARY_RESEARCH_TABLE_AABB_W = new AxisAlignedBB(-1.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    public BlockElementaryResearchTable() {
        super(Material.ROCK);
        this.setUnlocalizedName("eok." + name);
        this.setRegistryName(name);
        this.setHardness(5.0F);
        //this.setCreativeTab(EOK.tabEOK);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        BlockHandler.BLOCK_REGISTRY.add(this);
        ItemHandler.ITEM_REGISTRY.add(new ItemBlock(this).setRegistryName(name));
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
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public void registerModel() {
        EOK.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TEElementaryResearchTable();
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        EnumFacing facing = state.getValue(FACING);
        switch (facing){
            case NORTH: return ELEMENTARY_RESEARCH_TABLE_AABB_S;
            case SOUTH: return ELEMENTARY_RESEARCH_TABLE_AABB_N;
            case WEST: return ELEMENTARY_RESEARCH_TABLE_AABB_E;
            case EAST: return ELEMENTARY_RESEARCH_TABLE_AABB_W;
            default: return ELEMENTARY_RESEARCH_TABLE_AABB_S;
        }
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing facing = EnumFacing.getHorizontal(meta & 3);
        return this.getDefaultState().withProperty(FACING, facing);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int facing = state.getValue(FACING).getHorizontalIndex();
        return facing;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()));
    }


    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote){
            int id = GUIHandler.GUIElementaryResearchTable;
            playerIn.openGui(EOK.instance, id, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }
}
