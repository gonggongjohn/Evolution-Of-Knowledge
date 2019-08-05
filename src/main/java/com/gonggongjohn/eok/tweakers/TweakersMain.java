package com.gonggongjohn.eok.tweakers;

import net.minecraftforge.fml.common.ProgressManager;
import net.minecraftforge.fml.common.ProgressManager.ProgressBar;

public class TweakersMain {
	public static void setup() {
		ProgressBar progress = ProgressManager.push("Initializating tweakers", 4);
		progress.step("BlockHardnessTweaker");
		BlockHardness.setup();
		progress.step("LeavesTweaker");
		new LeavesTweaker();
		progress.step("TreeTweaker");
		new TreeTweaker();
		progress.step("GravelTweaker");
		new GravelTweaker();
		ProgressManager.pop(progress);
	}
}
