package fr.kinjer.kinomod.blocks;

import fr.kinjer.kinomod.blocks.base.BaseBlock;
import net.minecraft.block.material.Material;

public class BlockGabbroPolished extends BaseBlock {
	
	public static final String NAME = "gabbro_polished";
	
	public BlockGabbroPolished() {
		super(NAME, Material.IRON);
		setHardness(1.6f);
		setResistance(1.6f);
		setHarvestLevel("pickaxe", 1);
	}

}
