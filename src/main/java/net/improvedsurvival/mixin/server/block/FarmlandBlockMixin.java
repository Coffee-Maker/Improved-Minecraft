package net.improvedsurvival.mixin.server.block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;

import net.improvedsurvival.Isur;
import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(FarmlandBlock.class)
public class FarmlandBlockMixin {
    
    @Inject(at = @At("HEAD"), method = "setToDirt", cancellable = true)
    private static void setToDirt(final BlockState state, final World world, final BlockPos pos, final CallbackInfo callbackInfo) {
        if(state.getBlock().equals(Isur.SOIL_FARMLAND)) {
            world.setBlockState(pos, Isur.SOIL.getDefaultState());
            callbackInfo.cancel();
        }
    }
}