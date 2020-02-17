package net.improvedsurvival.mixin.server.world;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.improvedsurvival.Isur;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.OceanMonumentGenerator;
import net.minecraft.structure.StructurePieceType;

@Mixin(OceanMonumentGenerator.Base.class)
public abstract class OceanMonumentGeneratorBaseMixin extends OceanMonumentGenerator.Piece {
    private static final int OLD_SEA_LEVEL = 63;
    private static final int SEALEVEL_DIFF = (Isur.seaLevel - OLD_SEA_LEVEL);
    private static final int OLD_MONUMENT_Y = 39;
    private static final int MONUMENT_HEIGHT = 22;

    public OceanMonumentGeneratorBaseMixin(StructurePieceType structurePieceType, CompoundTag compoundTag) { super(structurePieceType, compoundTag); }

    @ModifyConstant(method = "<init>", constant = { @Constant(intValue = OLD_MONUMENT_Y), @Constant(intValue = OLD_MONUMENT_Y + MONUMENT_HEIGHT) })
    protected int constructor(int variable) {
        if(variable == 39)
            return OLD_MONUMENT_Y + SEALEVEL_DIFF;
        else if(variable == 61)
            return OLD_MONUMENT_Y + MONUMENT_HEIGHT + SEALEVEL_DIFF;
        
        return -1;
    }
}