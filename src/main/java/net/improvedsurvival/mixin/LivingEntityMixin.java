package net.improvedsurvival.mixin;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonObject;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.improvedsurvival.IsurMath;
import net.improvedsurvival.Isur;
import net.improvedsurvival.HeatSource;
import net.improvedsurvival.interfaces.IEnvironmentalEntity;
import net.improvedsurvival.items.TemperatureArmorMaterial;
import net.improvedsurvival.config.json.ConfigManager;
import net.improvedsurvival.containers.GlazerContainer;
import net.improvedsurvival.containers.GlazingObject;
import net.improvedsurvival.utility.BiomeTemp;
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
import net.minecraft.world.biome.Biome;

@Mixin(LivingEntity.class)
public class LivingEntityMixin implements IEnvironmentalEntity {
    
    private static Map<Biome, BiomeTemp> BIOME_TEMPERATURES;

    private float temperature = 50;
    private float wetness;
    private long lastTemperatureDamageTime;

    @Shadow
    private ItemStack activeItemStack;

    @Inject(at = @At("HEAD"), method = "consumeItem")
    protected void consumeItem(CallbackInfo callbackInfo) {
        LivingEntity entity = ((LivingEntity)(Object)this);
        if(!entity.world.isClient){
            ListTag t = activeItemStack.getOrCreateTag().getList("Glazings", 8);
            if(t != null){
                for(Tag tag : t){
                    String glazing = tag.asString();
                    if(glazing != null && !glazing.isEmpty()){
                        GlazerContainer.ensureReady();
                        GlazingObject g = GlazerContainer.GLAZINGS.get(glazing);
                        if(g != null){
                            g.applyEffect(entity);
                        }
                    }
                }
            }
            
            if(!entity.world.isClient){
                if(PotionUtil.getPotion(activeItemStack).equals(Potions.WATER)){
                    addTemperature(temperature > 60 ? -15 : 0);
                }
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "tickStatusEffects")
    private void tickStatusEffects(CallbackInfo callbackInfo) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity instanceof PlayerEntity && !entity.world.isClient) {
            float biomeTemp = getBiomeTemperature(entity.getBlockPos(), entity.world);
            biomeTemp = MathHelper.lerp(MathHelper.clamp((entity.getBlockPos().getY() -30) / 30f, 0f, 1f), 50f, biomeTemp);
            float ambientTemp = biomeTemp;
            float armorTemp = 0;
            float altitudeTemp = IsurMath.multiLerp(entity.getBlockPos().getY() /  32, 45, 15, 0f, -15f, -50f, -100);
            float heatRentetion = 0;
            float coldRetention = 0;
            
            Iterable<ItemStack> equippedItems = entity.getItemsEquipped();
            
            for(ItemStack i : equippedItems){
                if(i.getItem() instanceof ArmorItem){
                    ArmorItem a = (ArmorItem)i.getItem();
                    if(a.getMaterial() instanceof TemperatureArmorMaterial){
                        armorTemp += ((TemperatureArmorMaterial)a.getMaterial()).getTemperature();
                        heatRentetion += ((TemperatureArmorMaterial)a.getMaterial()).getHeatRetention();
                        coldRetention += ((TemperatureArmorMaterial)a.getMaterial()).getColdRetention();
                    }
                }
            }
            
            
            if(entity.world.hasRain(entity.getBlockPos())){
                ambientTemp -= entity.world.getBiome(entity.getBlockPos()).getRainfall() * 15;
            }
            
            if (entity.checkWaterState()) {
                wetness = 100;
            }
            
            float heatSourceTemp = HeatSource.getAccumulatedTemperature(entity.getBlockPos(), entity.world);
            heatSourceTemp -= temperature;
            heatSourceTemp *= 2;
            heatSourceTemp = MathHelper.clamp(heatSourceTemp, 0, 200);

            wetness = MathHelper.lerp(0.0002f * ambientTemp, wetness, 0);
            wetness = MathHelper.clamp(wetness, 0, 100);

            float wetnessCooling = wetness * -0.3f;
            
            float armorTarget = 1 - MathHelper.clamp(temperature / armorTemp, 0, 1);
            float targetTemp = ambientTemp + wetnessCooling + altitudeTemp + heatSourceTemp + (armorTemp * armorTarget);
            float temperatureShiftSpeed = 0.001f * (targetTemp < temperature ? 1-heatRentetion : 1-coldRetention);
            temperatureShiftSpeed *= MathHelper.abs(temperature - targetTemp) / 10;

            temperature = MathHelper.lerp(temperatureShiftSpeed, temperature, targetTemp);
            temperature = MathHelper.clamp(temperature, -100, 100);

            // Cold
            if (temperature < 40 && temperature > 25) {
                if (entity.world.getTime() - lastTemperatureDamageTime > 50) {
                    lastTemperatureDamageTime = entity.world.getTime();
                    entity.addStatusEffect(new StatusEffectInstance(Isur.COLD, 1000, 0, false, false, true));
                }
            } else if (temperature > 40 && entity.hasStatusEffect(Isur.COLD) || temperature < 25 && entity.hasStatusEffect(Isur.COLD)) {
                entity.removeStatusEffect(Isur.COLD);
                lastTemperatureDamageTime = 0;
            }

            // Frost bite
            if (temperature < 25) {
                if (entity.world.getTime() - lastTemperatureDamageTime > 50) {
                    entity.damage(DamageSource.GENERIC, 2);
                    lastTemperatureDamageTime = entity.world.getTime();
                    entity.addStatusEffect(new StatusEffectInstance(Isur.FROST_BITE, 1000, 0, false, false, true));
                }
            } else if (temperature > 25 && entity.hasStatusEffect(Isur.FROST_BITE)) {
                entity.removeStatusEffect(Isur.FROST_BITE);
                lastTemperatureDamageTime = 0;
            }

            // Sweating
            if (temperature > 65 && temperature < 85) {
                if (entity.world.getTime() - lastTemperatureDamageTime > 50) {
                    lastTemperatureDamageTime = entity.world.getTime();
                    entity.addStatusEffect(new StatusEffectInstance(Isur.SWEATING, 1000, 0, false, false, true));
                }
            } else if (temperature < 65 && entity.hasStatusEffect(Isur.SWEATING) || temperature > 85 && entity.hasStatusEffect(Isur.SWEATING)) {
                entity.removeStatusEffect(Isur.SWEATING);
                lastTemperatureDamageTime = 0;
            }

            // Heatstroke
            if (temperature > 85) {
                if (entity.world.getTime() - lastTemperatureDamageTime > 50) {
                    entity.damage(DamageSource.GENERIC, 2);
                    lastTemperatureDamageTime = entity.world.getTime();
                    entity.addStatusEffect(new StatusEffectInstance(Isur.HEATSTROKE, 1000, 0, false, false, true));
                }
            } else if (temperature < 85 && entity.hasStatusEffect(Isur.HEATSTROKE)) {
                entity.removeStatusEffect(Isur.HEATSTROKE);
                lastTemperatureDamageTime = 0;
            }

            //if(entity instanceof ClientPlayerEntity)
                //LivingEntityMixinData.TEMPERATURE = temperature;
        }
    }

