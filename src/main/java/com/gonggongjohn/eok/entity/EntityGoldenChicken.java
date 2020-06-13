package com.gonggongjohn.eok.entity;

import com.gonggongjohn.eok.handlers.ItemHandler;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.world.World;

public class EntityGoldenChicken extends EntityChicken {
    public EntityGoldenChicken(World worldIn) {
        super(worldIn);
        this.setSize(1.2F, 1.8F);
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
    }

    @Override
    protected void dropFewItems(boolean arg1, int arg2) {
        if (this.rand.nextInt(10) == 0) {
            this.dropItem(ItemHandler.Golden_Egg, 1);
        }
        super.dropFewItems(arg1, arg2);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();

        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
    }
}
