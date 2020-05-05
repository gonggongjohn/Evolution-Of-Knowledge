package com.gonggongjohn.eok.api.item.meta.module;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public abstract class RightClickListenerModule implements IItemModule {

	public abstract void onItemRightClock(World worldIn, EntityPlayer playerIn, EnumHand handIn);
}
