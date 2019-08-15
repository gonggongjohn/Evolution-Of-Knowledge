package com.gonggongjohn.eok.items;

import java.util.List;

import javax.annotation.Nullable;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.entity.EntityBullet;
import com.gonggongjohn.eok.handlers.ItemHandler;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMachineGun extends Item {
	public ItemMachineGun() {
		this.setRegistryName("machine_gun");
		this.setUnlocalizedName("machine_gun");
		this.setCreativeTab(EOK.tabEOK);
		ItemHandler.items.add(this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List tooltip, ITooltipFlag flagIn) {
		tooltip.add(I18n.format("item.machine_gun.description"));
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if (!world.isRemote) {
			EntityBullet bullet = new EntityBullet(world, player);
			// EntityTippedArrow bullet = new EntityTippedArrow(world, player);
			Vec3d vec = player.getLookVec();
			bullet.shoot(vec.x, vec.y, vec.z, 40, 0);
			world.spawnEntity(bullet);
		}
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
	}
}