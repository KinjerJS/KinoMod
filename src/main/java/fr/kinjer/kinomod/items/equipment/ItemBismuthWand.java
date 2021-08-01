package fr.kinjer.kinomod.items.equipment;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.items.base.BaseKino;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemBismuthWand extends BaseKino {
	
	public static final String NAME = "bismuth_wand";
	
	public ItemBismuthWand() {
		super(NAME);
	}
	
	@Override
	public boolean hasEffect(ItemStack stack) {
		
		return true;
	}
}
