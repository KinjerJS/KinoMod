package fr.kinjer.kinomod.blocks;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.init.BlocksMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockKino extends Block{
	
	public BlockKino(String name, Material material) {
		super(material);
		setCreativeTab(KinoMod.tabKino);
		BlocksMod.setBlockName(this, name);
		
	}

}
