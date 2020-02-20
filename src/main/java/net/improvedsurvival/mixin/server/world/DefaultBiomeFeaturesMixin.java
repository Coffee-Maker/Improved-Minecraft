package net.improvedsurvival.mixin.server.world;

import net.improvedsurvival.Isur;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(DefaultBiomeFeatures.class)
public class DefaultBiomeFeaturesMixin {
	private static final int NEW_COAL_COUNT = 40;
	private static final int NEW_COAL_MAXIMUM = 256;
	private static final int NEW_IRON_COUNT = 30;
	private static final int NEW_IRON_MAXIMUM = 100;
	private static final int NEW_GOLD_COUNT = 3;
	private static final int NEW_GOLD_MAXIMUM = 45;
	private static final int NEW_REDSTONE_COUNT = 8;
	private static final int NEW_REDSTONE_MAXIMUM = 16;
	private static final int NEW_DIAMOND_COUNT = 1;
	private static final int NEW_DIAMOND_MAXIMUM = 16;
	private static final int NEW_LAPIS_COUNT = 1;
	private static final int NEW_LAPIS_BASELINE = 16;
	private static final int NEW_LAPIS_SPREAD = 16;
	
	@ModifyArgs(
			method = "addDefaultOres",
			slice = @Slice(
					from = @At(value = "FIELD", target = "Lnet/minecraft/world/biome/DefaultBiomeFeatures;COAL_ORE:Lnet/minecraft/block/BlockState;", opcode = Opcodes.GETSTATIC),
					to = @At(value = "FIELD", target = "Lnet/minecraft/world/biome/DefaultBiomeFeatures;IRON_ORE:Lnet/minecraft/block/BlockState;", opcode = Opcodes.GETSTATIC)
			),
			at = @At(
					ordinal = 0,
					value = "INVOKE",
					target = "Lnet/minecraft/world/gen/decorator/RangeDecoratorConfig;<init>(IIII)V"
			)
	)
	private static void fixCoalDecoratorConfig(Args args) {
		args.setAll(NEW_COAL_COUNT * 200, args.get(1), args.get(2), NEW_COAL_MAXIMUM);
	}
	
	@ModifyArgs(
			method = "addDefaultOres",
			slice = @Slice(
					from = @At(value = "FIELD", target = "Lnet/minecraft/world/biome/DefaultBiomeFeatures;IRON_ORE:Lnet/minecraft/block/BlockState;", opcode = Opcodes.GETSTATIC),
					to = @At(value = "FIELD", target = "Lnet/minecraft/world/biome/DefaultBiomeFeatures;GOLD_ORE:Lnet/minecraft/block/BlockState;", opcode = Opcodes.GETSTATIC)
			),
			at = @At(
					ordinal = 0,
					value = "INVOKE",
					target = "Lnet/minecraft/world/gen/decorator/RangeDecoratorConfig;<init>(IIII)V"
			)
	)
	private static void fixIronDecoratorConfig(Args args) {
		args.setAll(NEW_IRON_COUNT, args.get(1), args.get(2), NEW_IRON_MAXIMUM);
	}
	
	@ModifyArgs(
			method = "addDefaultOres",
			slice = @Slice(
					from = @At(value = "FIELD", target = "Lnet/minecraft/world/biome/DefaultBiomeFeatures;GOLD_ORE:Lnet/minecraft/block/BlockState;", opcode = Opcodes.GETSTATIC),
					to = @At(value = "FIELD", target = "Lnet/minecraft/world/biome/DefaultBiomeFeatures;REDSTONE_ORE:Lnet/minecraft/block/BlockState;", opcode = Opcodes.GETSTATIC)
			),
			at = @At(
					ordinal = 0,
					value = "INVOKE",
					target = "Lnet/minecraft/world/gen/decorator/RangeDecoratorConfig;<init>(IIII)V"
			)
	)
	private static void fixGoldDecoratorConfig(Args args) {
		args.setAll(NEW_GOLD_COUNT, args.get(1), args.get(2), NEW_GOLD_MAXIMUM);
	}
	
	@ModifyArgs(
			method = "addDefaultOres",
			slice = @Slice(
					from = @At(value = "FIELD", target = "Lnet/minecraft/world/biome/DefaultBiomeFeatures;REDSTONE_ORE:Lnet/minecraft/block/BlockState;", opcode = Opcodes.GETSTATIC),
					to = @At(value = "FIELD", target = "Lnet/minecraft/world/biome/DefaultBiomeFeatures;DIAMOND_ORE:Lnet/minecraft/block/BlockState;", opcode = Opcodes.GETSTATIC)
			),
			at = @At(
					ordinal = 0,
					value = "INVOKE",
					target = "Lnet/minecraft/world/gen/decorator/RangeDecoratorConfig;<init>(IIII)V"
			)
	)
	private static void fixRedstoneDecoratorConfig(Args args) {
		args.setAll(NEW_REDSTONE_COUNT, args.get(1), args.get(2), NEW_REDSTONE_MAXIMUM);
	}
	
