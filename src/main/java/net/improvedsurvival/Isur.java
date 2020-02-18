package net.improvedsurvival;

import com.google.common.collect.ImmutableSet;

import net.improvedsurvival.blocks.FrostBerryBush;
import net.improvedsurvival.blocks.Glazer;
import net.improvedsurvival.blocks.Soil;
import net.improvedsurvival.blocks.SoilFarmland;
import net.improvedsurvival.config.json.ConfigManager;
import net.improvedsurvival.containers.GlazerContainer;
import net.improvedsurvival.items.BottledFood;
import net.improvedsurvival.items.FrostBerry;
import net.improvedsurvival.items.TemperatureArmorItem;
import net.improvedsurvival.items.TemperatureArmorMaterial;
import net.improvedsurvival.mixin_data.HoeExtensions;
import net.improvedsurvival.status_effects.Chilling;
import net.improvedsurvival.status_effects.CustomStatusEffect;
import net.improvedsurvival.util.RandomBlockMapperStructureProcessor;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.fabricmc.fabric.api.tools.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.CountDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;
import net.minecraft.world.gen.placer.SimpleBlockPlacer;
import net.minecraft.world.gen.stateprovider.SimpleStateProvider;

public class Isur implements ModInitializer {
    public static final String MODID = "isur";

    public static final int seaLevel = 141;

    public static final ItemGroup ISUR = FabricItemGroupBuilder.build(new Identifier("isur", "improved_survival"), () -> new ItemStack(Blocks.FARMLAND));

    public static final SoilFarmland SOIL_FARMLAND = new SoilFarmland(FabricBlockSettings.of(Material.EARTH).ticksRandomly().hardness(0.5f).breakByTool(FabricToolTags.SHOVELS).build());
    public static final Glazer GLAZER = new Glazer(FabricBlockSettings.of(Material.WOOD).hardness(2.5f).breakByTool(FabricToolTags.AXES).breakByHand(true).build());
    public static final Identifier GLAZER_CRAFTING = new Identifier(MODID, "glazer_crafting");
    public static final Soil SOIL = new Soil(FabricBlockSettings.of(Material.EARTH).ticksRandomly().hardness(0.5f).breakByTool(FabricToolTags.SHOVELS).build());
    public static final FrostBerryBush FROST_BERRY_BUSH = new FrostBerryBush(FabricBlockSettings.of(Material.PLANT).ticksRandomly().noCollision().sounds(BlockSoundGroup.SWEET_BERRY_BUSH).build());
    public static final Block END_GROUND = new Block(FabricBlockSettings.of(Material.STONE).hardness(1.5f).resistance(6f).breakByTool(FabricToolTags.PICKAXES).build());

    public static final FoodComponent ROASTED_CARROT = new FoodComponent.Builder().hunger(5).saturationModifier(0.7f).build();
    public static final FoodComponent SWEET_BERRY_JAM_BOTTLE = new FoodComponent.Builder().hunger(3).saturationModifier(0.5f).build();
    public static final FoodComponent FROST_BERRY_JAM_BOTTLE = new FoodComponent.Builder().hunger(3).saturationModifier(0.5f).build();
    public static final FoodComponent TOASTED_BREAD = new FoodComponent.Builder().hunger(7).saturationModifier(0.7f).build();
    public static final FoodComponent FROST_BERRIES = new FoodComponent.Builder().hunger(2).saturationModifier(0.1f).build();
    public static final FoodComponent FRIED_EGG = new FoodComponent.Builder().hunger(4).saturationModifier(0.5f).build();

    public static final StatusEffect COLD;
    public static final StatusEffect FROST_BITE;
    public static final StatusEffect SWEATING;
    public static final StatusEffect HEATSTROKE;
    public static final StatusEffect CHILLING;

    public static final Item PADDED_IRON_HELMET = new TemperatureArmorItem(TemperatureArmorMaterial.PADDED_IRON, EquipmentSlot.HEAD, new Item.Settings().group(ISUR));
    public static final Item PADDED_IRON_CHESTPLATE = new TemperatureArmorItem(TemperatureArmorMaterial.PADDED_IRON, EquipmentSlot.CHEST, new Item.Settings().group(ISUR));
    public static final Item PADDED_IRON_LEGGINGS = new TemperatureArmorItem(TemperatureArmorMaterial.PADDED_IRON, EquipmentSlot.LEGS, new Item.Settings().group(ISUR));
    public static final Item PADDED_IRON_BOOTS = new TemperatureArmorItem(TemperatureArmorMaterial.PADDED_IRON, EquipmentSlot.FEET, new Item.Settings().group(ISUR));

    public static StructureProcessorType RANDOM_BLOCK_MAPPER;

