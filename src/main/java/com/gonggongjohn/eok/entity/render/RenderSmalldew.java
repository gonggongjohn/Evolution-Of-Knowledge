package com.gonggongjohn.eok.entity.render;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.entity.EntityNPCBase;
import com.gonggongjohn.eok.entity.EntitySmalldew;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderSmalldew extends RenderNPCBase{

	public RenderSmalldew(RenderManager rendermanagerIn) {
		super(rendermanagerIn);
	}


	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return new ResourceLocation(EOK.MODID + ":textures/entity/smalldew.png");
	}

}
