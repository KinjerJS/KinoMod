package fr.kinjer.kinomod.blocks;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.init.BlocksMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockSeminiumOre extends BlockKino {
	
	public static final String NAME = "seminium_ore";
	
	public BlockSeminiumOre() {
		super(NAME, Material.IRON);
		setHardness(1.6f);
		setResistance(1.6f);
		
	}

}
