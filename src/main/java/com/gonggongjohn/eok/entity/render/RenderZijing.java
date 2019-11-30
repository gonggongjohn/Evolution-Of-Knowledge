package com.gonggongjohn.eok.entity.render;

import com.gonggongjohn.eok.EOK;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class RenderZijing<T extends EntityLiving> extends RenderNPCBase<T>{

	public RenderZijing(RenderManager rendermanagerIn) {
		super(rendermanagerIn);
	}

	@Override
	protected ResourceLocation getEntityTexture(T entity) {
		return new ResourceLocation(EOK.MODID + ":textures/entity/zijing.png");
	}

}
