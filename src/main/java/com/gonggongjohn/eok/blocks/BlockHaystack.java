package com.gonggongjohn.eok.blocks;


import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.handlers.BlockHandler;
import com.gonggongjohn.eok.handlers.ItemHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(modid = EOK.MODID)
public class BlockHaystack extends Block {
    public final String name="haystack";
    private boolean posCanDry;//放置的位置是否可以晾干

    public BlockHaystack()
    {
        super(Material.GRASS);
        this.setUnlocalizedName(name);
        this.setCreativeTab(EOK.tabEOK);
        this.setRegistryName(name);
        this.setHardness(1.0F);
        BlockHandler.blocks.add(this);
        ItemHandler.items.add(new ItemBlock(this).setRegistryName(name));
    }

    private int sec=0;
    private int posx,posy,posz;
    //didn't work?
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void serverTick()
    {
        if(sec<50)sec++;
        else
        {
            sec=0;
            BlockPos pos=new BlockPos(posx,posy,posz);
            if(Minecraft.getMinecraft().world.canBlockSeeSky(pos))posCanDry=true;
            else posCanDry=false;
            System.out.println("now dry state:"+posCanDry);
        }
    }
    //half completed
    @Override
    public void onBlockPlacedBy (World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        posx=pos.getX();
        posy=pos.getY();
        posz=pos.getZ();
        int y=posy+1;
        posCanDry=true;
        for(BlockPos tmppos=new BlockPos(posx,y,posz);y<=256;y++)
        {
            if(!worldIn.isAirBlock(tmppos)||!worldIn.isDaytime()||worldIn.isRainingAt(pos)){posCanDry=false;break;}
        }
        //System.out.println(posCanDry);
    }
}
