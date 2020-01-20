package com.gonggongjohn.eok.blocks;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.handlers.BlockHandler;
import com.gonggongjohn.eok.handlers.ItemHandler;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockDecomposedHaystack extends Block {
    public final String name="decomposed_haystack";
    public BlockDecomposedHaystack()
    {
        super(Material.GRASS);
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setHardness(0.3F);
        this.setCreativeTab(EOK.tabEOK);
        ItemHandler.items.add(new ItemBlock(this).setRegistryName(name));
        BlockHandler.blocks.add(this);
        this.setSoundType(SoundType.PLANT);
    }
}
