package net.improvedsurvival.mixin.server.world;

import net.improvedsurvival.Isur;
import net.improvedsurvival.interfaces.IStructurePlacementDataReplaceBlocks;
import net.improvedsurvival.util.RandomBlockStateMapperStructureProcessor;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.processor.BlockIgnoreStructureProcessor;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.feature.BranchedTreeFeatureConfig;
import net.minecraft.world.gen.feature.OakTreeFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mixin(OakTreeFeature.class)
public class OakTreeFeatureMixin {
	private static final Map<Identifier, Integer> TREES = Stream.of(
			new SimpleEntry<>(new Identifier(Isur.MODID, "oak_tree/oak_tree_0"), 0),
			new SimpleEntry<>(new Identifier(Isur.MODID, "oak_tree/oak_tree_1"), 0),
			new SimpleEntry<>(new Identifier(Isur.MODID, "oak_tree/oak_tree_2"), 0),
			new SimpleEntry<>(new Identifier(Isur.MODID, "oak_tree/oak_tree_3"), 0),
			new SimpleEntry<>(new Identifier(Isur.MODID, "oak_tree/oak_tree_4"), 0),
			new SimpleEntry<>(new Identifier(Isur.MODID, "oak_tree/oak_tree_5"), 0),
			new SimpleEntry<>(new Identifier(Isur.MODID, "oak_tree/oak_bush_0"), -1),
			new SimpleEntry<>(new Identifier(Isur.MODID, "oak_tree/oak_bush_1"), -1)
	).collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));
	
	@Inject(method = "generate", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/foliage/FoliagePlacer;generate(Lnet/minecraft/world/ModifiableTestableWorld;Ljava/util/Random;Lnet/minecraft/world/gen/feature/BranchedTreeFeatureConfig;IIILnet/minecraft/util/math/BlockPos;Ljava/util/Set;)V"), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
	public void generate(ModifiableTestableWorld modifiableTestableWorld, Random random, BlockPos blockPos, Set<BlockPos> set, Set<BlockPos> set2, BlockBox blockBox, BranchedTreeFeatureConfig branchedTreeFeatureConfig, CallbackInfoReturnable<Boolean> cir, int i, int j, int k, Optional<BlockPos> optional, BlockPos generationPos) {
		IWorld iWorld = ((IWorld) modifiableTestableWorld);
		Random random2 = iWorld.getRandom();
		
		BlockRotation[] blockRotations = BlockRotation.values();
		BlockRotation blockRotation = blockRotations[random2.nextInt(blockRotations.length)];
		
		Set<Identifier> treeIdentifierSet = TREES.keySet();
		Identifier[] treeIdentifiers = treeIdentifierSet.toArray(new Identifier[0]);
		Identifier treeIdentifier = treeIdentifiers[random2.nextInt(treeIdentifiers.length)];
		
		StructureManager manager = ((ServerWorld) iWorld.getWorld()).getSaveHandler().getStructureManager();
		Structure structure = manager.getStructureOrBlank(treeIdentifier);
		
		//iWorld.setBlockState(generationPos, Blocks.AIR.getDefaultState(), 10);
		for(int f = 0; f < 128; f++) {
			iWorld.setBlockState(generationPos, Blocks.AIR.getDefaultState(), f);
		}
		
		StructurePlacementData structurePlacementData = ((IStructurePlacementDataReplaceBlocks) new StructurePlacementData())
				.setReplaceBlocks(false)
				.setPosition(new BlockPos(structure.getSize().getX() / 2, 0, structure.getSize().getZ() / 2)) // is called setPosition, but really sets the rotation pivot
				.setRotation(blockRotation)
				.setRandom(random2)
				.addProcessor(BlockIgnoreStructureProcessor.IGNORE_AIR_AND_STRUCTURE_BLOCKS)
				.addProcessor(new RandomBlockStateMapperStructureProcessor(Blocks.LIME_CONCRETE.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState(), 0.9f, Blocks.AIR.getDefaultState()));
		
		structure.method_15172(iWorld, generationPos.add(structurePlacementData.getPosition().rotate(BlockRotation.CLOCKWISE_180)).up(TREES.get(treeIdentifier)), structurePlacementData, 2);
		
		cir.setReturnValue(true);
	}
}