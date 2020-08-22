package com.gonggongjohn.eok.item;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.tier.RedstoneTier;
import net.minecraft.item.IItemTier;
import net.minecraft.item.PickaxeItem;

public class RedstonePickaxeItem extends PickaxeItem {
    private static final IItemTier tierRedstone = new RedstoneTier();

    public RedstonePickaxeItem() {
        super(tierRedstone, 1, -2.8F, new Properties().group(EOK.EOK_ITEMGROUP));
    }
}