    private float getBiomeTemperature(BlockPos pos, World world){
        if(BIOME_TEMPERATURES == null){
            BIOME_TEMPERATURES = new HashMap<Biome, BiomeTemp>();
            ConfigManager.getArray("biomeTemperatures").forEach(b -> {
                JsonObject j = (JsonObject)b;
                BiomeTemp t = new BiomeTemp(j.get("day").getAsFloat(), j.get("night").getAsFloat());
                Biome biome = Registry.BIOME.get(new Identifier(j.get("biome").getAsString()));
                if(t != null && biome != null)
                    BIOME_TEMPERATURES.put(biome, t);
            });
        }


        Biome b = world.getBiome(pos);
        float timeTemp = (world.getTimeOfDay() % 24000) / 6000f;
        timeTemp = IsurMath.multiLerp(timeTemp, 0.5f, 1, 0.5f, 0, 0.5f);
        if(BIOME_TEMPERATURES.containsKey(b)){
            BiomeTemp t = BIOME_TEMPERATURES.get(b);
            return MathHelper.lerp(timeTemp,t.night, t.day); 
        }
        return (b.getTemperature() + 0.25f) * (40 * (timeTemp + 0.5f));
    }

    @Override
    public void addTemperature(final float amount){
        temperature += amount;
        temperature = MathHelper.clamp(temperature, -100, 100);
    }

    @Override
    public float getTemperature() {
        return temperature;
    }

    @Override
    public void addWetness(float amount) {
        wetness += amount;
        wetness = MathHelper.clamp(wetness, 0, 100);
    }

    @Override
    public float getWetness() {
        return wetness;
    }

    @Override
    public void setTemperature(float amount) {
        temperature = amount;
        temperature = MathHelper.clamp(temperature, -100, 100);
    }

    @Override
    public void setWetness(float amount) {
        wetness = amount;
        wetness = MathHelper.clamp(wetness, 0, 100);
    }
}