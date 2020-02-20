package net.improvedsurvival.mixin.server.world;

import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Biome.Settings.class)
public class Biome$SettingsMixin {
	@Shadow private Float depth;
	
	@Inject(method = "depth", at = @At("HEAD"), cancellable = true)
	public void depth(float depth, CallbackInfoReturnable<Biome.Settings> cir) {
		this.depth = depth + 3.7f;
		cir.setReturnValue((Biome.Settings) (Object) this);
	}
}