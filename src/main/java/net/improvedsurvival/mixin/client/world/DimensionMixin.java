package net.improvedsurvival.mixin.client.world;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.dimension.Dimension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Dimension.class)
public class DimensionMixin {
	@Environment(EnvType.CLIENT)
	@Inject(method = "getCloudHeight", at = @At("HEAD"), cancellable = true)
	public void getCloudHeight(CallbackInfoReturnable<Float> cir) {
		cir.setReturnValue(260.0f);
	}
}