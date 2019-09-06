package com.gonggongjohn.eok.entity.render;

import com.gonggongjohn.eok.EOK;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderZijing extends RenderNPCBase{

	public RenderZijing(RenderManager rendermanagerIn) {
		super(rendermanagerIn);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return new ResourceLocation(EOK.MODID + ":textures/entity/zijing.png");
	}

}
