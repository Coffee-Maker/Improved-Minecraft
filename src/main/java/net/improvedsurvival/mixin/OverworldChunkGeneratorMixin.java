package net.improvedsurvival.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.world.gen.chunk.OverworldChunkGenerator;

@Mixin(OverworldChunkGenerator.class)
public class OverworldChunkGeneratorMixin {
    @ModifyConstant(method = "computeNoiseFalloff")
    protected double computeNoiseFalloff(double variable) {
        if(variable != 8.5d)
            return variable;
  
        return 9.5d;
    }

    @ModifyConstant(method = "getSeaLevel")
    public int getSeaLevel(int variable) {
        if(variable != 63)
            return variable;
  
        return 141;
    }
}