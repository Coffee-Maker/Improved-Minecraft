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
import net.improvedsurvival.registry.IsurItems;
import net.improvedsurvival.registry.IsurStatusEffects;
import net.improvedsurvival.util.RandomBlockStateMapperStructureProcessor;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;

public class Isur implements ModInitializer {
    public static final String MODID = "isur";
    public static final int seaLevel = 140;
    public static final ItemGroup ISUR = FabricItemGroupBuilder.build(new Identifier("isur", "improved_survival"), () -> new ItemStack(Blocks.FARMLAND));
    public static final Identifier GLAZER_CRAFTING = new Identifier(Isur.MODID, "glazer_crafting");
    public static StructureProcessorType RANDOM_BLOCK_MAPPER;
    public static final BlockState FROST_BERRY_BUSH_STATE = IsurBlocks.FROST_BERRY_BUSH.getDefaultState().with(FrostBerryBush.AGE, 3);
    public static final RandomPatchFeatureConfig FROST_BERRY_BUSH_CONFIG = (new RandomPatchFeatureConfig.Builder(new SimpleBlockStateProvider(FROST_BERRY_BUSH_STATE), new SimpleBlockPlacer()))
            .cannotProject().tries(64).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK)).build();
    
    @Override
    public void onInitialize() {
        ConfigManager.loadConfig();
        GlazerContainer.ensureReady();
        
        IsurFood.registerFoods();
        IsurStatusEffects.registerEffects();
        IsurItems.registerItems();

        // Containers
        ContainerProviderRegistry.INSTANCE.registerFactory(GLAZER_CRAFTING, (syncId, identifier, player, byteBuf) -> new GlazerContainer(syncId, player.inventory));
        
        HoeExtensions.addTillableBlock(IsurBlocks.SOIL, IsurBlocks.SOIL_FARMLAND.getDefaultState());
        
        // Features
        Registry.BIOME.stream().filter(b -> b.getTemperature() <= 0.05f).forEach(b -> b.addFeature(
                GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.RANDOM_PATCH.configure(FROST_BERRY_BUSH_CONFIG).createDecoratedFeature(Decorator.COUNT_HEIGHTMAP_DOUBLE.configure(new CountDecoratorConfig(1)))
        ));
        
        RANDOM_BLOCK_MAPPER = Registry.register(Registry.STRUCTURE_PROCESSOR, "random_block_state_mapper", RandomBlockStateMapperStructureProcessor::new);
    }
}