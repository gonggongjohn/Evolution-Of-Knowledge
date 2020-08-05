package com.gonggongjohn.eok.items;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.handlers.ItemHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import java.util.ArrayList;
import java.util.Arrays;

public class ItemSmallHammer40 extends Item {
    public ArrayList<Block> blockDeleted = new ArrayList<>();
    public final Block[] blockDeletedDefault = {Blocks.STONE,Blocks.GRAVEL,Blocks.DIRT,Blocks.GRASS_PATH,
    Blocks.SAND,Blocks.SANDSTONE,Blocks.SNOW_LAYER,Blocks.GRASS};
    public final String name = "smallhammer40";

    public ItemSmallHammer40(){
        super();
        this.setUnlocalizedName("eok."+name);
        this.setRegistryName(name);
        this.setCreativeTab(EOK.tabEOK);
        ItemHandler.ITEM_REGISTRY.add(this);
        this.initList();
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        Chunk chunkIn = worldIn.getChunkFromBlockCoords(playerIn.getPosition());
        for(int x=chunkIn.x*16;x<chunkIn.x*16+16;x++)
            for(int z = chunkIn.z*16;z< chunkIn.z*16+16;z++)
                for(int y=1;y<256;y++)
                {
                    BlockPos tmpPos = new BlockPos(x,y,z);
                    Block tmpBlock = worldIn.getBlockState(tmpPos).getBlock();
                    if(this.blockDeleted.contains(tmpBlock)){
                        worldIn.setBlockToAir(tmpPos);
                    }
                }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    private void initList(){
        this.blockDeleted.addAll(Arrays.asList(this.blockDeletedDefault));
    }
}
