package net.improvedsurvival.mixin.server.world;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Constant;

import net.minecraft.world.gen.chunk.CavesChunkGenerator;

@Mixin(CavesChunkGenerator.class)
public class CavesChunkGeneratorMixin {
    @ModifyConstant(method = "getMaxY", constant = { @Constant(intValue = 128) })
    protected int getMaxY(int variable) {
        return 255;
    }
}