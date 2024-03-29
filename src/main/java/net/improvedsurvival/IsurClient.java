package net.improvedsurvival;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.fabricmc.fabric.api.event.client.ItemTooltipCallback;
import net.improvedsurvival.containers.GlazerContainer;
import net.improvedsurvival.containers.GlazerScreen;
import net.improvedsurvival.registry.IsurBlocks;
import net.improvedsurvival.rendering.PlayerOverlayIndicators;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.ListTag;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

public class IsurClient implements ClientModInitializer
{
    @Override
    public void onInitializeClient() {
        ScreenProviderRegistry.INSTANCE.<GlazerContainer>registerFactory(Isur.GLAZER_CRAFTING, container -> new GlazerScreen(container, playerInv(), new TranslatableText("container.glazer", new Object[0])));

        HudRenderCallback.EVENT.register(t -> PlayerOverlayIndicators.renderHeatOverlay());
        BlockRenderLayerMap.INSTANCE.putBlock(IsurBlocks.FROST_BERRY_BUSH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(IsurBlocks.PALM_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(IsurBlocks.PALM_TRAPDOOR, RenderLayer.getCutout());

        ItemTooltipCallback.EVENT.register((stack, context, lines) -> {
            if(!stack.getItem().isFood())
                return;
                
            lines.add(1, new LiteralText("Glazings:"));
            
            ListTag glazings = stack.getOrCreateTag().getList("Glazings", 8);
            
            if(!glazings.isEmpty()) {
                for(int i = 0; i < glazings.size(); i++) {
                    lines.add(2+i, new LiteralText(Formatting.GRAY + "" + (new TranslatableText("glazing." + glazings.getString(i)).getString()) + " (" + new TranslatableText("glazing." + glazings.getString(i) + ".effect", GlazerContainer.GLAZINGS.get(glazings.getString(i)).duration / 20).getString() + ")"));
                }
            } else {
                lines.add(2, new LiteralText(Formatting.GRAY + "" + Formatting.ITALIC + "None"));
            }
        });

        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> BiomeColors.getFoliageColor(view, pos), IsurBlocks.PALM_LEAVES);
    }

    public static PlayerInventory playerInv(){
        MinecraftClient client = MinecraftClient.getInstance();
        return client.player.inventory;
    }
}