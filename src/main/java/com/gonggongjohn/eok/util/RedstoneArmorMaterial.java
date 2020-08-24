package com.gonggongjohn.eok.util;

import com.gonggongjohn.eok.EOK;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

public class RedstoneArmorMaterial implements IArmorMaterial {
    @Override
    @ParametersAreNonnullByDefault
    public int getDurability(EquipmentSlotType slotIn) {
        int[] Dur = {13, 15, 16, 11};
        return Dur[slotIn.getIndex()] * 10;
    }

    @Override
    @ParametersAreNonnullByDefault
    public int getDamageReductionAmount(EquipmentSlotType slotIn) {
        int[] Damage = {2, 4, 6, 2};
        return Damage[slotIn.getIndex()];
    }

    @Override
    public int getEnchantability() {
        return 10;
    }

    @Override
    @Nonnull
    public SoundEvent getSoundEvent() {
        return SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND;
    }

    @Override
    @Nonnull
    public Ingredient getRepairMaterial() {
        return Ingredient.fromItems(Items.REDSTONE);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    @Nonnull
    public String getName() {
        return EOK.MODID + ":" + "redstone";
    }

    @Override
    public float getToughness() {
        return 1.0F;
    }

    @Override
    public float getKnockbackResistance() {
        return 0.0F;
    }
}
