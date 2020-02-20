package net.improvedsurvival.mixin.server.entity;

import com.google.gson.JsonObject;
import net.improvedsurvival.config.json.ConfigManager;
import net.improvedsurvival.containers.GlazerContainer;
import net.improvedsurvival.containers.GlazingObject;
import net.improvedsurvival.interfaces.IEnvironmentalEntity;
import net.improvedsurvival.items.TemperatureArmorMaterial;
import net.improvedsurvival.registry.IsurStatusEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(LivingEntity.class)
public class LivingEntityMixin implements IEnvironmentalEntity {
	private static Map<DimensionType, Float> DIMENSION_TEMPERATURES;
	
	private float temperature = 50;
	private long lastTemperatureDamageTime;
	
	@Shadow protected ItemStack activeItemStack;
	
	@Inject(at = @At("HEAD"), method = "consumeItem")
	protected void consumeItem(CallbackInfo callbackInfo) {
		LivingEntity entity = ((LivingEntity) (Object) this);
		if(!entity.world.isClient) {
			ListTag t = activeItemStack.getOrCreateTag().getList("Glazings", 8);
			if(t != null) {
				for(Tag tag : t) {
					String glazing = tag.asString();
					if(glazing != null && !glazing.isEmpty()) {
						GlazerContainer.ensureReady();
						GlazingObject g = GlazerContainer.GLAZINGS.get(glazing);
						if(g != null) {
							g.applyEffect(entity);
						}
					}
				}
			}
			
			if(!entity.world.isClient) {
				if(PotionUtil.getPotion(activeItemStack).equals(Potions.WATER)) {
					addTemperature(temperature > 60 ? -15 : 0);
				}
			}
		}
	}
	
	@Inject(at = @At("HEAD"), method = "tickStatusEffects")
	private void tickStatusEffects(CallbackInfo callbackInfo) {
		LivingEntity entity = (LivingEntity) (Object) this;
		if(entity instanceof PlayerEntity && !entity.world.isClient) {
			float dimensionTemp = getDimensionTemp(entity.getBlockPos(), entity.world);
			float armorTemp = 0;
			float heatRentetion = 0;
			float coldRetention = 0;
			
			Iterable<ItemStack> equippedItems = entity.getItemsEquipped();
			
			for(ItemStack i : equippedItems) {
				if(i.getItem() instanceof ArmorItem) {
					ArmorItem a = (ArmorItem) i.getItem();
					if(a.getMaterial() instanceof TemperatureArmorMaterial) {
						armorTemp += ((TemperatureArmorMaterial) a.getMaterial()).getTemperature();
						heatRentetion += ((TemperatureArmorMaterial) a.getMaterial()).getHeatRetention();
						coldRetention += ((TemperatureArmorMaterial) a.getMaterial()).getColdRetention();
					}
				}
			}
			
			float armorTarget = 1 - MathHelper.clamp(temperature / armorTemp, 0, 1);
			float targetTemp = dimensionTemp + (armorTemp * armorTarget);
			float temperatureShiftSpeed = 0.025f * (targetTemp < temperature ? 1 - heatRentetion : 1 - coldRetention);
			
			temperature = MathHelper.lerp(temperatureShiftSpeed, temperature, targetTemp);
			temperature = MathHelper.clamp(temperature, -100, 100);
			
			// Cold
			if(temperature < 40 && temperature > 25) {
				if(entity.world.getTime() - lastTemperatureDamageTime > 50) {
					lastTemperatureDamageTime = entity.world.getTime();
					entity.addStatusEffect(new StatusEffectInstance(IsurStatusEffects.COLD, 1000, 0, false, false, true));
				}
			} else if(temperature > 40 && entity.hasStatusEffect(IsurStatusEffects.COLD) || temperature < 25 && entity.hasStatusEffect(IsurStatusEffects.COLD)) {
				entity.removeStatusEffect(IsurStatusEffects.COLD);
				lastTemperatureDamageTime = 0;
			}
			
			// Frost bite
			if(temperature < 25) {
				if(entity.world.getTime() - lastTemperatureDamageTime > 50) {
					entity.damage(DamageSource.GENERIC, 2);
					lastTemperatureDamageTime = entity.world.getTime();
					entity.addStatusEffect(new StatusEffectInstance(IsurStatusEffects.FROST_BITE, 1000, 0, false, false, true));
				}
			} else if(temperature > 25 && entity.hasStatusEffect(IsurStatusEffects.FROST_BITE)) {
				entity.removeStatusEffect(IsurStatusEffects.FROST_BITE);
				lastTemperatureDamageTime = 0;
			}
			
			// Sweating
			if(temperature > 65 && temperature < 85) {
				if(entity.world.getTime() - lastTemperatureDamageTime > 50) {
					lastTemperatureDamageTime = entity.world.getTime();
					entity.addStatusEffect(new StatusEffectInstance(IsurStatusEffects.SWEATING, 1000, 0, false, false, true));
				}
			} else if(temperature < 65 && entity.hasStatusEffect(IsurStatusEffects.SWEATING) || temperature > 85 && entity.hasStatusEffect(IsurStatusEffects.SWEATING)) {
				entity.removeStatusEffect(IsurStatusEffects.SWEATING);
				lastTemperatureDamageTime = 0;
			}
			
			// Heatstroke
			if(temperature > 85) {
				if(entity.world.getTime() - lastTemperatureDamageTime > 50) {
					entity.damage(DamageSource.GENERIC, 2);
					lastTemperatureDamageTime = entity.world.getTime();
					entity.addStatusEffect(new StatusEffectInstance(IsurStatusEffects.HEATSTROKE, 1000, 0, false, false, true));
				}
			} else if(temperature < 85 && entity.hasStatusEffect(IsurStatusEffects.HEATSTROKE)) {
				entity.removeStatusEffect(IsurStatusEffects.HEATSTROKE);
				lastTemperatureDamageTime = 0;
			}
		}
	}
	
	private float getDimensionTemp(BlockPos pos, World world) {
		if(DIMENSION_TEMPERATURES == null) {
			DIMENSION_TEMPERATURES = new HashMap<>();
			ConfigManager.getArray("dimensionTemperatures").forEach(b -> {
				JsonObject j = (JsonObject) b;
				float t = j.get("temperature").getAsFloat();
				DimensionType biome = Registry.DIMENSION_TYPE.get(new Identifier(j.get("biome").getAsString()));
				DIMENSION_TEMPERATURES.put(biome, t);
			});
		}
		
		DimensionType b = world.getDimension().getType();
		if(DIMENSION_TEMPERATURES.containsKey(b))
			return DIMENSION_TEMPERATURES.get(b);
		return 50f;
	}
	
	@Override
	public void addTemperature(final float amount) {
		temperature += amount;
		temperature = MathHelper.clamp(temperature, -100, 100);
	}
	
	@Override
	public float getTemperature() {
		return temperature;
	}
	
	@Override
	public void setTemperature(float amount) {
		temperature = amount;
		temperature = MathHelper.clamp(temperature, -100, 100);
	}
}