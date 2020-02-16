package net.improvedsurvival.mixin.item;

import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.item.ToolMaterials;

@Mixin(ToolMaterials.class)
public class ToolMaterialsMixin {
	static {
		((ToolMaterialsAccessor) (Object) ToolMaterials.GOLD).setItemDurability(170);
		((ToolMaterialsAccessor) (Object) ToolMaterials.GOLD).setMiningLevel(1);
	}
}