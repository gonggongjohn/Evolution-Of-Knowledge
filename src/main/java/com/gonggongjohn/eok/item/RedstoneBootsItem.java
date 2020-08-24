package com.gonggongjohn.eok.item;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.util.RedstoneArmorMaterial;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;

public class RedstoneBootsItem extends ArmorItem {
    private static final IArmorMaterial redstoneArmorMaterial = new RedstoneArmorMaterial();

    public RedstoneBootsItem() {
        super(redstoneArmorMaterial, EquipmentSlotType.FEET, new Properties().group(EOK.EOK_ITEMGROUP));
    }

//    @Override
//    public void onArmorTick(World world, PlayerEntity player, ItemStack item) {
//        if (!world.isRemote) {
//            player.addPotionEffect(new EffectInstance(Effects.JUMP_BOOST, 300, 0));
//            player.addPotionEffect(new EffectInstance(Effects.SPEED, 300, 1));
//        }
//    }
}
