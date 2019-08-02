package com.gonggongjohn.eok;

import com.gonggongjohn.eok.network.PacketGuiButton;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = EOK.MODID, name = EOK.NAME, version = EOK.VERSION, useMetadata = true)
public class EOK
{
    public static final String MODID = "eok";
    public static final String NAME = "Evolution Of Knowledge";
    public static final String VERSION = "0.0.1";

    @Mod.Instance
    public static EOK instance;

    @SidedProxy(clientSide = "com.gonggongjohn.eok.ClientProxy", serverSide = "com.gonggongjohn.eok.CommonProoxy")
    public static CommonProxy proxy;

    public static final CreativeTabs tabEOK = new EOKTab();

    private SimpleNetworkWrapper network;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
		proxy.preInit(event);
		network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);

		network.registerMessage(new PacketGuiButton.Handler(), PacketGuiButton.class, 0, Side.SERVER);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }
	@EventHandler
    public void postInit(FMLPostInitializationEvent event){
        proxy.postInit(event);
    }

    public static CommonProxy getProxy(){
        return proxy;
    }

    public static SimpleNetworkWrapper getNetwork(){
        return instance.network;
    }
}
