package com.gonggongjohn.eok.handler;

import com.gonggongjohn.eok.util.IHasSpecialModel;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryHandler {
    @SubscribeEvent
    public static void onItemsRegister(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(ItemHandler.ITEM_REGISTRY.toArray(new Item[0]));
    }

    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event) {
        ItemHandler.ITEM_REGISTRY.stream()
                .filter(item -> item instanceof IHasSpecialModel)
                .forEach(item -> ((IHasSpecialModel) item).registerModel());
    }
}
