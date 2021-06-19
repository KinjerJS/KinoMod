package fr.kinjer.kinomod.itemblocks;

import fr.kinjer.kinomod.init.BlocksMod;
import fr.kinjer.kinomod.init.ItemBlocksMod;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ItemBlockKino extends ItemBlock{
	
	public ItemBlockKino(Block block) {
		super(block);
		ItemBlocksMod.items.add((ItemBlock) this.setRegistryName(block.getRegistryName()));
		
	}
}
