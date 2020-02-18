package net.improvedsurvival.mixin.server.block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.improvedsurvival.registry.IsurBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

@Mixin(CropBlock.class)
public class CropBlockMixin{

    @Inject(at = @At("HEAD"), method = "canPlantOnTop", cancellable = true)
    private void canPlantOnTop(final BlockState floor, final BlockView view, final BlockPos pos, final CallbackInfoReturnable<Boolean> returnable){
        if(floor.getBlock().equals(IsurBlocks.SOIL_FARMLAND)){
            returnable.setReturnValue(true);
        }
    }
}