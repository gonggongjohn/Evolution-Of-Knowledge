package com.gonggongjohn.eok.items;

import com.gonggongjohn.eok.EOK;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;

public class ItemRedstoneArmor extends ItemArmor {
    public static final ItemArmor.ArmorMaterial REDSTONE = EnumHelper.addArmorMaterial("redstone",
            EOK.MODID + ":" + "redstone", 16, new int[]
            { 2, 4, 6, 2 }, 10,SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 1.0F);

    public ItemRedstoneArmor(EntityEquipmentSlot equipmentSlotIn)
    {
        super(REDSTONE, REDSTONE.ordinal(), equipmentSlotIn);
    }
}

