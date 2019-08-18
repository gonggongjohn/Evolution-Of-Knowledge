package com.gonggongjohn.eok.entity;

import com.gonggongjohn.eok.entity.ai.EntityAISelfDefense;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveIndoors;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAIRestrictOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntitySmalldew extends EntityCreature {

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
		ItemStack egg = new ItemStack(Items.SPAWN_EGG, 1);
		ItemMonsterPlacer.applyEntityIdToItemStack(egg, new ResourceLocation("eok:entity.smalldew"));
		this.entityDropItem(egg, 0.0F);
		super.dropFewItems(wasRecentlyHit, lootingModifier);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0F);
	}

	@Override
	protected void initEntityAI() {
		
		
		this.tasks.addTask(1, new EntityAISwimming(this));
		//this.tasks.addTask(2, new EntityAIMoveTowardsTarget(this, 0.9D, 32.0F));
		//this.tasks.addTask(3, new EntityAIPanic(this, 0.5D));
		this.tasks.addTask(1, new EntityAIMoveIndoors(this));
        this.tasks.addTask(1, new EntityAIRestrictOpenDoor(this));
        this.tasks.addTask(1, new EntityAIOpenDoor(this, true));
		this.tasks.addTask(2, new EntityAIAttackMelee(this, 0.55D, true));
        this.tasks.addTask(2, new EntityAIMoveTowardsTarget(this, 0.55D, 32.0F));
		
        this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 0.4D));
		this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
		this.tasks.addTask(8,  new EntityAIWatchClosest(this, EntitySmalldew.class, 10.0F));
		this.targetTasks.addTask(9, new EntityAIWatchClosest(this, EntityLiving.class, 10.0F));
		
		//this.targetTasks.addTask(1, new EntityAIAttackMelee(this, 0.7D, false));
		this.targetTasks.addTask(1, new EntityAISelfDefense(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
		
		//this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityZombie.class, true));
		
		super.initEntityAI();
	}
	
	

	
}


