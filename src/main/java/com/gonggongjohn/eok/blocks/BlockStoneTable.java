package com.gonggongjohn.eok.blocks;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.handlers.BlockHandler;
import com.gonggongjohn.eok.handlers.ItemHandler;
import com.gonggongjohn.eok.utils.IHasModel;
import com.gonggongjohn.eok.utils.JudgeWithFacing;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockStoneTable extends MultiBlockCompBase implements IHasModel {
    private final String name = "stone_table";
    public static final AxisAlignedBB STONE_TABLE_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    public BlockStoneTable() {
        super(Material.ROCK);
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        this.setCreativeTab(EOK.tabEOK);
        this.setHardness(5.0F);
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
        return STONE_TABLE_AABB;
    }

    @Override
    public void registerModel() {
        EOK.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        JudgeWithFacing checkResult = checkStructure(worldIn, pos, state, 1, "structure_elementary_research_table");
        if(checkResult.isComplete() && checkResult.getFacing() != null){
            EnumFacing facing = checkResult.getFacing();
            //facing = checkResult[1] == 0 ? (checkResult[3] == 1 ? EnumFacing.NORTH : EnumFacing.SOUTH) : (checkResult[1] == 1 ? EnumFacing.WEST : EnumFacing.EAST);
            BlockPos sideBlockPos = new BlockPos(pos.getX() + facing.getDirectionVec().getX(), pos.getY(), pos.getZ() + facing.getDirectionVec().getZ());
            worldIn.setBlockToAir(sideBlockPos);
            worldIn.setBlockState(pos, BlockHandler.blockElementaryResearchTable.getDefaultState().withProperty(FACING, facing.getOpposite()));
        }
    }
}
