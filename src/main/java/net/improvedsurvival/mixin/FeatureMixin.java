package net.improvedsurvival.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.improvedsurvival.Isur;
import net.minecraft.block.Block;
import net.minecraft.world.gen.feature.Feature;

@Mixin(Feature.class)
public class FeatureMixin {
    @Inject(at = @At("HEAD"), method = "isDirt", cancellable = true)
    private static void isDirt(Block block, CallbackInfoReturnable<Boolean> callbackInfo){
        if(block == Isur.SOIL || block == Isur.SOIL_FARMLAND)
            callbackInfo.setReturnValue(true);
    }
}