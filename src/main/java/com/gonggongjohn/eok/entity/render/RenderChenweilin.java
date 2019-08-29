package com.gonggongjohn.eok.entity.render;

import com.gonggongjohn.eok.EOK;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderChenweilin extends RenderNPCBase {

	
	public RenderChenweilin(RenderManager manager) {
		super(manager);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return new ResourceLocation(EOK.MODID + ":textures/entity/chenweilin.png");
	}
}
