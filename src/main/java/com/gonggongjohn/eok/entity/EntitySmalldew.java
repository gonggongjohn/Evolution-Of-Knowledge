package com.gonggongjohn.eok.entity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
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

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0F);
	}

	
	
	
	
}
