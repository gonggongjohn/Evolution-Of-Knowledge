package com.gonggongjohn.eok.model;

import org.lwjgl.opengl.GL11;

import com.gonggongjohn.eok.EOK;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderBullet extends Render {
	private static final ResourceLocation texture = new ResourceLocation(EOK.MODID, "textures/entity/bullet.png");
	private ModelBase model;

	public RenderBullet() {
		model = new ModelBullet();
	}
	
	@Override
	public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTick) {
		GL11.glPushMatrix();
		bindTexture(texture);
		GL11.glTranslated(x, y - 1.25D, z);
		model.render(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return texture;
	}
}