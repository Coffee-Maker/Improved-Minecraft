package net.improvedsurvival.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.At;

import net.improvedsurvival.Isur;
import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

@Mixin(PlantBlock.class)
public class PlantBlockMixin{
    @Inject(at = @At("HEAD"), method = "canPlantOnTop", cancellable = true)
    public void canPlantOnTop(BlockState floor, BlockView view, BlockPos pos, CallbackInfoReturnable<Boolean> callbackInfo) {
        if(floor.getBlock() == Isur.SOIL)
            callbackInfo.setReturnValue(true);
    }
}