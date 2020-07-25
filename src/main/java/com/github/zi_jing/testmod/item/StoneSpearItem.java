package com.github.zi_jing.testmod.item;

import com.github.zi_jing.testmod.TestMod;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

@SuppressWarnings("NullableProblems")
public class StoneSpearItem extends Item {
    public StoneSpearItem(Properties properties) {
        super(properties.group(TestMod.testModItemGroup).maxDamage(63).maxStackSize(1));
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return 0F;
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof PlayerEntity) {
            target.attackEntityFrom(DamageSource.causePlayerDamage((PlayerEntity) attacker), 5F);
        } else {
            target.attackEntityFrom(DamageSource.causeMobDamage(attacker), 5F);
        }
        stack.setDamage(stack.getDamage() + 1);
        return true;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
}
