package net.improvedsurvival.blocks;

import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.improvedsurvival.Isur;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Glazer extends DirectionalBlock {
	
	public Glazer(Block.Settings settings) {
		super(settings);
	}
	
	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if(!world.isClient)
			ContainerProviderRegistry.INSTANCE.openContainer(Isur.GLAZER_CRAFTING, player, byteBuf -> { });
		
		return ActionResult.SUCCESS;
	}
}