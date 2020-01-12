package com.gonggongjohn.eok.handlers;

import com.gonggongjohn.eok.EOK;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.ProgressManager;
import net.minecraftforge.fml.common.ProgressManager.ProgressBar;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ConfigHandler {
	public static Configuration config;

	public static boolean increaseBlockHardness;
	public static boolean canPlayerChopTreesWithBareHands;
	public static boolean torcherinoExploding;
	public static boolean reduceBrightness;

	public ConfigHandler(FMLPreInitializationEvent event) {
		ProgressBar progress = ProgressManager.push("Loading config", 0);

		EOK.getLogger().info("Loading config...");

		config = new Configuration(event.getSuggestedConfigurationFile());

		config.load();

		increaseBlockHardness = config.getBoolean("increaseBlockHardness", Configuration.CATEGORY_GENERAL, true,
				"Whether to increase the hardness of vanilla blocks. If it is true, it will be very hard to break a block.");

		canPlayerChopTreesWithBareHands = config.getBoolean("canPlayerChopTreesWithBareHands",
				Configuration.CATEGORY_GENERAL, true, "If a player can chop trees with bare hands.");

		torcherinoExploding = config.getBoolean("torcherinoExploding", Configuration.CATEGORY_GENERAL, true,
				"Whether to explode when using the EOK torchino. It is a punishment for someone who likes cheating.");

		reduceBrightness = config.getBoolean("reduceBrightness", Configuration.CATEGORY_CLIENT, true,
				"Whether to reduce the brightness. If it is true, the night will be very very dark.");

		config.save();
		EOK.getLogger().info("Config loaded successfully");
		ProgressManager.pop(progress);

	}
}
