package com.gonggongjohn.eok.handlers;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.client.settings.KeyBinding;

public class KeyHandler {
	public static KeyBinding showTime;

	public KeyHandler() {
		KeyHandler.showTime = new KeyBinding("key.eok.showTime", Keyboard.KEY_H, "key.categories.eok");
		ClientRegistry.registerKeyBinding(KeyHandler.showTime);
	}
}