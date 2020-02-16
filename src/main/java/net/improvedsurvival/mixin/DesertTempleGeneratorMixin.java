package net.improvedsurvival.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.structure.DesertTempleGenerator;
import net.minecraft.world.gen.chunk.CavesChunkGenerator;

@Mixin(DesertTempleGenerator.class)
public class DesertTempleGeneratorMixin {
    @Inject()
    public boolean generate(IWorld world, ChunkGenerator<?> generator, Random random, BlockBox box, ChunkPos pos) {
        if(variable != 128)
            return variable;
  
        return 255;
    }
}