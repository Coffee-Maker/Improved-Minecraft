package net.improvedsurvival.interfaces;

import net.minecraft.structure.StructurePlacementData;

public interface IStructurePlacementDataReplaceBlocks {
	StructurePlacementData setReplaceBlocks(boolean replaceBlocks);
	
	boolean getReplaceBlocks();
}