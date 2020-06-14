package com.gonggongjohn.eok.handlers;

import com.gonggongjohn.eok.EOK;
import com.gonggongjohn.eok.api.item.meta.MetaItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(modid = EOK.MODID)
public class MetaItemHandler {

    private static final MetaItem METAITEM = new MetaItem(new ResourceLocation(EOK.MODID, "meta_item0"));

    public static void setup() {
        METAITEM.setCreativeTab(EOK.tabEOK);
        METAITEM.addItem((short) 0, "others.eok_symbol");
    }

    @SubscribeEvent
    public static void onItemRegister(Register<Item> event) {
        event.getRegistry().register(METAITEM);
    }
}
