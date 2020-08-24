package com.gonggongjohn.eok.item;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.util.RedstoneTier;
import net.minecraft.item.IItemTier;
import net.minecraft.item.SwordItem;

public class RedstoneSwordItem extends SwordItem {
    private static final IItemTier tierRedstone = new RedstoneTier();

    public RedstoneSwordItem() {
        super(tierRedstone, 3, -2.4F, new Properties().group(EOK.EOK_ITEMGROUP));
    }
}
