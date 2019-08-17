package com.gonggongjohn.eok.entity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.world.World;

public class EntitySmalldew extends EntityLiving{

	public EntitySmalldew(World worldIn) {
		super(worldIn);
	}

	@Override
	public void onLivingUpdate() {
		// TODO 自动生成的方法存根
		super.onLivingUpdate();
	}

	@Override
	protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
		// TODO 自动生成的方法存根
		super.dropFewItems(wasRecentlyHit, lootingModifier);
	}
	
	
}
