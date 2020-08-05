package com.gonggongjohn.eok.items;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.handlers.ItemHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class ItemBigHammer80 extends Item {
    public final String name = "bighammer80";
    public ItemBigHammer80()
    {
        super();
        this.setMaxStackSize(1);
        this.setCreativeTab(EOK.tabEOK);
        this.setUnlocalizedName("eok."+name);
        this.setRegistryName(name);
        ItemHandler.ITEM_REGISTRY.add(this);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        Chunk chunkIn = worldIn.getChunkFromBlockCoords(playerIn.getPosition());
        for(int x=chunkIn.x*16;x<chunkIn.x*16+16;x++)
            for(int z = chunkIn.z*16;z< chunkIn.z*16+16;z++)
                for(int y=1;y<256;y++)
                    worldIn.setBlockToAir(new BlockPos(x,y,z));
        return super.onItemRightClick(worldIn,playerIn,handIn);
    }
}
