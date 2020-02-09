package com.gonggongjohn.eok.blocks;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.handlers.BlockHandler;
import com.gonggongjohn.eok.handlers.GUIHandler;
import com.gonggongjohn.eok.handlers.ItemHandler;
import com.gonggongjohn.eok.utils.JudgeWithFacing;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockTest2DCore extends MultiBlockCompBase {
    private final String name = "test_2d_core";

    public BlockTest2DCore() {
        super(Material.ROCK);
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        this.setCreativeTab(EOK.tabEOK);
        this.setHardness(5.0F);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.SOUTH));
        BlockHandler.blocks.add(this);
        ItemHandler.items.add(new ItemBlock(this).setRegistryName(name));
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()));
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        String structureName = EOK.multiBlockDict.structureNameDict.get(this.getUnlocalizedName());
        int dimensionNum = EOK.multiBlockDict.structureDimensionDict.get(structureName);
        JudgeWithFacing checkResult = checkStructure(worldIn, pos, state, dimensionNum, structureName);
        if(checkResult.isComplete() && checkResult.getFacing() != null){
            int id = GUIHandler.GUITest2D;
            playerIn.openGui(EOK.instance, id, worldIn, pos.getX(), pos.getY(), pos.getZ());
            EOK.getLogger().info("Structure Complete!");
        }
        return true;
    }
}
