package com.gonggongjohn.eok.handlers;

import com.gonggongjohn.eok.api.render.ICustomModel;
import com.gonggongjohn.eok.api.render.IStateMapperModel;
import com.gonggongjohn.eok.utils.IHasModel;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class ModelHandler {
	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent e) {
		ItemHandler.ITEM_REGISTRY.stream().filter((item) -> item instanceof IHasModel)
				.forEach((item) -> ((IHasModel) item).registerModel());
		BlockHandler.BLOCK_REGISTRY.stream().filter((block) -> block instanceof IHasModel)
				.forEach((block) -> ((IHasModel) block).registerModel());
		BlockHandler.BLOCK_REGISTRY.stream().filter((block) -> block instanceof ICustomModel)
				.forEach((block) -> ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block),
						((ICustomModel) block).getMetaData(e), ((ICustomModel) block).getBlockModel(e)));
		BlockHandler.BLOCK_REGISTRY.stream().filter((block) -> block instanceof IStateMapperModel).forEach(
				(block) -> ModelLoader.setCustomStateMapper(block, ((IStateMapperModel) block).getStateMapper(e)));
	}
}