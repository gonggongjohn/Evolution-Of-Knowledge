package com.gonggongjohn.eok.blocks;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.handlers.BlockHandler;
import com.gonggongjohn.eok.handlers.ItemHandler;
import com.gonggongjohn.eok.utils.IHasModel;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class BlockDecomposedHaystack extends Block implements IHasModel {
	
    public final String name = "decomposed_haystack";
    
    public BlockDecomposedHaystack() {
    	
        super(Material.GRASS);
        this.setRegistryName(name);
        this.setUnlocalizedName("eok." + name);
        this.setHardness(0.3F);
        this.setCreativeTab(EOK.tabEOK);
        this.setSoundType(SoundType.PLANT);
        
        ItemHandler.ITEM_REGISTRY.add(new ItemBlock(this).setRegistryName(name));
        BlockHandler.BLOCK_REGISTRY.add(this);
    }
    
	@Override
	public void registerModel() {
		
		EOK.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}
}
