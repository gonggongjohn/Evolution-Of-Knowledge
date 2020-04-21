package com.gonggongjohn.eok.tweakers;

import net.minecraftforge.fml.common.ProgressManager;
import net.minecraftforge.fml.common.ProgressManager.ProgressBar;

public class TweakersMain {
	public static void preInit() {
		
	}
	
	public static void init() {
		ProgressBar progress = ProgressManager.push("Initializating tweakers", 0);
		BlockHardness.setup();
		BrightnessTweaker.tweak();
		ProgressManager.pop(progress);
	}
	
	public static void postInit() {
		
	}
}
