package fr.kinjer.kinomod.items;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.items.base.BaseKino;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemBismuth extends BaseKino {
	
	public static final String NAME = "bismuth";
	
	public ItemBismuth() {
		super(NAME);
	}
	
	@Override
	public boolean hasEffect(ItemStack par1ItemStack) {
		return true;
	}
}
