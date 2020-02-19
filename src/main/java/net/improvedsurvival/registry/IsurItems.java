package net.improvedsurvival.registry;

import net.improvedsurvival.Isur;
import net.improvedsurvival.items.TemperatureArmorItem;
import net.improvedsurvival.items.TemperatureArmorMaterial;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class IsurItems {
    public static final Item PADDED_IRON_HELMET = register("padded_iron_helmet", new TemperatureArmorItem(TemperatureArmorMaterial.PADDED_IRON, EquipmentSlot.HEAD, new Item.Settings().group(Isur.ISUR_GROUP)));
    public static final Item PADDED_IRON_CHESTPLATE = register("padded_iron_chestplate", new TemperatureArmorItem(TemperatureArmorMaterial.PADDED_IRON, EquipmentSlot.CHEST, new Item.Settings().group(Isur.ISUR_GROUP)));
    public static final Item PADDED_IRON_LEGGINGS = register("padded_iron_leggings", new TemperatureArmorItem(TemperatureArmorMaterial.PADDED_IRON, EquipmentSlot.LEGS, new Item.Settings().group(Isur.ISUR_GROUP)));
    public static final Item PADDED_IRON_BOOTS = register("padded_iron_boots", new TemperatureArmorItem(TemperatureArmorMaterial.PADDED_IRON, EquipmentSlot.FEET, new Item.Settings().group(Isur.ISUR_GROUP)));

    private static Item register(String id, Item item) {
        return (Item)Registry.register(Registry.ITEM, new Identifier(Isur.MODID, id), item);
    }
}