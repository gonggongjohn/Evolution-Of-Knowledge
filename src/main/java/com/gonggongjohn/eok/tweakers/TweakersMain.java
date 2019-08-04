package com.gonggongjohn.eok.tweakers;

public class TweakersMain {
	public static void setup() {
		BlockHardness.setup();
		new LeavesTweaker();
		new TreeTweaker();
		new GravelTweaker();
	}
}
