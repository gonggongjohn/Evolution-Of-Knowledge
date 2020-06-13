package com.gonggongjohn.eok.entity;

import com.gonggongjohn.eok.handlers.ItemHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityGoldenEgg extends EntityThrowable {
    public EntityGoldenEgg(World worldIn) {
        super(worldIn);
    }

    public EntityGoldenEgg(World worldIn, EntityLivingBase throwerIn) {
        super(worldIn, throwerIn);
    }

    public EntityGoldenEgg(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    @Override
    protected void onImpact(RayTraceResult movingObjectPosition) {
        if (!this.world.isRemote) {
            if ((movingObjectPosition.entityHit instanceof EntityChicken) && !(movingObjectPosition.entityHit instanceof EntityGoldenChicken)) {
                EntityChicken originalChicken = (EntityChicken) movingObjectPosition.entityHit;
                EntityGoldenChicken goldenChicken = new EntityGoldenChicken(this.world);
                goldenChicken.setGrowingAge(originalChicken.getGrowingAge());
                goldenChicken.setLocationAndAngles(originalChicken.posX, originalChicken.posY, originalChicken.posZ,
                        originalChicken.rotationYaw, originalChicken.rotationPitch);
                originalChicken.setDead();
                this.world.spawnEntity(goldenChicken);
            } else {
                this.world.spawnEntity(new EntityItem(this.world, this.posX, this.posY, this.posZ,
                        new ItemStack(ItemHandler.Golden_Egg)));
            }
            this.setDead();
        }
    }
}