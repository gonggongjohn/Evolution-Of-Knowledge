package com.gonggongjohn.eok.handlers;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.entity.EntityBullet;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class EntityHandler {
	public static void register() {
		EntityRegistry.registerModEntity(new ResourceLocation(EOK.MODID, "entity.bullet"), EntityBullet.class,
				"entity_bullet", 0, EOK.instance, 128, 1, true);
	}
}