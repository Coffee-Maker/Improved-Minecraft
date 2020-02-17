package net.improvedsurvival.mixin.server.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.player.PlayerEntity;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin{
    @Inject(at = @At("HEAD"), method = "canConsume", cancellable = true)
    public void canConsume(boolean ignoreHunger, CallbackInfoReturnable<Boolean> callbackInfo){
        if(((PlayerEntity)(Object)this).getMainHandStack().getOrCreateTag().getBoolean("AlwaysEdible"))
            callbackInfo.setReturnValue(true);
    }
}