package fr.kinjer.kinomod.blocks;

import fr.kinjer.kinomod.blocks.base.BlockKino;
import net.minecraft.block.material.Material;

public class BlockGabbro extends BlockKino {
	
	public static final String NAME = "gabbro";
	
	public BlockGabbro() {
		super(NAME, Material.IRON);
		setHardness(1.6f);
		setResistance(1.6f);
		setHarvestLevel("pickaxe", 1);
	}

}
