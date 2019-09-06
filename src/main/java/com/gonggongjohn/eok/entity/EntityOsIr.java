package com.gonggongjohn.eok.entity;

import net.minecraft.init.Items;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityOsIr extends EntityNPCBase{

	public EntityOsIr(World worldIn) {
		super(worldIn);
		// TODO 自动生成的构造函数存根
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
	}

	@Override
	protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
		ItemStack egg = new ItemStack(Items.SPAWN_EGG, 1);
		ItemMonsterPlacer.applyEntityIdToItemStack(egg, new ResourceLocation("eok:entity.osir"));
		this.entityDropItem(egg, 0.0F);
		super.dropFewItems(wasRecentlyHit, lootingModifier);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
	}

	@Override
	protected void initEntityAI() {
		super.initEntityAI();
	}
	
	

}
