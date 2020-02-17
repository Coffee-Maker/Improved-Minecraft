package net.improvedsurvival.mixin.server.world;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.DesertTempleGenerator;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.structure.StructurePieceWithDimensions;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.chunk.ChunkGenerator;

@Mixin(DesertTempleGenerator.class)
public abstract class DesertTempleGeneratorMixin extends StructurePieceWithDimensions {
    protected DesertTempleGeneratorMixin(StructurePieceType structurePieceType, CompoundTag compoundTag) { super(structurePieceType, compoundTag); }

    @Inject(at = @At("HEAD"), method = "generate", cancellable = true)
    public void generate(IWorld world, ChunkGenerator<?> generator, Random random, BlockBox box, ChunkPos pos, CallbackInfoReturnable<Boolean> cir) {
        if(!this.method_14839(world, box, -2 - random.nextInt(5))) {
            cir.setReturnValue(false);
        }
    }
}