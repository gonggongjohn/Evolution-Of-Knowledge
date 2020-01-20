package com.gonggongjohn.eok.blocks;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.handlers.BlockHandler;
import com.gonggongjohn.eok.handlers.ItemHandler;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;

public class BlockDriedHaystack extends Block {
    public final String name= "dried_haystack";
    public BlockDriedHaystack()
    {
        super(Material.GRASS);
        this.setRegistryName(name);
        this.setHardness(0.5F);
        this.setCreativeTab(EOK.tabEOK);
        this.setUnlocalizedName(name);
        this.setSoundType(SoundType.PLANT);
        BlockHandler.blocks.add(this);
        ItemHandler.items.add(new ItemBlock(this).setRegistryName(name));
    }
}