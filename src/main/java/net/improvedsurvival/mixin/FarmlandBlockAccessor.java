package net.improvedsurvival.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.block.FarmlandBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;

@Mixin(FarmlandBlock.class)
public interface FarmlandBlockAccessor{
    
    @Invoker("isWaterNearby")
    static boolean isWaterNearby(WorldView worldView, BlockPos pos){
        throw new IllegalStateException("Untransformed Accessor!");
    }

    @Invoker("hasCrop")
    static boolean hasCrop(BlockView world, BlockPos pos) {
        throw new IllegalStateException("Untransformed Accessor!");
     }
}