	@ModifyArgs(
			method = "addDefaultOres",
			slice = @Slice(
					from = @At(value = "FIELD", target = "Lnet/minecraft/world/biome/DefaultBiomeFeatures;DIAMOND_ORE:Lnet/minecraft/block/BlockState;", opcode = Opcodes.GETSTATIC),
					to = @At(value = "FIELD", target = "Lnet/minecraft/world/biome/DefaultBiomeFeatures;LAPIS_ORE:Lnet/minecraft/block/BlockState;", opcode = Opcodes.GETSTATIC)
			),
			at = @At(
					ordinal = 0,
					value = "INVOKE",
					target = "Lnet/minecraft/world/gen/decorator/RangeDecoratorConfig;<init>(IIII)V"
			)
	)
	private static void fixDiamondDecoratorConfig(Args args) {
		args.setAll(NEW_DIAMOND_COUNT, args.get(1), args.get(2), NEW_DIAMOND_MAXIMUM);
	}
	
	@ModifyArgs(
			method = "addDefaultOres",
			slice = @Slice(
					from = @At(value = "FIELD", target = "Lnet/minecraft/world/biome/DefaultBiomeFeatures;LAPIS_ORE:Lnet/minecraft/block/BlockState;", opcode = Opcodes.GETSTATIC)
			),
			at = @At(
					ordinal = 0,
					value = "INVOKE",
					target = "Lnet/minecraft/world/gen/decorator/CountDepthDecoratorConfig;<init>(III)V"
			)
	)
	private static void fixLapisDecoratorConfig(Args args) {
		args.setAll(NEW_LAPIS_COUNT, NEW_LAPIS_BASELINE, NEW_LAPIS_SPREAD);
	}
	
	
	@ModifyArgs(
			method = "addMineables",
			slice = @Slice(
					from = @At(value = "FIELD", target = "Lnet/minecraft/world/biome/DefaultBiomeFeatures;GRANITE:Lnet/minecraft/block/BlockState;", opcode = Opcodes.GETSTATIC),
					to = @At(value = "FIELD", target = "Lnet/minecraft/world/biome/DefaultBiomeFeatures;DIORITE:Lnet/minecraft/block/BlockState;", opcode = Opcodes.GETSTATIC)
			),
			at = @At(
					ordinal = 0,
					value = "INVOKE",
					target = "Lnet/minecraft/world/gen/decorator/RangeDecoratorConfig;<init>(IIII)V"
			)
	)
	private static void fixGraniteDecoratorConfig(Args args) {
		args.setAll(10, 0, 0, Isur.seaLevel + 17);
	}
	
	@ModifyArgs(
			method = "addMineables",
			slice = @Slice(
					from = @At(value = "FIELD", target = "Lnet/minecraft/world/biome/DefaultBiomeFeatures;DIORITE:Lnet/minecraft/block/BlockState;", opcode = Opcodes.GETSTATIC),
					to = @At(value = "FIELD", target = "Lnet/minecraft/world/biome/DefaultBiomeFeatures;ANDESITE:Lnet/minecraft/block/BlockState;", opcode = Opcodes.GETSTATIC)
			),
			at = @At(
					ordinal = 0,
					value = "INVOKE",
					target = "Lnet/minecraft/world/gen/decorator/RangeDecoratorConfig;<init>(IIII)V"
			)
	)
	private static void fixDioriteDecoratorConfig(Args args) {
		args.setAll(10, 0, 0, Isur.seaLevel + 17);
	}
	
	@ModifyArgs(
			method = "addMineables",
			slice = @Slice(
					from = @At(value = "FIELD", target = "Lnet/minecraft/world/biome/DefaultBiomeFeatures;ANDESITE:Lnet/minecraft/block/BlockState;", opcode = Opcodes.GETSTATIC)
			),
			at = @At(
					ordinal = 0,
					value = "INVOKE",
					target = "Lnet/minecraft/world/gen/decorator/RangeDecoratorConfig;<init>(IIII)V"
			)
	)
	private static void fixAndesiteDecoratorConfig(Args args) {
		args.setAll(10, 0, 0, Isur.seaLevel + 17);
	}
}