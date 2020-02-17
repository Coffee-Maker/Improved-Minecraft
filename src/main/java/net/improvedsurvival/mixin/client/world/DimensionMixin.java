package net.improvedsurvival.mixin.client.world;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.world.dimension.Dimension;

@Mixin(Dimension.class)
public class DimensionMixin{
    @Overwrite
    public float getCloudHeight() {
        return 260;
    }
}