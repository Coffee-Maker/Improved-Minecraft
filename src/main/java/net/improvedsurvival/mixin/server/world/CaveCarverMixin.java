package net.improvedsurvival.mixin.server.world;

import net.minecraft.world.gen.carver.CaveCarver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(CaveCarver.class)
public class CaveCarverMixin {
	@ModifyConstant(method = "getCaveY", constant = { @Constant(intValue = 120) })
	public int getCaveY(int variable) {
		return 248;
	}
	
	@ModifyConstant(method = "getMaxCaveCount", constant = { @Constant(intValue = 15) })
	public int getMaxCaveCount(int variable) {
		return 25;
	}
	
	@Inject(at = @At("HEAD"), method = "getTunnelSystemWidth", cancellable = true)
	public void getTunnelSystemWidth(Random random, CallbackInfoReturnable<Float> cir) {
		cir.setReturnValue(((float) Math.pow(random.nextDouble(), 2) * 9.5f) + 0.5f);
	}
	
	@ModifyConstant(method = "getTunnelSystemHeightWidthRatio", constant = { @Constant(doubleValue = 1.0d) })
	public double getTunnelSystemHeightWidthRatio(double variable) {
		return 0.7d + (new Random()).nextFloat() * 0.6d;
	}
}