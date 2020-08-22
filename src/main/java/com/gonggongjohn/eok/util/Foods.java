package com.gonggongjohn.eok.util;

import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class Foods {
    public static final Food REDSTONE_APPLE = (new Food.Builder())
            .hunger(4)
            .saturation(0.6F)
            .effect(new EffectInstance(Effects.ABSORPTION, 600, 1), 1.0F)
            .effect(new EffectInstance(Effects.SATURATION, 100, 0), 1.0F)
            .effect(new EffectInstance(Effects.REGENERATION, 160, 0), 1.0F)
            .setAlwaysEdible()
            .build();
}
