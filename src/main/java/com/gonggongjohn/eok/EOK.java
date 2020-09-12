package com.gonggongjohn.eok;

import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("eok")
public class EOK {

    public static final ItemGroup EOK_ITEMGROUP = new EOKItemGroup();
    public static final String MODID = "eok";
    private static final Logger LOGGER = LogManager.getLogger("EvolutionOfKnowledge");

    public EOK() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static Logger getLogger() {
        return LOGGER;
    }

    // 在注册事件之后被调用
    @SubscribeEvent
    public void init(final FMLCommonSetupEvent event) {

    }

    @SubscribeEvent
    public void clientInit(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
    }

    @SubscribeEvent
    public void enqueueIMC(final InterModEnqueueEvent event) {

    }

    @SubscribeEvent
    public void processIMC(final InterModProcessEvent event) {

    }

    @SubscribeEvent
    public void onServerStarting(final FMLServerStartingEvent event) {
        // do something when the server starts
    }

}
