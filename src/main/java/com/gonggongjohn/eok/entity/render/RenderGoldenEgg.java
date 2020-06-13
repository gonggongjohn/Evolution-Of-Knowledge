package com.gonggongjohn.eok.entity.render;

import com.gonggongjohn.eok.entity.EntityGoldenEgg;
import com.gonggongjohn.eok.handlers.ItemHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;

public class RenderGoldenEgg extends RenderSnowball<EntityGoldenEgg> {
    public RenderGoldenEgg(RenderManager renderManagerIn) {
        super(renderManagerIn, ItemHandler.Golden_Egg, Minecraft.getMinecraft().getRenderItem());
    }
}