package net.improvedsurvival.mixin.item;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.item.ToolMaterials;

@Mixin(ToolMaterials.class)
public interface ToolMaterialsAccessor {
	@Accessor public void setItemDurability(int durability);
	@Accessor public void setMiningLevel(int level);
}