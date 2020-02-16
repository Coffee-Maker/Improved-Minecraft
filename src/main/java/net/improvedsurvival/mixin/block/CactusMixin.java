package net.improvedsurvival.mixin.block;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CactusBlock;
import net.minecraft.container.Container;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(CactusBlock.class)
public abstract class CactusMixin extends Block{
    public CactusMixin(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
            BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isEmpty()) {
            return ActionResult.PASS;
        }else{
            Item item = itemStack.getItem();
            ItemStack itemStack4;
            if (item == Items.GLASS_BOTTLE) {
                if (!world.isClient) {
                    itemStack4 = PotionUtil.setPotion(new ItemStack(Items.POTION), Potions.WATER);
                    itemStack.decrement(1);
                    if (itemStack.isEmpty()) {
                    player.setStackInHand(hand, itemStack4);
                    } else if (!player.inventory.insertStack(itemStack4)) {
                    player.dropItem(itemStack4, false);
                    } else if (player instanceof ServerPlayerEntity) {
                    ((ServerPlayerEntity)player).openContainer((Container)player.playerContainer);
                    }

                    world.playSound((PlayerEntity)null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }

                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    }
}