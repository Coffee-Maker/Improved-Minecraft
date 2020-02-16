package net.improvedsurvival.items;

import net.improvedsurvival.interfaces.IEnvironmentalEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FrostBerry extends AliasedBlockItem {

    public FrostBerry(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        ((IEnvironmentalEntity)user).addTemperature(-15);
        return super.finishUsing(stack, world, user);
    }
}