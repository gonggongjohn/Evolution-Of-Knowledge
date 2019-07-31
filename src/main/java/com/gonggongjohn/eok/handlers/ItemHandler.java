package com.gonggongjohn.eok.handlers;

import com.gonggongjohn.eok.items.ItemMaterialBase;
import com.gonggongjohn.eok.items.ItemPapyrus;
import com.gonggongjohn.eok.items.ItemRefractingTelescope;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemHandler {
    public static final List<Item> items = new ArrayList<Item>();

    public static final Item papyrus = new ItemPapyrus();
    public static final Item refractingTelescope = new ItemRefractingTelescope();

    public static final Item convexLens = new ItemMaterialBase("convexLens");
    public static final Item concaveLens = new ItemMaterialBase("concaveLens");

}
