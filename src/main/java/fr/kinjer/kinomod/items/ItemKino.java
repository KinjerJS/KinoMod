package fr.kinjer.kinomod.items;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.init.ItemsMod;
import net.minecraft.item.Item;

public class ItemKino extends Item{
	
	private String NAME;
	
	public ItemKino(String name) {
		this.NAME = name;
		ItemsMod.setItemName(this, name);
		setCreativeTab(KinoMod.tabKino);
	}
	
	public String getName() {
		return this.NAME;
	}

}
