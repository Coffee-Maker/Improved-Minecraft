package net.improvedsurvival.mixin.server.world;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Constant;

import net.improvedsurvival.Isur;
import net.minecraft.world.gen.chunk.OverworldChunkGenerator;

@Mixin(OverworldChunkGenerator.class)
public class OverworldChunkGeneratorMixin {
    @ModifyConstant(method = "computeNoiseFalloff", constant = { @Constant(doubleValue = 8.5d) })
    protected double computeNoiseFalloff(double variable) {
        return 9.5d;
    }

    @ModifyConstant(method = "getSeaLevel", constant = { @Constant(intValue = 63) })
    public int getSeaLevel(int variable) {
        return Isur.seaLevel;
    }
}