package com.gonggongjohn.eok.items;

import java.util.List;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.entity.EntityAirBullet;
import com.gonggongjohn.eok.entity.EntityPistolBullet;
import com.gonggongjohn.eok.entity.EntityRifleBullet;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

/**
 * @author Os-Ir
 */
public class ItemGun extends Item {
	private long time;
	private int maxBulletCapacity;
	private int shootInterval;
	private String bulletType;

	public ItemGun(String name, String _bulletType, int _maxBulletCapacit, int _shootInterval) {
		this.setCreativeTab(EOK.tabEOK);
		this.setMaxStackSize(1);
		this.setUnlocalizedName("gun_" + name);
		this.setMaxBulletCapacity(_maxBulletCapacit);
		this.setShootInterval(_shootInterval);
		this.setBulletType(_bulletType);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		if (time + 100 < System.currentTimeMillis()) {
			time = System.currentTimeMillis();
			return itemStack;
		}
		NBTTagCompound nbt;
		player.swingItem();
		if (itemStack.hasTagCompound()) {
			nbt = itemStack.getTagCompound();
		} else {
			nbt = new NBTTagCompound();
		}
		if (!nbt.hasKey("lastShootTime")) {
			nbt.setLong("lastShootTime", 0);
		}
		if (nbt.getLong("lastShootTime") + this.shootInterval <= world.getTotalWorldTime()) {
			nbt.setLong("lastShootTime", world.getTotalWorldTime());
			if (nbt.hasKey("bulletNumber")) {
				int bulletNumber = nbt.getInteger("bulletNumber");
				if (bulletNumber > 0) {
					world.spawnEntityInWorld(this.getBullet(world, player));
					nbt.setInteger("bulletNumber", bulletNumber - 1);
				} else if (bulletNumber == 0) {
					nbt.setInteger("bulletNumber", this.maxBulletCapacity);
				}
			} else {
				nbt.setInteger("bulletNumber", this.maxBulletCapacity);
			}
		}
		itemStack.setTagCompound(nbt);
		return itemStack;
	}

	public EntityThrowable getBullet(World world, EntityPlayer player) {
		switch (this.bulletType) {
		case "pistolBullet":
			return new EntityPistolBullet(world, player);
		case "airBullet":
			return new EntityAirBullet(world, player);
		case "rifleBullet":
			return new EntityRifleBullet(world, player);
		}
		return null;
	}

	public ItemGun setShootInterval(int _shootInterval) {
		this.shootInterval = _shootInterval;
		return this;
	}

	public int getShootInterval() {
		return this.shootInterval;
	}

	public ItemGun setMaxBulletCapacity(int _maxBulletCapacity) {
		this.maxBulletCapacity = _maxBulletCapacity;
		return this;
	}

	public int getMaxBulletCapacity() {
		return this.maxBulletCapacity;
	}

	public ItemGun setBulletType(String _bulletType) {
		this.bulletType = _bulletType;
		return this;
	}

	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List tooltip, boolean flag) {
		if (itemStack.hasTagCompound()) {
			NBTTagCompound nbt = itemStack.getTagCompound();
			if (nbt.hasKey("bulletNumber")) {
				tooltip.add(
						I18n.format("item.gun.bulletNumber", nbt.getInteger("bulletNumber"), this.maxBulletCapacity));
			}
		} else {
			tooltip.add(I18n.format("item.gun.bulletNumber", 0, this.maxBulletCapacity));
		}
	}
}