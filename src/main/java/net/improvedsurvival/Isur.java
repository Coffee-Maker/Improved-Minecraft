package net.improvedsurvival;

import com.google.common.collect.ImmutableSet;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.improvedsurvival.blocks.FrostBerryBush;
import net.improvedsurvival.config.json.ConfigManager;
import net.improvedsurvival.containers.GlazerContainer;
import net.improvedsurvival.mixin_data.HoeExtensions;
import net.improvedsurvival.registry.IsurBlocks;
import net.improvedsurvival.registry.IsurFood;
import net.improvedsurvival.status_effects.Chilling;
import net.improvedsurvival.status_effects.CustomStatusEffect;
import net.improvedsurvival.util.RandomBlockMapperStructureProcessor;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
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

    public static final Identifier GLAZER_CRAFTING = new Identifier(Isur.MODID, "glazer_crafting");

    public static final StatusEffect COLD;
    public static final StatusEffect FROST_BITE;
    public static final StatusEffect SWEATING;
    public static final StatusEffect HEATSTROKE;
    public static final StatusEffect CHILLING;

    public static StructureProcessorType RANDOM_BLOCK_MAPPER;

    @Override
    public void onInitialize() {
        ConfigManager.loadConfig();
        GlazerContainer.ensureReady();

        IsurFood.registerFoods();

        // Containers
        ContainerProviderRegistry.INSTANCE.registerFactory(GLAZER_CRAFTING, (syncId, identifier, player, byteBuf) -> new GlazerContainer(syncId, player.inventory));

        HoeExtensions.addTillableBlock(IsurBlocks.SOIL, IsurBlocks.SOIL_FARMLAND.getDefaultState());

        // Features
        Registry.BIOME.stream().filter(b -> b.getTemperature() <= 0.05f).forEach(b -> {
            b.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
            Feature.RANDOM_PATCH.configure(FROST_BERRY_BUSH_CONFIG).createDecoratedFeature(Decorator.COUNT_HEIGHTMAP_DOUBLE.configure(new
            CountDecoratorConfig(1))));
        });

        RANDOM_BLOCK_MAPPER = Registry.register(Registry.STRUCTURE_PROCESSOR, "random_block_mapper", RandomBlockMapperStructureProcessor::new);
    }

    static BlockState FROST_BERRY_BUSH_STATE = (BlockState)IsurBlocks.FROST_BERRY_BUSH.getDefaultState().with(FrostBerryBush.AGE, 3);
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