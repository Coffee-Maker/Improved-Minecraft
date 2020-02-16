package net.improvedsurvival;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ToolMaterial;

@Mixin(HoeItem.class)
public class HoeExtensions extends HoeItem {

    public HoeExtensions(ToolMaterial material, float attackSpeed, Settings settings) {
        super(material, attackSpeed, settings);
    }

    public static void addTillableBlock(Block block, BlockState result) {
        TILLED_BLOCKS.put(block, result);
    }
}
