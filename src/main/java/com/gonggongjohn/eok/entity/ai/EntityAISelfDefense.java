package com.gonggongjohn.eok.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

/*
 * 该文件已弃置！！
 * 该文件已弃置！！
 * 该文件已弃置！！
 * 该文件已弃置！！
 * 该文件已弃置！！
 * 该文件已弃置！！
 * 该文件已弃置！！
 * 该文件已弃置！！
 */


@Deprecated	//别看这个，这是段垃圾代码
public class EntityAISelfDefense extends EntityAIBase {

	private final EntityLiving entity;

	
	public EntityAISelfDefense(EntityLiving entity) {
		this.entity = entity;
	}

	@Override
	public boolean shouldExecute() {

		if (entity.getRevengeTarget() == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void updateTask() {

		if (entity.isDead)
			return;

		entity.attackEntityAsMob(entity.getRevengeTarget());
		super.updateTask();
	}

	@Override
	public boolean isInterruptible() {
		return true;
	}

	@Override
	public void startExecuting() {
		entity.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(Items.IRON_SWORD, 1));
		entity.setAttackTarget(entity.getRevengeTarget());
		super.startExecuting();
	}

	@Override
	public boolean shouldContinueExecuting() {
		// TODO 自动生成的方法存根
		return super.shouldContinueExecuting();
	}

	@Override
	public void resetTask() {
		// TODO 自动生成的方法存根
		super.resetTask();
	}

}
