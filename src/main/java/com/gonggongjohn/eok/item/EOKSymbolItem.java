package com.gonggongjohn.eok.item;

import com.gonggongjohn.eok.EOK;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class EOKSymbolItem extends Item {

    public EOKSymbolItem() {
        super(new Item.Properties().maxStackSize(1).group(EOK.EOK_ITEMGROUP));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        // 仅在客户端执行
        if (worldIn.isRemote) {
            playerIn.sendStatusMessage(new TranslationTextComponent("eok.status_message.eok_symbol_click"), false);
            playerIn.swingArm(handIn);
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
