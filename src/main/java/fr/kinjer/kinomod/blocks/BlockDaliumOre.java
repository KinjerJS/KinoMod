package fr.kinjer.kinomod.blocks;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.blocks.base.BaseBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockDaliumOre extends BaseBlock {
	
	public String NAME;
	
	public BlockDaliumOre(String name) {
		super(name, Material.IRON);
		this.NAME = name;
		setHardness(38.0f);
		setResistance(1.6f);
	}

}
