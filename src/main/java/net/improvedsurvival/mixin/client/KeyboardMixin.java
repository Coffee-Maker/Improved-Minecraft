package net.improvedsurvival.mixin.client;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;

@Mixin(Keyboard.class)
public class KeyboardMixin {
	@Shadow @Final private MinecraftClient client;
	@Shadow private void debugWarn(String string, Object... objects) { }
	
	@Inject(method = "processF3", at = @At("HEAD"), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
	private void injectProcessF3(int key, CallbackInfoReturnable<Boolean> cir) {
		if(key == 78) {
			if(!this.client.player.allowsPermissionLevel(2)) {
				this.debugWarn("debug.creative_spectator.error");
			} else if(this.client.player.isCreative()) {
				this.client.player.sendChatMessage("/tp @s ~ ~0.5 ~");
				this.client.player.sendChatMessage("/gamemode spectator");
			} else if(this.client.player.isSpectator()) {
				this.client.player.sendChatMessage("/gamemode survival");
			} else {
				this.client.player.sendChatMessage("/gamemode creative");
			}
			
			cir.setReturnValue(true);
		}
	}
}
