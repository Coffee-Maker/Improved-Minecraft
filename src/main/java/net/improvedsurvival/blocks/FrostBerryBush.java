package net.improvedsurvival.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class FrostBerryBush extends SweetBerryBushBlock {
	public FrostBerryBush(Settings settings) {
		super(settings);
	}
	
	@Environment(EnvType.CLIENT)
	@Override
	public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
		return new ItemStack(Registry.ITEM.get(new Identifier("isur:frost_berries")));
	}
	
	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		super.onEntityCollision(state, world, pos, entity);
	}
	
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		int i = state.get(AGE);
		boolean bl = i == 3;
		if(!bl && player.getStackInHand(hand).getItem() == Items.BONE_MEAL) {
			return ActionResult.PASS;
		} else if(i > 1) {
			int j = 1 + world.random.nextInt(2);
			dropStack(world, pos, new ItemStack(Registry.ITEM.get(new Identifier("isur:frost_berries")), j + (bl ? 1 : 0)));
			world.playSound(null, pos, SoundEvents.ITEM_SWEET_BERRIES_PICK_FROM_BUSH, SoundCategory.BLOCKS, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);
			world.setBlockState(pos, state.with(AGE, 1), 2);
			return ActionResult.SUCCESS;
		} else {
			return super.onUse(state, world, pos, player, hand, hit);
		}
	}
}