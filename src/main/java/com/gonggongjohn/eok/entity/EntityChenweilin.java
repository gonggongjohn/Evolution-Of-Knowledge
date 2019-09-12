package com.gonggongjohn.eok.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityChenweilin extends EntityNPCBase{

	public EntityChenweilin(World worldIn) {
		super(worldIn);
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
	}

	@Override
	protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
		ItemStack egg = new ItemStack(Items.SPAWN_EGG, 1);
		ItemMonsterPlacer.applyEntityIdToItemStack(egg, new ResourceLocation("eok:entity.chenweilin"));
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

	@Override
	protected boolean processInteract(EntityPlayer player, EnumHand hand) {
		return super.processInteract(player, hand);
	}
	
	
}
