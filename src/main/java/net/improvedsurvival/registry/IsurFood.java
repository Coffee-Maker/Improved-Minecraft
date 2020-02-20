package net.improvedsurvival.registry;

import net.improvedsurvival.Isur;
import net.improvedsurvival.items.BottledFood;
import net.improvedsurvival.items.FrostBerry;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class IsurFood {
    public static final FoodComponent ROASTED_CARROT = new FoodComponent.Builder().hunger(5).saturationModifier(0.7f).build();
    public static final FoodComponent SWEET_BERRY_JAM_BOTTLE = new FoodComponent.Builder().hunger(3).saturationModifier(0.5f).build();
    public static final FoodComponent FROST_BERRY_JAM_BOTTLE = new FoodComponent.Builder().hunger(3).saturationModifier(0.5f).build();
    public static final FoodComponent TOASTED_BREAD = new FoodComponent.Builder().hunger(7).saturationModifier(0.7f).build();
    public static final FoodComponent FROST_BERRIES = new FoodComponent.Builder().hunger(2).saturationModifier(0.1f).build();
    public static final FoodComponent FRIED_EGG = new FoodComponent.Builder().hunger(4).saturationModifier(0.5f).build();

    public static void registerFoods(){
        Registry.register(Registry.ITEM, new Identifier(Isur.MODID, "roasted_carrot"), new Item(new Item.Settings().group(Isur.ISUR).food(ROASTED_CARROT)));
        Registry.register(Registry.ITEM, new Identifier(Isur.MODID, "sweet_berry_jam_bottle"), new BottledFood(new Item.Settings().group(Isur.ISUR).food(SWEET_BERRY_JAM_BOTTLE).recipeRemainder(Items.GLASS_BOTTLE).maxCount(16), true));
        Registry.register(Registry.ITEM, new Identifier(Isur.MODID, "frost_berry_jam_bottle"), new BottledFood(new Item.Settings().group(Isur.ISUR).food(FROST_BERRY_JAM_BOTTLE).recipeRemainder(Items.GLASS_BOTTLE).maxCount(16), true));
        Registry.register(Registry.ITEM, new Identifier(Isur.MODID, "toasted_bread"), new Item(new Item.Settings().group(Isur.ISUR).food(TOASTED_BREAD)));
        Registry.register(Registry.ITEM, new Identifier(Isur.MODID, "frost_berries"), new FrostBerry(IsurBlocks.FROST_BERRY_BUSH, new Item.Settings().group(Isur.ISUR).food(FROST_BERRIES)));
        Registry.register(Registry.ITEM, new Identifier(Isur.MODID, "fried_egg"), new Item(new Item.Settings().group(Isur.ISUR).food(FRIED_EGG)));
    }
}