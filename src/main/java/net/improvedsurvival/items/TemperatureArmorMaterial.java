package net.improvedsurvival.items;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Lazy;

import java.util.function.Supplier;

public enum TemperatureArmorMaterial implements ArmorMaterial {
	PADDED_IRON("padded_iron", 15, new int[] { 2, 5, 6, 2 }, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F, () -> {
		return Ingredient.ofItems(Items.IRON_INGOT);
	}, 25, 0.21f, -0.5f),
	PADDED_GOLD("padded_gold", 7, new int[] { 1, 3, 5, 2 }, 25, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0.0F, () -> {
		return Ingredient.ofItems(Items.GOLD_INGOT);
	}, 12, 0.22f, -0.6f),
	PADDED_DIAMOND("padded_diamond", 33, new int[] { 3, 6, 8, 3 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F, () -> {
		return Ingredient.ofItems(Items.DIAMOND);
	}, 12, 0.23f, -0.8f);
	
	private static final int[] BASE_DURABILITY = new int[] { 13, 15, 16, 11 };
	private final String name;
	private final int durabilityMultiplier;
	private final int[] protectionAmounts;
	private final int enchantability;
	private final SoundEvent equipSound;
	private final float toughness;
	private final Lazy<Ingredient> repairIngredientSupplier;
	private final float temperature;
	private final float heatRetention;
	private final float coldRetention;
	
	TemperatureArmorMaterial(String name, int durabilityMultiplier, int[] protectionAmounts, int enchantability, SoundEvent equipSound, float toughness, Supplier<Ingredient> ingredientSupplier, float temperature, float heatRetention, float coldRetention) {
		this.name = name;
		this.durabilityMultiplier = durabilityMultiplier;
		this.protectionAmounts = protectionAmounts;
		this.enchantability = enchantability;
		this.equipSound = equipSound;
		this.toughness = toughness;
		this.repairIngredientSupplier = new Lazy<>(ingredientSupplier);
		this.temperature = temperature;
		this.heatRetention = heatRetention;
		this.coldRetention = coldRetention;
	}
	
	public int getDurability(EquipmentSlot slot) {
		return BASE_DURABILITY[slot.getEntitySlotId()] * this.durabilityMultiplier;
	}
	
	public int getProtectionAmount(EquipmentSlot slot) {
		return this.protectionAmounts[slot.getEntitySlotId()];
	}
	
	public int getEnchantability() {
		return this.enchantability;
	}
	
	public SoundEvent getEquipSound() {
		return this.equipSound;
	}
	
	public Ingredient getRepairIngredient() {
		return this.repairIngredientSupplier.get();
	}
	
	@Environment(EnvType.CLIENT)
	public String getName() {
		return this.name;
	}
	
	public float getToughness() {
		return this.toughness;
	}
	
	public float getTemperature() {
		return this.temperature;
	}
	
	public float getHeatRetention() {
		return this.heatRetention;
	}
	
	public float getColdRetention() {
		return this.coldRetention;
	}
}