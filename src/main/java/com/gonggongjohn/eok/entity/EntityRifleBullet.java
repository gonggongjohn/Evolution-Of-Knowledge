package com.gonggongjohn.eok.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityRifleBullet extends EntityBullet {
	public EntityRifleBullet(World worldIn) {
		super(worldIn);
	}

	public EntityRifleBullet(World worldIn, EntityLivingBase throwerIn) {
		super(worldIn, throwerIn);
	}

	public EntityRifleBullet(World worldIn, int x, int y, int z) {
		super(worldIn, x, y, z);
	}

	@Override
	protected float func_70182_d() {
		return 51.2F;
	}

	@Override
	protected float getDamage() {
		return 32F;
	}
}