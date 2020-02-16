package net.improvedsurvival.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.world.gen.chunk.CavesChunkGenerator;

@Mixin(CavesChunkGenerator.class)
public class CavesChunkGeneratorMixin {
    @ModifyConstant(method = "getMaxY")
    protected int getMaxY(int variable) {
        if(variable != 128)
            return variable;
  
        return 255;
    }
}