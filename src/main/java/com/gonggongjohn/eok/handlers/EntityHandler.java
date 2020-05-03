package com.gonggongjohn.eok.handlers;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.entity.*;
import com.gonggongjohn.eok.entity.render.*;
import com.gonggongjohn.eok.utils.EntityRenderFactory;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityHandler {
	public static void register() {
		EntityRegistry.registerModEntity(new ResourceLocation(EOK.MODID, "entity.bullet"), EntityBullet.class,
				"eok.entity_bullet", 0, EOK.instance, 128, 1, true);

		EntityRegistry.registerModEntity(new ResourceLocation(EOK.MODID, "entity.smalldew"), EntitySmalldew.class,
				"eok.entity_smalldew", 1, EOK.instance, 64, 1, true, 0x0066CC, 0xCCE5FF);
		// 暂时不迫害咸猫
		// EntityRegistry.registerModEntity(new ResourceLocation(EOK.MODID,
		// "entity.chenweilin"), EntityChenweilin.class,
		// "entity_chenwe_i_lin", 2, EOK.instance, 64, 1, true, 0x916B94, 0x99CCFF);
		EntityRegistry.registerModEntity(new ResourceLocation(EOK.MODID, "entity.osir"), EntityOsIr.class,
				"eok.entity_osir", 3, EOK.instance, 64, 1, true, 0xFF99FF, 0x99FF99);
		EntityRegistry.registerModEntity(new ResourceLocation(EOK.MODID, "entity.zijing"), EntityZijing.class,
				"eok.entity_zijing", 4, EOK.instance, 64, 1, true, 0xB3FFFF, 0x4D94FF);
	}

	@SuppressWarnings("unchecked")
	@SideOnly(Side.CLIENT)
	public static void registerRenders() {
		registerEntityRender(EntitySmalldew.class, RenderSmalldew.class);
		// registerEntityRender(EntityChenweilin.class, RenderChenweilin.class);
		registerEntityRender(EntityOsIr.class, RenderOsIr.class);
		registerEntityRender(EntityZijing.class, RenderZijing.class);
	}

	@SideOnly(Side.CLIENT)
	private static <T extends EntityLiving, E extends RenderLiving<T>> void registerEntityRender(Class<T> entityClass,
			Class<E> render) {
		RenderingRegistry.registerEntityRenderingHandler(entityClass, new EntityRenderFactory<T>(render));
	}
}