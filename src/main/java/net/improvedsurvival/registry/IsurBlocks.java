package net.improvedsurvival.registry;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tools.FabricToolTags;
import net.improvedsurvival.Isur;
import net.improvedsurvival.blocks.FrostBerryBush;
import net.improvedsurvival.blocks.Glazer;
import net.improvedsurvival.blocks.Soil;
import net.improvedsurvival.blocks.SoilFarmland;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.PillarBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class IsurBlocks {

    public static final Block SOIL_FARMLAND = register("soil_farmland", new SoilFarmland(FabricBlockSettings.of(Material.EARTH).ticksRandomly().hardness(0.5f).breakByTool(FabricToolTags.SHOVELS).build()));
    public static final Block GLAZER = register("glazer", new Glazer(FabricBlockSettings.of(Material.WOOD).hardness(2.5f).breakByTool(FabricToolTags.AXES).breakByHand(true).build()));
    public static final Block SOIL = register("soil", new Soil(FabricBlockSettings.of(Material.EARTH).ticksRandomly().hardness(0.5f).breakByTool(FabricToolTags.SHOVELS).build()));
    public static final Block FROST_BERRY_BUSH = register("frost_berry_bush", new FrostBerryBush(FabricBlockSettings.of(Material.PLANT).ticksRandomly().noCollision().sounds(BlockSoundGroup.SWEET_BERRY_BUSH).build()));
    public static final Block END_GROUND = register("end_ground", new Block(FabricBlockSettings.of(Material.STONE).hardness(1.5f).resistance(6f).breakByTool(FabricToolTags.PICKAXES).build()));
    public static final Block PALM_LOG = register("palm_log", new PillarBlock(FabricBlockSettings.of(Material.WOOD).sounds(BlockSoundGroup.WOOD).hardness(2).resistance(2).breakByTool(FabricToolTags.AXES).build()));
    public static final Block PALM_PLANKS = register("palm_planks", new Block(FabricBlockSettings.of(Material.WOOD).sounds(BlockSoundGroup.WOOD).hardness(2).resistance(2).breakByTool(FabricToolTags.AXES).build()));
    
    private static Block register(String id, Block block) {
        Registry.register(Registry.ITEM, new Identifier(Isur.MODID, id), new BlockItem(block, new Item.Settings().group(Isur.ISUR)));
        return (Block)Registry.register(Registry.BLOCK, new Identifier(Isur.MODID, id), block);
     }
}