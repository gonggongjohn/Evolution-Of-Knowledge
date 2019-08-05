package com.gonggongjohn.eok.handlers;

import com.gonggongjohn.eok.EOK;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ConfigHandler {
	public static Configuration config;

	public static boolean modifyBlockHardness;

	public ConfigHandler(FMLPreInitializationEvent event) {
		config = new Configuration(event.getSuggestedConfigurationFile());

		EOK.instance.getLogger().info("Loading config...");
		config.load();

		modifyBlockHardness = config.getBoolean("modifyBlockHardness", Configuration.CATEGORY_GENERAL, true,
				"Whether to modify the hardness of vanilla blocks.");

		config.save();
		EOK.instance.getLogger().info("Config loaded successfully");
	}
}
