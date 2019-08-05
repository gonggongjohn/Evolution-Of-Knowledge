package com.gonggongjohn.eok.handlers;

import com.gonggongjohn.eok.EOK;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.ProgressManager;
import net.minecraftforge.fml.common.ProgressManager.ProgressBar;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ConfigHandler {
	public static Configuration config;

	public static boolean modifyBlockHardness;
	public static boolean disableBareHandedCuttingOfTrees;

	public ConfigHandler(FMLPreInitializationEvent event) {
		ProgressBar progress = ProgressManager.push("Loading config", 1);

		EOK.instance.getLogger().info("Loading config...");

		progress.step("modifyBlockHardness");

		config = new Configuration(event.getSuggestedConfigurationFile());

		config.load();

		modifyBlockHardness = config.getBoolean("modifyBlockHardness", Configuration.CATEGORY_GENERAL, true,
				"Whether to modify the hardness of vanilla blocks. If it is set to true, the game will be more difficult.");

		disableBareHandedCuttingOfTrees = config.getBoolean("disableBareHandedCuttingOfTrees",
				Configuration.CATEGORY_GENERAL, true,
				"Disable bare-handed cutting of trees. If it is set to true, players will only be able to chop trees with axes. ");
		

		config.save();
		EOK.instance.getLogger().info("Config loaded successfully");
		ProgressManager.pop(progress);

	}
}
