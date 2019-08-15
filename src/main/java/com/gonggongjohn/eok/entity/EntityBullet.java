package com.gonggongjohn.eok.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityBullet extends EntityTippedArrow {
	public EntityBullet(World world) {
		super(world);
	}

	public EntityBullet(World world, EntityLivingBase thrower) {
		super(world, thrower);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (this.inGround) {
			this.setDead();
		}
	}

	// @Override
	// protected void onImpact(RayTraceResult result) {
	// if (this.world.isRemote) {
	// return;
	// }
	// if (result.typeOfHit == RayTraceResult.Type.ENTITY) {
	// System.out.println(result.entityHit.getEntityId());
	// if (result.entityHit instanceof EntityLiving) {
	// EntityLiving living = (EntityLiving) result.entityHit;
	// living.attackEntityFrom(DamageSource.causeThrownDamage(this, this.thrower),
	// this.damage);
	// }
	// }
	// this.setDead();
	// }

	@Override
	protected ItemStack getArrowStack() {
		return ItemStack.EMPTY;
	}
}