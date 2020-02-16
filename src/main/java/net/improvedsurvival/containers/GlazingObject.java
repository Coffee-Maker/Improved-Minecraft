package net.improvedsurvival.containers;

import net.improvedsurvival.interfaces.IEnvironmentalEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;

public class GlazingObject {

   public enum GlazingInstantEffects{
      SATURATION,
      TEMPERATURE
   }

   public StatusEffect effect;
   public int duration;
   public int amplifier;
   public GlazingInstantEffects instantEffect;
   public int instantEffectAmount;
   public int color;

   public GlazingObject(StatusEffect effect, int duration, int amplifier, int color){
      this.effect = effect;
      this.duration = duration;
      this.amplifier = amplifier;
      this.color = color;
   }

   public GlazingObject(int instantEffectAmount, GlazingInstantEffects instantEffectType, int color){
      this.instantEffectAmount = instantEffectAmount;
      this.instantEffect = instantEffectType;
      this.color = color;
   }

   public void applyEffect(LivingEntity entity){
      if(effect != null)
         entity.addStatusEffect(new StatusEffectInstance(effect, duration, amplifier));
      else{
         switch(instantEffect){
            case SATURATION:
               ((PlayerEntity)entity).getHungerManager().add(amplifier + 1, 1.0F);
               break;
            case TEMPERATURE:
               ((IEnvironmentalEntity)entity).addTemperature(instantEffectAmount);
               break;
         }
      }
   }
}