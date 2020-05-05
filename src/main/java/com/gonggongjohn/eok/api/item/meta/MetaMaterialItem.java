package com.gonggongjohn.eok.api.item.meta;

import java.util.function.Function;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;

public class MetaMaterialItem extends MetaItem implements IMetaItem {

	public MetaMaterialItem(ResourceLocation registryName, Function<MetaValueItem, ModelResourceLocation> modelLocationFunction) {
		super(registryName, modelLocationFunction);
	}

	// TODO

}
