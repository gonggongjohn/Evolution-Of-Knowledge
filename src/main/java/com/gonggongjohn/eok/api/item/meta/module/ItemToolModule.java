package com.gonggongjohn.eok.api.item.meta.module;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ItemToolModule implements IItemModule {

    /**
     * @see net.minecraft.item.Item#getDestroySpeed
     */
    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        return 1.0F;
    }

    /**
     * @see net.minecraft.item.Item#canDestroyBlockInCreative
     */
    public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player) {
        return !(stack.getItem() instanceof ItemSword);
    }

    /**
     * @see net.minecraft.item.Item#shouldCauseBlockBreakReset
     */
    public boolean shouldCauseBlockBreakReset(ItemStack oldStack, ItemStack newStack) {
        return !(newStack.getItem() == oldStack.getItem() && ItemStack.areItemStackTagsEqual(newStack, oldStack) && (newStack.isItemStackDamageable() || newStack.getMetadata() == oldStack.getMetadata()));
    }

    /**
     * @see net.minecraft.item.Item#canContinueUsing
     */
    public boolean canContinueUsing(ItemStack oldStack, ItemStack newStack) {
        return oldStack.equals(newStack);
    }

    /**
     * @see net.minecraft.item.Item#canDisableShield
     */
    public boolean canDisableShield(ItemStack stack, ItemStack shield, EntityLivingBase entity, EntityLivingBase attacker) {
        return stack.getItem() instanceof ItemAxe;
    }

    /**
     * @see net.minecraft.item.Item#isShield
     */
    public boolean isShield(ItemStack stack, @Nullable EntityLivingBase entity) {
        return stack.getItem() == Items.SHIELD;
    }
}
