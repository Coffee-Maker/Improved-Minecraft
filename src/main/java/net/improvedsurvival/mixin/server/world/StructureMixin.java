package net.improvedsurvival.mixin.server.world;

import net.improvedsurvival.interfaces.IStructurePlacementDataReplaceBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Structure.class)
public class StructureMixin {
	// This targets the second call, even though we want to redirect the third one in the deobfuscated code. This is due to the code not perfectly resembling bytecode structure. This might break in a future update.
	@Redirect(
			method = "method_15172",
			slice = @Slice(
					from = @At(value = "INVOKE", target = "Lnet/minecraft/util/Clearable;clear(Ljava/lang/Object;)V"),
					to = @At(value = "INVOKE", target = "Ljava/lang/Math;min(II)I")
			),
			at = @At(ordinal = 1, value = "INVOKE", target = "Lnet/minecraft/world/IWorld;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z")
	)
	private boolean redirectSetBlockState(IWorld world, BlockPos pos, BlockState state, int flags, IWorld iWorld, BlockPos blockPos, StructurePlacementData structurePlacementData, int i) {
		return (((IStructurePlacementDataReplaceBlocks) structurePlacementData).getReplaceBlocks() || iWorld.getBlockState(pos) == Blocks.AIR.getDefaultState()) &&
			   world.setBlockState(pos, state, flags);
	}
}