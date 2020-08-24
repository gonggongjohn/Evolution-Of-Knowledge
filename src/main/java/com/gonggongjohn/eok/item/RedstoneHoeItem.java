package com.gonggongjohn.eok.item;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.util.RedstoneTier;
import net.minecraft.item.HoeItem;
import net.minecraft.item.IItemTier;

public class RedstoneHoeItem extends HoeItem {
    private static final IItemTier tierRedstone = new RedstoneTier();

    public RedstoneHoeItem() {
        super(tierRedstone, -3, 0.0F, new Properties().group(EOK.EOK_ITEMGROUP));
    }
}
