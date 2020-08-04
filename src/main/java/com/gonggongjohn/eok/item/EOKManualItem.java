package com.gonggongjohn.eok.item;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.client.gui.EOKManualScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class EOKManualItem extends Item {
    public EOKManualItem() {
        super(new Item.Properties().maxStackSize(1).group(EOK.EOK_ITEMGROUP));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        // 也是client only
        if (worldIn.isRemote) {
            Minecraft.getInstance().displayGuiScreen(new EOKManualScreen(new StringTextComponent("111")));
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
