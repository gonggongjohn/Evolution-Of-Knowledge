package com.gonggongjohn.eok.utils;

public class EOKToolMaterial {
	public String name;
	public float attackDamage;
	public int maxDamage;
	public int harvestLevel;
	public float efficiency;
	public int enchantAbility;
	public EOKToolMaterial(float attackDamage, int maxDamage, int harvestLevel, float efficiency, int enchantAbility) {
		this.name = "default_material";
		this.attackDamage = attackDamage;
		this.maxDamage = maxDamage;
		this.harvestLevel = harvestLevel;
		this.efficiency = efficiency;
		this.enchantAbility = enchantAbility;
	}
}
