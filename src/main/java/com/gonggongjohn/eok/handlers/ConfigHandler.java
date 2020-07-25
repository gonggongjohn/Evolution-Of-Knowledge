package com.gonggongjohn.eok.handlers;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Logger;

public class ConfigHandler {
    private static Configuration config;
    private static Logger logger;
    public static int test;

    public ConfigHandler(FMLPreInitializationEvent event){
        logger = event.getModLog();
        config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
        load();
    }

    public static void load(){
        logger.info("Started loading config. ");
        String comment;

        comment = "A Test Configuration";
        test = config.get(Configuration.CATEGORY_GENERAL, "test", 1, comment).getInt();

        config.save();
        logger.info("Finished loading config. ");
    }

    public static Logger logger()
    {
        return logger;
    }

}
