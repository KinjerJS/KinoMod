package fr.kinjer.kinomod.blocks;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.blocks.base.BaseBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockSpecialSeminium extends BaseBlock {
	
	public static final String NAME = "special_seminium";
	
	public BlockSpecialSeminium() {
		super(NAME, Material.IRON);
		setHardness(1.6f);
		setResistance(1.6f);
		setHarvestLevel("pickaxe", 3);
		
	}

}
