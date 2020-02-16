package net.improvedsurvival.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.world.gen.carver.CaveCarver;

@Mixin(CaveCarver.class)
public class CaveCarverMixin{

    @ModifyConstant(method = "getCaveY")
    protected int getCaveY(int variable) {
        if(variable != 120)
            return variable;
  
        return 248;
    }
}