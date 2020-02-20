package net.improvedsurvival.mixin.server.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Item.class)
public class ItemMixin {
	@Redirect(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;canConsume(Z)Z"))
	public boolean use(PlayerEntity player, boolean ignoreHunger, World world, PlayerEntity user, Hand hand) {
		return player.getStackInHand(hand).getOrCreateTag().getBoolean("AlwaysEdible") || player.canConsume(ignoreHunger);
	}
}