package com.gonggongjohn.eok.tweakers;

import com.gonggongjohn.eok.handlers.ConfigHandler;

import net.minecraft.client.Minecraft;

public class BrightnessTweaker {
	
	public static final float GAMMA = -0.30F; 
	
	public static void tweak() {
		if(!ConfigHandler.reduceBrightness)
			return;
		
		Minecraft.getMinecraft().gameSettings.gammaSetting = GAMMA;
	}
	
}
