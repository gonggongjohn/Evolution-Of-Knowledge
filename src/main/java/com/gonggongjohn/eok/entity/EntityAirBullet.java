package com.gonggongjohn.eok.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityAirBullet extends EntityBullet {
	public EntityAirBullet(World worldIn) {
		super(worldIn);
	}

	public EntityAirBullet(World worldIn, EntityLivingBase throwerIn) {
		super(worldIn, throwerIn);
	}

	public EntityAirBullet(World worldIn, int x, int y, int z) {
		super(worldIn, x, y, z);
	}

	@Override
	protected float func_70182_d() {
		return 9.8F;
	}

	@Override
	protected float getDamage() {
		return 8F;
	}
}