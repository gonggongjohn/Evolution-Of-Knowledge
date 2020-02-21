package com.gonggongjohn.eok.entity.render;

import com.gonggongjohn.eok.entity.model.ModelNPC;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerArrow;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerElytra;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.EntityLiving;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class RenderNPCBase<T extends EntityLiving> extends RenderLiving<T> {

	public RenderNPCBase(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ModelNPC(0.0F, false), 0.5F);
		this.addLayer(new LayerBipedArmor(this));
		this.addLayer(new LayerHeldItem(this));
		this.addLayer(new LayerArrow(this));
		// this.addLayer(new LayerCape(this));
		// this.addLayer(new LayerCustomHead(this.getMainModel().bipedHead));
		this.addLayer(new LayerElytra(this));

	}

	@Override
	protected void preRenderCallback(EntityLiving entitylivingbaseIn, float partialTickTime) {
		GlStateManager.scale(0.9375F, 0.9375F, 0.9375F);
	}

}
