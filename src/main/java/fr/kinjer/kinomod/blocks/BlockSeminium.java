package fr.kinjer.kinomod.blocks;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.blocks.base.BlockKino;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockSeminium extends BlockKino {
	
	public static final String NAME = "seminium_block";
	
	public BlockSeminium() {
		super(NAME, Material.IRON);
		setHardness(1.6f);
		setResistance(1.6f);
		setHarvestLevel("pickaxe", 3);
		
	}

}
