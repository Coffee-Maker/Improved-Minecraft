package net.improvedsurvival.mixin.server.world;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.improvedsurvival.registry.IsurBlocks;
import net.minecraft.block.Block;
import net.minecraft.world.gen.feature.Feature;

@Mixin(Feature.class)
public class FeatureMixin {
    @Inject(at = @At("HEAD"), method = "isDirt", cancellable = true)
    private static void isDirt(Block block, CallbackInfoReturnable<Boolean> callbackInfo){
        if(block == IsurBlocks.SOIL || block == IsurBlocks.SOIL_FARMLAND)
            callbackInfo.setReturnValue(true);
    }
}