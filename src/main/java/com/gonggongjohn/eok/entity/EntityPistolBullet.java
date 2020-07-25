package com.gonggongjohn.eok.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityPistolBullet extends EntityBullet {
	public EntityPistolBullet(World worldIn) {
		super(worldIn);
	}

	public EntityPistolBullet(World worldIn, EntityLivingBase throwerIn) {
		super(worldIn, throwerIn);
	}

	public EntityPistolBullet(World worldIn, int x, int y, int z) {
		super(worldIn, x, y, z);
	}

	@Override
	protected float func_70182_d() {
		return 19.2F;
	}

	@Override
	protected float getDamage() {
		return 16F;
	}
}