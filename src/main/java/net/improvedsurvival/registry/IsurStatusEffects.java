package net.improvedsurvival.registry;

import net.improvedsurvival.Isur;
import net.improvedsurvival.status_effects.Chilling;
import net.improvedsurvival.status_effects.CustomStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class IsurStatusEffects {
	public static final StatusEffect COLD = register(1507, "cold", (new CustomStatusEffect(StatusEffectType.HARMFUL, 12572927)));
	public static final StatusEffect FROST_BITE = register(1507, "frost_bite", (new CustomStatusEffect(StatusEffectType.HARMFUL, 12572927)));
	public static final StatusEffect SWEATING = register(1507, "sweating", (new CustomStatusEffect(StatusEffectType.HARMFUL, 12572927)));
	public static final StatusEffect HEATSTROKE = register(1507, "heatstroke", (new CustomStatusEffect(StatusEffectType.HARMFUL, 12572927)));
	public static final StatusEffect CHILLING = register(1507, "chilling", (new Chilling(StatusEffectType.NEUTRAL, 12572927)));
	
	private static StatusEffect register(int rawId, String id, StatusEffect entry) {
		return Registry.register(Registry.STATUS_EFFECT, new Identifier(Isur.MODID, id), entry);
	}
}