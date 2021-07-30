package fr.kinjer.kinomod.blocks;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.blocks.base.BlockKino;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockBalium extends BlockKino {
	
	public static final String NAME = "balium_block";
	
	public BlockBalium() {
		super(NAME, Material.IRON);
		setHardness(1.6f);
		setResistance(1.6f);
		setHarvestLevel("pickaxe", 3);
		
	}

}
