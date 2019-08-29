package com.gonggongjohn.eok.entity.render;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.entity.EntitySmalldew;
import com.gonggongjohn.eok.entity.model.ModelSmalldew;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerArrow;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerElytra;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;

@SideOnly(Side.CLIENT)
public class RenderSmalldew extends RenderLiving<EntitySmalldew>{

	private boolean smallArms;

	public RenderSmalldew(RenderManager rendermanagerIn) {
		//super(rendermanagerIn, new ModelSmalldew(), 0.5F);
		super(rendermanagerIn, new ModelSmalldew(0.0F, false), 0.5F);
		this.smallArms = false;
        
		this.addLayer(new LayerBipedArmor(this));
        this.addLayer(new LayerHeldItem(this));
        this.addLayer(new LayerArrow(this));
        //this.addLayer(new LayerDeadmau5Head(this));
        //this.addLayer(new LayerCape(this));
        //this.addLayer(new LayerCustomHead(this.getMainModel().bipedHead));
        this.addLayer(new LayerElytra(this));
        //this.addLayer(new LayerEntityOnShoulder(renderManager));
         
         
	}
	

	@Override
	protected ResourceLocation getEntityTexture(EntitySmalldew entity) {
		return new ResourceLocation(EOK.MODID + ":textures/entity/smalldew.png");
	}
	
	
	@Override
	protected void preRenderCallback(EntitySmalldew entitylivingbaseIn, float partialTickTime)
    {
        float f = 0.9375F;
        GlStateManager.scale(0.9375F, 0.9375F, 0.9375F);
    }
	


}



