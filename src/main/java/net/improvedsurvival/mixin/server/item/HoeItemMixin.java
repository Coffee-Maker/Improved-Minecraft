package net.improvedsurvival.mixin.server.item;

import net.minecraft.block.BeetrootsBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(HoeItem.class)
public class HoeItemMixin {
	
	@Inject(at = @At("HEAD"), method = "useOnBlock", cancellable = true)
	private void useOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> callbackInfo) {
		BlockPos pos = context.getBlockPos();
		World world = context.getWorld();
		BlockState state = world.getBlockState(pos);
		if(state.getBlock() instanceof CropBlock) {
			CropBlock b = (CropBlock) state.getBlock();
			IntProperty age = b instanceof BeetrootsBlock ? BeetrootsBlock.AGE : CropBlock.AGE;
			if(state.get(age) == b.getMaxAge()) {
				if(!world.isClient) {
					List<ItemStack> stacks = CropBlock.getDroppedStacks(state, (ServerWorld) world, pos, null);
					for(ItemStack s : stacks) {
						double d = (double) (world.random.nextFloat() * 0.5F) + 0.25D;
						double e = (double) (world.random.nextFloat() * 0.5F) + 0.25D;
						double g = (double) (world.random.nextFloat() * 0.5F) + 0.25D;
						world.spawnEntity(new ItemEntity(world, pos.getX() + d, pos.getY() + e, pos.getZ() + g, s));
					}
					world.playLevelEvent(2001, pos, Block.getRawIdFromState(state));
					world.setBlockState(pos, state.with(age, 0));
					
					LivingEntity player = context.getPlayer();
					if(player != null) {
						context.getStack().damage(1, player, ((p) -> p.sendToolBreakStatus(context.getHand())));
					}
				}
				
				
				callbackInfo.setReturnValue(ActionResult.SUCCESS);
				callbackInfo.cancel();
			}
		}
	}
}