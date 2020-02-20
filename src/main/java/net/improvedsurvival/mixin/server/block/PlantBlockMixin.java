package net.improvedsurvival.mixin.server.block;

import net.improvedsurvival.registry.IsurBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlantBlock.class)
public class PlantBlockMixin {
	@Inject(at = @At("HEAD"), method = "canPlantOnTop", cancellable = true)
	public void canPlantOnTop(BlockState floor, BlockView view, BlockPos pos, CallbackInfoReturnable<Boolean> callbackInfo) {
		if(floor.getBlock() == IsurBlocks.SOIL)
			callbackInfo.setReturnValue(true);
	}
}