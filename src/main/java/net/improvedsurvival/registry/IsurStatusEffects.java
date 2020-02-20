package net.improvedsurvival.registry;

import net.improvedsurvival.Isur;
import net.improvedsurvival.status_effects.Chilling;
import net.improvedsurvival.status_effects.CustomStatusEffect;
import net.improvedsurvival.status_effects.Purifying;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class IsurStatusEffects {

    public static StatusEffect COLD;
    public static StatusEffect FROST_BITE;
    public static StatusEffect SWEATING;
    public static StatusEffect HEATSTROKE;
    public static StatusEffect CHILLING;
    public static StatusEffect PURIFYING;

    public static void registerEffects(){
        COLD = register(1507, "cold", (new CustomStatusEffect(StatusEffectType.HARMFUL, 12572927)));
        FROST_BITE = register(1507, "frost_bite", (new CustomStatusEffect(StatusEffectType.HARMFUL, 12572927)));
        SWEATING = register(1507, "sweating", (new CustomStatusEffect(StatusEffectType.HARMFUL, 12572927)));
        HEATSTROKE = register(1507, "heatstroke", (new CustomStatusEffect(StatusEffectType.HARMFUL, 12572927)));
        CHILLING = register(1507, "chilling", (new Chilling(StatusEffectType.NEUTRAL, 12572927)));
        PURIFYING = register(1507, "purifying", (new Purifying(StatusEffectType.BENEFICIAL, 0xffffff)));
    }

    private static StatusEffect register(int rawId, String id, StatusEffect entry) {
        return (StatusEffect)Registry.register(Registry.STATUS_EFFECT, new Identifier(Isur.MODID, id), entry);
    }
}