package net.improvedsurvival.status_effects;

import java.util.Map;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectType;

public class Purifying extends CustomStatusEffect {

    public Purifying(StatusEffectType type, int color) {
        super(type, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if(entity.world.isClient)
            return;
        
        Map<StatusEffect, StatusEffectInstance> effects = entity.getActiveStatusEffects();

        effects.forEach((e, a) -> {
            if(e.getType() == StatusEffectType.HARMFUL){
                entity.removeStatusEffect(e);
            }
        });
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        int k = 8 >> amplifier;
        if(k > 0)
            return duration % k == 0;
        return false;
    }
}