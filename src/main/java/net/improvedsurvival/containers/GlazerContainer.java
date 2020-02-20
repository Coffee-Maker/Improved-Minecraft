package net.improvedsurvival.containers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.improvedsurvival.config.json.ConfigManager;
import net.improvedsurvival.containers.GlazingObject.GlazingInstantEffects;
import net.minecraft.container.Container;
import net.minecraft.container.Slot;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.BasicInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;

public class GlazerContainer extends Container {
	
	public static final Map<String, GlazingObject> GLAZINGS = new HashMap<>();
	
	private final Inventory craftingInv;
	private final Inventory resultInv;
	private final Slot foodSlot;
	private final Slot glazingSlot;
	private final Slot resultSlot;
	
	public static void ensureReady() {
		ConfigManager.loadConfig();
		
		GLAZINGS.clear();
		JsonArray glazingsArray = ConfigManager.getArray("glazings");
		
		for(Object o : glazingsArray) {
			JsonElement item = ((JsonObject) o).get("item");
			JsonElement effect = ((JsonObject) o).get("effect");
			JsonElement duration = ((JsonObject) o).get("duration");
			JsonElement amplifier = ((JsonObject) o).get("amplifier");
			JsonElement temperature = ((JsonObject) o).get("temperature");
			JsonElement saturation = ((JsonObject) o).get("saturation");
			JsonElement color = ((JsonObject) o).get("color");
			int intColor = Integer.parseInt(color.getAsString(), 16);
			
			if(item != null) {
				if(effect != null && duration != null && amplifier != null) {
					StatusEffect statusEffect = Registry.STATUS_EFFECT.get(new Identifier(effect.getAsString()));
					GLAZINGS.put(item.getAsString(), new GlazingObject(statusEffect, duration.getAsInt(), amplifier.getAsInt(), intColor));
				} else if(temperature != null) {
					GLAZINGS.put(item.getAsString(), new GlazingObject(temperature.getAsInt(), GlazingInstantEffects.TEMPERATURE, intColor));
				} else if(saturation != null) {
					GLAZINGS.put(item.getAsString(), new GlazingObject(saturation.getAsInt(), GlazingInstantEffects.SATURATION, intColor));
				}
			}
		}
	}
	
	public GlazerContainer(int syncId, PlayerInventory playerInventory) {
		super(null, syncId);
		
		ensureReady();
		
		this.craftingInv = new BasicInventory(2) {
			public void markDirty() {
				super.markDirty();
				GlazerContainer.this.onContentChanged(this);
			}
		};
		
		this.resultInv = new BasicInventory(1);
		
		// Crafting interface
		foodSlot = this.addSlot(new Slot(craftingInv, 0, 43, 54) {
			@Override
			public boolean canInsert(ItemStack stack) {
				return stack.isFood();
			}
		});
		glazingSlot = this.addSlot(new Slot(craftingInv, 1, 43, 14));
		resultSlot = this.addSlot(new Slot(resultInv, 0, 119, 35) {
			
			@Override
			public ItemStack onTakeItem(PlayerEntity player, ItemStack stack) {
				onTakeResult(player);
				return stack;
			}
			
			@Override
			public ItemStack takeStack(int amount) {
				ItemStack r = resultSlot.getStack().copy();
				while(getResult() != null && r.getCount() != amount) {
					r.increment(1);
					onTakeResult(playerInventory.player);
				}
				return r;
			}
			
			@Override
			public boolean canInsert(ItemStack stack) {
				return false;
			}
		});
		
		// Player Inventory
		int m;
		int l;
		for(m = 0; m < 3; ++m) {
			for(l = 0; l < 9; ++l) {
				this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
			}
		}
		
		for(m = 0; m < 9; ++m) {
			this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 142));
		}
	}
	
	@Override
	public boolean canUse(PlayerEntity player) {
		return true;
	}
	
	@Override
	public void close(PlayerEntity player) {
		super.close(player);
		dropInventory(player, player.world, craftingInv);
	}
	
	@Override
	public ItemStack transferSlot(PlayerEntity player, int invSlot) {
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot = this.slots.get(invSlot);
		if(slot != null && slot.hasStack()) {
			ItemStack itemStack2 = slot.getStack();
			itemStack = itemStack2.copy();
			if(invSlot == 2) {
				itemStack2.setCount(1);
				while(getResult() != null) {
					if(!this.insertItem(itemStack2.copy(), 3, 39, true)) {
						return ItemStack.EMPTY;
					}
					onTakeResult(player);
					slot.onStackChanged(itemStack2, itemStack);
				}
			} else if(invSlot < 2) {
				if(!this.insertItem(itemStack2, 3, 39, true)) {
					return ItemStack.EMPTY;
				}
				updateResult();
				slot.onStackChanged(itemStack2, itemStack);
			} else {
				if(!insertItem(itemStack2, 0, 1, false)) {
					return ItemStack.EMPTY;
				}
				slot.onStackChanged(itemStack2, itemStack);
			}
		}
		
		return itemStack;
	}
	
	private void onTakeResult(PlayerEntity player) {
		foodSlot.getStack().decrement(1);
		Item remainder = glazingSlot.getStack().getItem().getRecipeRemainder();
		glazingSlot.getStack().decrement(1);
		if(!glazingSlot.hasStack()) {
			glazingSlot.setStack(new ItemStack(remainder));
		} else {
			player.inventory.offerOrDrop(player.world, new ItemStack(remainder));
		}
		updateResult();
	}
	
	@Override
	public void onContentChanged(Inventory inventory) {
		if(inventory == craftingInv)
			updateResult();
	}
	
	private void updateResult() {
		if(foodSlot.hasStack() && glazingSlot.hasStack()) {
			ItemStack r = getResult();
			resultSlot.setStack(r != null ? r : ItemStack.EMPTY);
		} else if(!resultSlot.getStack().isEmpty()) {
			resultSlot.setStack(ItemStack.EMPTY);
		}
	}
	
	public ItemStack getResult() {
		if(foodSlot.hasStack()) {
			if(GLAZINGS.containsKey(Registry.ITEM.getId(glazingSlot.getStack().getItem()).toString())) {
				ItemStack result = foodSlot.getStack().copy();
				result.setCount(1);
				String modifier = Registry.ITEM.getId(glazingSlot.getStack().getItem()).toString();
				ListTag listTag = result.getOrCreateTag().getList("Glazings", 8);
				listTag.clear();
				listTag.add(StringTag.of(modifier));
				result.getOrCreateTag().put("Glazings", listTag);
				result.getOrCreateTag().putBoolean("AlwaysEdible", true);
				return result;
			}
		}
		
		return null;
	}
}