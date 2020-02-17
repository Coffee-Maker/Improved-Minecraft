package net.improvedsurvival.mixin.client.item;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.improvedsurvival.containers.GlazerContainer;
import net.improvedsurvival.containers.GlazingObject;
import net.improvedsurvival.items.TemperatureArmorItem;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.Item;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.registry.Registry;

@Mixin(ItemColors.class)
public class ItemColorsMixin {
    @Inject(method = "create", at = @At("RETURN"))
    private static void create(BlockColors blockMap, CallbackInfoReturnable<ItemColors> info) {
        ItemColors itemColors = info.getReturnValue();
        
        itemColors.register((stack, tintIndex) -> {
            ListTag list = stack.getOrCreateTag().getList("Glazings", 8);
            if(list != null){
                for(Tag t : list){
                    GlazingObject go = GlazerContainer.GLAZINGS.get(t.asString());
                    if(go != null){
                        return go.color;
                    }
                }
            }
            return -1;
        }, Registry.ITEM.stream().filter(item -> item.isFood()).toArray(Item[]::new));

        itemColors.register((stack, tintIndex) -> {
            return tintIndex > 0 ? -1 : ((DyeableItem)stack.getItem()).getColor(stack);
        }, Registry.ITEM.stream().filter(item -> item instanceof TemperatureArmorItem).toArray(Item[]::new));
    }
}