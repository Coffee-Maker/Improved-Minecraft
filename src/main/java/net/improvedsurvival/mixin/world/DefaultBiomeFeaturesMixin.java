package net.improvedsurvival.mixin.world;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.improvedsurvival.Isur;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.decorator.ConfiguredDecorator;
import net.minecraft.world.gen.decorator.CountDepthDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.DecoratorConfig;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;

@Mixin(DefaultBiomeFeatures.class)
public class DefaultBiomeFeaturesMixin {
    @Redirect(method = "addDefaultOres", at = @At(ordinal = 0, value = "INVOKE", target = "Lnet/minecraft/world/gen/decorator/Decorator;configure(Lnet/minecraft/world/gen/decorator/DecoratorConfig;)Lnet/minecraft/world/gen/decorator/ConfiguredDecorator;"))
    private static ConfiguredDecorator<RangeDecoratorConfig> redirectConfigureCoal(Decorator<RangeDecoratorConfig> decorator, DecoratorConfig config) {
        return Decorator.COUNT_RANGE.configure(new RangeDecoratorConfig(20, 0, 0, 256));
    }
    
    @Redirect(method = "addDefaultOres", at = @At(ordinal = 1, value = "INVOKE", target = "Lnet/minecraft/world/gen/decorator/Decorator;configure(Lnet/minecraft/world/gen/decorator/DecoratorConfig;)Lnet/minecraft/world/gen/decorator/ConfiguredDecorator;"))
    private static ConfiguredDecorator<RangeDecoratorConfig> redirectConfigureIron(Decorator<RangeDecoratorConfig> decorator, DecoratorConfig config) {
        return Decorator.COUNT_RANGE.configure(new RangeDecoratorConfig(20, 0, 0, 80));
    }
    
    @Redirect(method = "addDefaultOres", at = @At(ordinal = 2, value = "INVOKE", target = "Lnet/minecraft/world/gen/decorator/Decorator;configure(Lnet/minecraft/world/gen/decorator/DecoratorConfig;)Lnet/minecraft/world/gen/decorator/ConfiguredDecorator;"))
    private static ConfiguredDecorator<RangeDecoratorConfig> redirectConfigureGold(Decorator<RangeDecoratorConfig> decorator, DecoratorConfig config) {
        return Decorator.COUNT_RANGE.configure(new RangeDecoratorConfig(2, 0, 0, 45));
    }
    
    @Redirect(method = "addDefaultOres", at = @At(ordinal = 3, value = "INVOKE", target = "Lnet/minecraft/world/gen/decorator/Decorator;configure(Lnet/minecraft/world/gen/decorator/DecoratorConfig;)Lnet/minecraft/world/gen/decorator/ConfiguredDecorator;"))
    private static ConfiguredDecorator<RangeDecoratorConfig> redirectConfigureRedstone(Decorator<RangeDecoratorConfig> decorator, DecoratorConfig config) {
        return Decorator.COUNT_RANGE.configure(new RangeDecoratorConfig(8, 0, 0, 23));
    }
    
    @Redirect(method = "addDefaultOres", at = @At(ordinal = 4, value = "INVOKE", target = "Lnet/minecraft/world/gen/decorator/Decorator;configure(Lnet/minecraft/world/gen/decorator/DecoratorConfig;)Lnet/minecraft/world/gen/decorator/ConfiguredDecorator;"))
    private static ConfiguredDecorator<RangeDecoratorConfig> redirectConfigureDiamond(Decorator<RangeDecoratorConfig> decorator, DecoratorConfig config) {
        return Decorator.COUNT_RANGE.configure(new RangeDecoratorConfig(1, 0, 0, 16));
    }
    
    @Redirect(method = "addDefaultOres", at = @At(ordinal = 5, value = "INVOKE", target = "Lnet/minecraft/world/gen/decorator/Decorator;configure(Lnet/minecraft/world/gen/decorator/DecoratorConfig;)Lnet/minecraft/world/gen/decorator/ConfiguredDecorator;"))
    private static ConfiguredDecorator<CountDepthDecoratorConfig> redirectConfigureLapis(Decorator<CountDepthDecoratorConfig> decorator, DecoratorConfig config) {
        return Decorator.COUNT_DEPTH_AVERAGE.configure(new CountDepthDecoratorConfig(1, 16, 16));
    }
    


    @Redirect(method = "addMineables", at = @At(ordinal = 2, value = "INVOKE", target = "Lnet/minecraft/world/gen/decorator/Decorator;configure(Lnet/minecraft/world/gen/decorator/DecoratorConfig;)Lnet/minecraft/world/gen/decorator/ConfiguredDecorator;"))
    private static ConfiguredDecorator<RangeDecoratorConfig> redirectConfigureGranite(Decorator<RangeDecoratorConfig> decorator, DecoratorConfig config) {
        return Decorator.COUNT_RANGE.configure(new RangeDecoratorConfig(10, 0, 0, Isur.seaLevel + 17));
    }

    @Redirect(method = "addMineables", at = @At(ordinal = 3, value = "INVOKE", target = "Lnet/minecraft/world/gen/decorator/Decorator;configure(Lnet/minecraft/world/gen/decorator/DecoratorConfig;)Lnet/minecraft/world/gen/decorator/ConfiguredDecorator;"))
    private static ConfiguredDecorator<RangeDecoratorConfig> redirectConfigureDionite(Decorator<RangeDecoratorConfig> decorator, DecoratorConfig config) {
        return Decorator.COUNT_RANGE.configure(new RangeDecoratorConfig(10, 0, 0, Isur.seaLevel + 17));
    }

    @Redirect(method = "addMineables", at = @At(ordinal = 4, value = "INVOKE", target = "Lnet/minecraft/world/gen/decorator/Decorator;configure(Lnet/minecraft/world/gen/decorator/DecoratorConfig;)Lnet/minecraft/world/gen/decorator/ConfiguredDecorator;"))
    private static ConfiguredDecorator<RangeDecoratorConfig> redirectConfigureAndesite(Decorator<RangeDecoratorConfig> decorator, DecoratorConfig config) {
        return Decorator.COUNT_RANGE.configure(new RangeDecoratorConfig(10, 0, 0, Isur.seaLevel + 17));
    }
}