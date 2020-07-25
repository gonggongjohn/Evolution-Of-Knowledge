package com.gonggongjohn.eok.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public abstract class EntityBullet extends EntityThrowable {
	public static DamageSource damageSourceBullet = new DamageSource("byBullet");

	public EntityBullet(World worldIn) {
		super(worldIn);
	}

	public EntityBullet(World worldIn, EntityLivingBase throwerIn) {
		super(worldIn, throwerIn);
	}

	public EntityBullet(World worldIn, int x, int y, int z) {
		super(worldIn, x, y, z);
	}

	@Override
	protected void onImpact(MovingObjectPosition movingObjectPosition) {
		if (worldObj.isRemote) {
			return;
		}
		Entity entityHit = movingObjectPosition.entityHit;
		int i;
		if (entityHit instanceof EntityLiving) {
			EntityLiving living = (EntityLiving) movingObjectPosition.entityHit;
			living.attackEntityFrom(EntityBullet.damageSourceBullet, this.getDamage());
		}
		this.setDead();
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (ticksExisted > 200) {
			this.setDead();
		}
	}

	protected float getDamage() {
		return 0F;
	}
}