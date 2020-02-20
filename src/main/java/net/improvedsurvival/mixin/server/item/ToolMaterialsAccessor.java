package net.improvedsurvival.mixin.server.item;

import net.minecraft.item.ToolMaterials;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ToolMaterials.class)
public interface ToolMaterialsAccessor {
	@Accessor
	void setItemDurability(int durability);
	
	@Accessor
	void setMiningLevel(int level);
}