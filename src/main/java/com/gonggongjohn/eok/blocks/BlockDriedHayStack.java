package com.gonggongjohn.eok.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockDriedHayStack extends Block {
    public final String name= "driedHayStack";
    public BlockDriedHayStack()
    {
        super(Material.GRASS);
        this.setRegistryName(name);
        this.setHardness(1.0F);
    }
}