    @Override
    public void onInitialize() {
        ConfigManager.loadConfig();
        GlazerContainer.ensureReady();

        // Item Registers
        Registry.register(Registry.ITEM, new Identifier(MODID, "soil_farmland"), new BlockItem(SOIL_FARMLAND, new Item.Settings().group(ISUR)));
        Registry.register(Registry.ITEM, new Identifier(MODID, "soil"), new BlockItem(SOIL, new Item.Settings().group(ISUR)));
        Registry.register(Registry.ITEM, new Identifier(MODID, "glazer"), new BlockItem(GLAZER, new Item.Settings().group(ISUR)));
        Registry.register(Registry.ITEM, new Identifier(MODID, "end_ground"), new BlockItem(END_GROUND, new Item.Settings().group(ISUR)));
        
        // Food
        Registry.register(Registry.ITEM, new Identifier(MODID, "roasted_carrot"), new Item(new Item.Settings().group(ISUR).food(ROASTED_CARROT)));
        Registry.register(Registry.ITEM, new Identifier(MODID, "sweet_berry_jam_bottle"), new BottledFood(new Item.Settings().group(ISUR).food(SWEET_BERRY_JAM_BOTTLE).recipeRemainder(Items.GLASS_BOTTLE).maxCount(16), true));
        Registry.register(Registry.ITEM, new Identifier(MODID, "frost_berry_jam_bottle"), new BottledFood(new Item.Settings().group(ISUR).food(FROST_BERRY_JAM_BOTTLE).recipeRemainder(Items.GLASS_BOTTLE).maxCount(16), true));
        Registry.register(Registry.ITEM, new Identifier(MODID, "toasted_bread"), new Item(new Item.Settings().group(ISUR).food(TOASTED_BREAD)));
        Registry.register(Registry.ITEM, new Identifier(MODID, "frost_berries"), new FrostBerry(FROST_BERRY_BUSH, new Item.Settings().group(ISUR).food(FROST_BERRIES)));
        Registry.register(Registry.ITEM, new Identifier(MODID, "fried_egg"), new Item(new Item.Settings().group(ISUR).food(FRIED_EGG)));

        Registry.register(Registry.ITEM, new Identifier(MODID, "padded_iron_helmet"), PADDED_IRON_HELMET);
        Registry.register(Registry.ITEM, new Identifier(MODID, "padded_iron_chestplate"), PADDED_IRON_CHESTPLATE);
        Registry.register(Registry.ITEM, new Identifier(MODID, "padded_iron_leggings"), PADDED_IRON_LEGGINGS);
        Registry.register(Registry.ITEM, new Identifier(MODID, "padded_iron_boots"), PADDED_IRON_BOOTS);

        // Block Registers
        Registry.register(Registry.BLOCK, new Identifier(MODID, "soil_farmland"), SOIL_FARMLAND);
        Registry.register(Registry.BLOCK, new Identifier(MODID, "soil"), SOIL);
        Registry.register(Registry.BLOCK, new Identifier(MODID, "glazer"), GLAZER);
        Registry.register(Registry.BLOCK, new Identifier(MODID, "frost_berry_bush"), FROST_BERRY_BUSH);
        Registry.register(Registry.BLOCK, new Identifier(MODID, "end_ground"), END_GROUND);

        // Containers
        ContainerProviderRegistry.INSTANCE.registerFactory(GLAZER_CRAFTING, (syncId, identifier, player, byteBuf) -> new GlazerContainer(syncId, player.inventory));

        HoeExtensions.addTillableBlock(SOIL, SOIL_FARMLAND.getDefaultState());

        // Features
        Registry.BIOME.stream().filter(b -> b.getTemperature() <= 0.05f).forEach(b -> {
            b.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
            Feature.RANDOM_PATCH.configure(FROST_BERRY_BUSH_CONFIG).createDecoratedFeature(Decorator.COUNT_HEIGHTMAP_DOUBLE.configure(new
            CountDecoratorConfig(1))));
        });

        RANDOM_BLOCK_MAPPER = Registry.register(Registry.STRUCTURE_PROCESSOR, "random_block_mapper", RandomBlockMapperStructureProcessor::new);
    }

    static BlockState FROST_BERRY_BUSH_STATE = (BlockState)FROST_BERRY_BUSH.getDefaultState().with(FrostBerryBush.AGE, 3);
    static RandomPatchFeatureConfig FROST_BERRY_BUSH_CONFIG = (new RandomPatchFeatureConfig.Builder(new SimpleStateProvider(FROST_BERRY_BUSH_STATE), new SimpleBlockPlacer())).cannotProject().tries(64).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK)).build();

    private static StatusEffect registerStatusEffect(int rawId, String id, StatusEffect entry) {
        return (StatusEffect)Registry.register(Registry.STATUS_EFFECT, new Identifier("isur", id), entry);
    }

    public static PlayerInventory playerInv(){
        return MinecraftClient.getInstance().player.inventory;
    }

    static{
        COLD = registerStatusEffect(1507, "cold", (new CustomStatusEffect(StatusEffectType.HARMFUL, 12572927)));
        FROST_BITE = registerStatusEffect(1507, "frost_bite", (new CustomStatusEffect(StatusEffectType.HARMFUL, 12572927)));
        SWEATING = registerStatusEffect(1507, "sweating", (new CustomStatusEffect(StatusEffectType.HARMFUL, 12572927)));
        HEATSTROKE = registerStatusEffect(1507, "heatstroke", (new CustomStatusEffect(StatusEffectType.HARMFUL, 12572927)));
        CHILLING = registerStatusEffect(1507, "chilling", (new Chilling(StatusEffectType.NEUTRAL, 12572927)));
    }
}