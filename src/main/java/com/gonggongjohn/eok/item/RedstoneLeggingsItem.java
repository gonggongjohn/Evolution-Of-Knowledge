package com.gonggongjohn.eok.item;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.util.RedstoneArmorMaterial;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;

public class RedstoneLeggingsItem extends ArmorItem {
    private static final IArmorMaterial redstoneArmorMaterial = new RedstoneArmorMaterial();

    public RedstoneLeggingsItem() {
        super(redstoneArmorMaterial, EquipmentSlotType.LEGS, new Properties().group(EOK.EOK_ITEMGROUP));
    }
}
