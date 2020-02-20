package net.improvedsurvival.mixin.server.item;

import net.minecraft.item.ToolMaterials;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ToolMaterials.class)
public class ToolMaterialsMixin {
	static {
		((ToolMaterialsAccessor) (Object) ToolMaterials.GOLD).setItemDurability(170);
		((ToolMaterialsAccessor) (Object) ToolMaterials.GOLD).setMiningLevel(1);
	}
}