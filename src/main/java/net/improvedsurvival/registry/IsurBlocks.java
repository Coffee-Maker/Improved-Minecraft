package net.improvedsurvival.registry;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tools.FabricToolTags;
import net.improvedsurvival.Isur;
import net.improvedsurvival.blocks.*;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class IsurBlocks {
	
	public static final Block SOIL_FARMLAND = register(
			"soil_farmland", new SoilFarmland(FabricBlockSettings.of(Material.EARTH).ticksRandomly().hardness(0.5f).breakByTool(FabricToolTags.SHOVELS).build()));
	public static final Block GLAZER = register("glazer", new Glazer(FabricBlockSettings.of(Material.WOOD).hardness(2.5f).breakByTool(FabricToolTags.AXES).breakByHand(true).build()));
	public static final Block SOIL = register("soil", new Soil(FabricBlockSettings.of(Material.EARTH).ticksRandomly().hardness(0.5f).breakByTool(FabricToolTags.SHOVELS).build()));
	public static final Block FROST_BERRY_BUSH = register(
			"frost_berry_bush", new FrostBerryBush(FabricBlockSettings.of(Material.PLANT).ticksRandomly().noCollision().sounds(BlockSoundGroup.SWEET_BERRY_BUSH).build()));
	public static final Block END_GROUND = register("end_ground", new Block(FabricBlockSettings.of(Material.STONE).hardness(1.5f).resistance(6f).breakByTool(FabricToolTags.PICKAXES).build()));
	public static final Block PALM_PLANKS = register(
			"palm_planks", new Block(FabricBlockSettings.of(Material.WOOD).sounds(BlockSoundGroup.WOOD).hardness(2).resistance(2).breakByTool(FabricToolTags.AXES).build()));
	public static final Block PALM_LOG = register("palm_log", new LogBlock(MaterialColor.WHITE, Block.Settings.copy(PALM_PLANKS)));
	public static final Block PALM_WOOD = register("palm_wood", new LogBlock(MaterialColor.WHITE, Block.Settings.copy(PALM_PLANKS)));
	public static final Block PALM_DOOR = register("palm_door", new IsurDoorBlock(FabricBlockSettings.of(Material.WOOD).hardness(3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque().build()));
	public static final Block STRIPPED_PALM_LOG = register("stripped_palm_log", new LogBlock(MaterialColor.WHITE, Block.Settings.copy(PALM_PLANKS)));
	public static final Block STRIPPED_PALM_WOOD = register("stripped_palm_wood", new LogBlock(MaterialColor.WHITE, Block.Settings.copy(PALM_PLANKS)));
	public static final Block PALM_STAIRS = register("palm_stairs", new IsurStairsBlock(PALM_PLANKS.getDefaultState(), Block.Settings.copy(PALM_PLANKS)));
	public static final Block PALM_SLAB = register("palm_slab", new SlabBlock(Block.Settings.copy(PALM_PLANKS)));
	public static final Block PALM_FENCE = register("palm_fence", new FenceBlock(Block.Settings.copy(PALM_PLANKS)));
	public static final Block PALM_FENCE_GATE = register("palm_fence_gate", new FenceGateBlock(Block.Settings.copy(PALM_PLANKS)));
	public static final Block PALM_BUTTON = register("palm_button", new IsurWoodButtonBlock(FabricBlockSettings.of(Material.PART).noCollision().hardness(0.5F).sounds(BlockSoundGroup.WOOD).build()));
	public static final Block PALM_PRESSURE_PLATE = register(
			"palm_pressure_plate",
			new IsurPressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, FabricBlockSettings.of(Material.WOOD).noCollision().hardness(0.5F).sounds(BlockSoundGroup.WOOD).build())
	);
	public static final Block PALM_LEAVES = register(
			"palm_leaves", new LeavesBlock(FabricBlockSettings.of(Material.LEAVES).hardness(0.2F).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque().build()));
	
	private static Block register(String id, Block block) {
		Registry.register(Registry.ITEM, new Identifier(Isur.MODID, id), new BlockItem(block, new Item.Settings().group(Isur.ISUR)));
		return Registry.register(Registry.BLOCK, new Identifier(Isur.MODID, id), block);
	}
}