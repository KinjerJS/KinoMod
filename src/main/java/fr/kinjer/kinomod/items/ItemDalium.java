package fr.kinjer.kinomod.items;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.init.ItemsMod;
import net.minecraft.item.Item;

public class ItemDalium extends ItemKino {
	
	public static final String NAME = "dalium";
	
	public ItemDalium() {
		super(NAME);
//		ItemsMod.setItemName(this, NAME);
		setCreativeTab(KinoMod.tabKino);
	}

}
