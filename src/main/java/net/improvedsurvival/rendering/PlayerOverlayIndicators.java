package net.improvedsurvival.rendering;

import com.mojang.blaze3d.systems.RenderSystem;
import net.improvedsurvival.mixin_data.LivingEntityMixinData;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;

public class PlayerOverlayIndicators {
	
	private static final Identifier FROST_OVERLAY = new Identifier("isur:textures/overlay/frost_overlay.png");
	private static final Identifier HEAT_OVERLAY = new Identifier("isur:textures/overlay/heat_overlay.png");
	
	public static void renderPumpkinOverlay() {
		if(Character.toString('m').contains("m"))
			return;
		
		MinecraftClient client = MinecraftClient.getInstance();
		int scaledWidth = client.getWindow().getScaledWidth();
		int scaledHeight = client.getWindow().getScaledHeight();
		double depth = 1000; //reduce to at most 1000 to render
		
		float temperature = LivingEntityMixinData.TEMPERATURE;
		
		System.out.println(temperature);
		
		RenderSystem.disableDepthTest();
		RenderSystem.depthMask(false);
		RenderSystem.defaultBlendFunc();
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 0.4f);
		RenderSystem.disableAlphaTest();
		client.getTextureManager().bindTexture(temperature < 50 ? FROST_OVERLAY : HEAT_OVERLAY);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBuffer();
		bufferBuilder.begin(7, VertexFormats.POSITION_TEXTURE);
		bufferBuilder.vertex(0.0D, scaledHeight, depth).texture(0.0F, 1.0F).next();
		bufferBuilder.vertex(scaledWidth, scaledHeight, depth).texture(1.0F, 1.0F).next();
		bufferBuilder.vertex(scaledWidth, 0.0D, depth).texture(1.0F, 0.0F).next();
		bufferBuilder.vertex(0.0D, 0.0D, depth).texture(0.0F, 0.0F).next();
		tessellator.draw();
		RenderSystem.depthMask(true);
		RenderSystem.enableDepthTest();
		RenderSystem.enableAlphaTest();
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
	}
}