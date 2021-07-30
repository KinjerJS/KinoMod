package fr.kinjer.kinomod.items.base;

import java.util.UUID;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.init.InitItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class BaseKino extends Item{
	
	private String NAME;
	
	public BaseKino(String name) {
		this.NAME = name;
		InitItems.setItemName(this, name);
		setCreativeTab(KinoMod.tabKino);
	}
	
	public String getName() {
		return this.NAME;
	}
	
	public static NBTTagCompound getNBT(ItemStack stack) {
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		return stack.getTagCompound();
	}
	
	public static boolean verifyExistance(ItemStack stack, String tag) {
		return !stack.isEmpty() && getNBT(stack).hasKey(tag);
	}

	
	public static void removeEntry(ItemStack stack, String tag) {
		getNBT(stack).removeTag(tag);
	}
	
	public static String getString(ItemStack stack, String tag, String defaultExpected) {
		return verifyExistance(stack, tag) ? getNBT(stack).getString(tag) : defaultExpected;
	}
}
