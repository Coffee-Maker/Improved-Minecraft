package net.improvedsurvival.mixin.server.world;

import net.improvedsurvival.interfaces.IStructurePlacementDataReplaceBlocks;
import net.minecraft.structure.StructurePlacementData;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(StructurePlacementData.class)
public class StructurePlacementDataMixin implements IStructurePlacementDataReplaceBlocks {
	private boolean replaceBlocks = true;
	
	@Override
	public StructurePlacementData setReplaceBlocks(boolean replaceBlocks) {
		this.replaceBlocks = replaceBlocks;
		return (StructurePlacementData) (Object) this;
	}
	
	@Override
	public boolean getReplaceBlocks() {
		return this.replaceBlocks;
	}
}