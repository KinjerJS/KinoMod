package fr.kinjer.kinomod.items;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.init.ItemsMod;
import net.minecraft.item.Item;

public class ItemBismuth extends ItemKino {
	
	public static final String NAME = "bismuth";
	
	public ItemBismuth() {
		super(NAME);
//		ItemsMod.setItemName(this, NAME);
		setCreativeTab(KinoMod.tabKino);
	}

}
