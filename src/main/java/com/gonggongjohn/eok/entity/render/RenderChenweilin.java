package com.gonggongjohn.eok.entity.render;

import com.gonggongjohn.eok.EOK;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class RenderChenweilin<T extends EntityLiving> extends RenderNPCBase<T> {


    public RenderChenweilin(RenderManager manager) {
        super(manager);
    }

    @Override
    protected ResourceLocation getEntityTexture(T entity) {
        return new ResourceLocation(EOK.MODID + ":textures/entity/chenweilin.png");
    }
}
