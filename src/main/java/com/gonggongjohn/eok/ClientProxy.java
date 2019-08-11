package com.gonggongjohn.eok;

import com.gonggongjohn.eok.utils.EOKToolMaterials;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ClientProxy extends CommonProxy{
    public void preInit(FMLPreInitializationEvent event){
    	EOKToolMaterials.setupClient();
    	super.preInit(event);
    }

    public void init(FMLInitializationEvent event){
        super.init(event);
    }

    public void postInit(FMLPostInitializationEvent event){
        super.postInit(event);
    }

    public void registerItemRenderer(Item item, int meta, String id){
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
    }

    public IThreadListener getThreadListener(MessageContext context){
        if (context.side.isClient())
        {
            return Minecraft.getMinecraft();
        }
        else
        {
            return context.getServerHandler().player.mcServer;
        }
    }

    public EntityPlayer getPlayer(MessageContext context)
    {
        if (context.side.isClient())
        {
            return Minecraft.getMinecraft().player;
        }
        else
        {
            return context.getServerHandler().player;
        }
    }
}
