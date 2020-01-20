package com.gonggongjohn.eok.blocks;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.handlers.BlockHandler;
import com.gonggongjohn.eok.handlers.ItemHandler;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
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
        BlockHandler.blocks.add(this);
        ItemHandler.items.add(new ItemBlock(this).setRegistryName(name));
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        //if(checkStructure(worldIn, pos, state, 2, "structure_test_2d")) createMultiBlock();
    }
}
