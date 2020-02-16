package net.improvedsurvival.mixin.world;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.world.biome.Biome;

@Mixin(Biome.Settings.class)
public class Biome$SettingsMixin{
    @Shadow private Float depth;

    @Overwrite
    public Biome.Settings depth(float depth) {
        this.depth = depth + 3.7f;
        return (Biome.Settings) (Object) this;
    }
}