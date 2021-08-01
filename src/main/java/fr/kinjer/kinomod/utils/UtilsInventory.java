package fr.kinjer.kinomod.utils;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;

public class UtilsInventory {

	public static boolean hasStack(ItemStack stack, IInventory inventory) {
		if (stack.isEmpty()) {
			return false;
		}

		for (int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack s = inventory.getStackInSlot(i);

			if (ItemStack.areItemsEqual(stack, s) && stack.getItemDamage() == s.getItemDamage()
					&& s.getCount() >= stack.getCount()) {
				return true;
			}
		}

		return false;
	}

	public static boolean consumeStack(ItemStack stack, IInventory inventory) {
		if (stack.isEmpty()) {
			return false;
		}

		for (int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack s = inventory.getStackInSlot(i);
			if (s.isEmpty()) {
				continue;
			}

			if (ItemStack.areItemsEqual(stack, s) && stack.getItemDamage() == s.getItemDamage()
					&& s.getCount() >= stack.getCount()) {
				s.shrink(stack.getCount());
				inventory.markDirty();
				return true;
			}
		}

		return false;
	}

	public static void consumeHeldItem(EntityPlayer player, ItemStack stack, EnumHand hand) {
		stack.shrink(1);
		player.setHeldItem(hand, stack.getCount() > 0 ? stack.copy() : ItemStack.EMPTY);
	}
}
