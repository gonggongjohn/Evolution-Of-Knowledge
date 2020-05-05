package com.gonggongjohn.eok.api.item.meta;

import java.util.ArrayList;
import java.util.List;

import com.gonggongjohn.eok.api.item.meta.module.IItemModule;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;

public class MetaValueItem {
	
	private final short id;
	protected String translationKey;
	/**
	 * @see MetaItem#addItem
	 */
	protected String unlocalizedName = "";
	/**
	 * @see MetaItem#addItem
	 */
	protected ModelResourceLocation model = null;
	protected List<IItemModule> modules = new ArrayList<IItemModule>();
	
	public MetaValueItem(int id, String translationKey) {
		if(id < 0 || id > 32766) {
			throw new IllegalArgumentException("id must be in [0, 32766]");
		}
		this.id = (short) id;
		this.translationKey = translationKey;
	}
	
	public MetaValueItem(int id, String translationKey, ModelResourceLocation customModel) {
		this(id, translationKey);
		this.model = customModel;
	}
	
	public MetaValueItem addModule(IItemModule module) {
		this.modules.add(module);
		return this;
	}
	
	public short getId() {
		return this.id;
	}
	
	public String getUnlocalizedName() {
		return this.unlocalizedName;
	}
	
	public String getModelName() {
		return this.translationKey;
	}
	
}
