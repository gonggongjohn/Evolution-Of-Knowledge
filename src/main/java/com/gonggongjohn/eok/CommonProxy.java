package com.gonggongjohn.eok;

import com.gonggongjohn.eok.entity.EntityAirBullet;
import com.gonggongjohn.eok.entity.EntityBullet;
import com.gonggongjohn.eok.entity.EntityRifleBullet;
import com.gonggongjohn.eok.handlers.BlockHandler;
import com.gonggongjohn.eok.handlers.ConfigHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;

public class CommonProxy {
	public void preInit(FMLPreInitializationEvent event) {
		new ConfigHandler(event);
		HeatableItemHandler.setup();
		HeatableItemHandler.register();
		EntityRegistry.registerModEntity(EntityAirBullet.class, "AirBullet", 0, EOK.instance, 256, 1, true);
		EntityRegistry.registerModEntity(EntityPistolBullet.class, "PistolBullet", 0, EOK.instance, 256, 1, true);
		EntityRegistry.registerModEntity(EntityRifleBullet.class, "RifleBullet", 0, EOK.instance, 256, 1, true);
	}

	public void init(FMLInitializationEvent event) {

	}

	public void postInit(FMLPostInitializationEvent event) {

	}
}
