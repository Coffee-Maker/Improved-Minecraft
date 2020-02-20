package net.improvedsurvival.status_effects;

import net.improvedsurvival.interfaces.IEnvironmentalEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.util.math.MathHelper;

public class Chilling extends CustomStatusEffect {
	
	public Chilling(StatusEffectType type, int color) {
		super(type, color);
	}
	
	@Override
	public void applyUpdateEffect(LivingEntity entity, int amplifier) {
		if(entity.world.isClient)
			return;
		if(entity instanceof IEnvironmentalEntity) {
			IEnvironmentalEntity ee = ((IEnvironmentalEntity) (entity));
			ee.addTemperature((MathHelper.clamp(ee.getTemperature(), 30, 100) - 30) * -0.075f);
		}
	}
	
	@Override
	public boolean canApplyUpdateEffect(int duration, int amplifier) {
		int k = 8 >> amplifier;
		if(k > 0)
			return duration % k == 0;
		return false;
	}
}