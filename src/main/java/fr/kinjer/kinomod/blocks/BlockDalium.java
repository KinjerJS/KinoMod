package fr.kinjer.kinomod.blocks;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.blocks.base.BaseBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockDalium extends BaseBlock {
	
	public static final String NAME = "dalium_block";
	
	public BlockDalium() {
		super(NAME, Material.IRON);
		setHardness(1.6f);
		setResistance(1.6f);
		setHarvestLevel("pickaxe", 3);
		
	}

}
