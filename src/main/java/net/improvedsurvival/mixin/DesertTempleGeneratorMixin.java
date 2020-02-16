package net.improvedsurvival.mixin;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.structure.DesertTempleGenerator;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.chunk.ChunkGenerator;

@Mixin(DesertTempleGenerator.class)
public class DesertTempleGeneratorMixin {
    @Shadow protected boolean method_14839(IWorld world, BlockBox boundingBox, int i) { return false; }
    
    @Inject(at = @At("HEAD"), method = "generate")
    public void generate(IWorld world, ChunkGenerator<?> generator, Random random, BlockBox box, ChunkPos pos, CallbackInfoReturnable<Boolean> cir) {
        if(!this.method_14839(world, box, 0)) {
            cir.setReturnValue(false);
        }
    }
}