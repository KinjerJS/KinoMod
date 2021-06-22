package fr.kinjer.kinomod.blocks;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.init.BlocksMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockBaliumOre extends BlockKino {
	
	public String NAME;
	
	public BlockBaliumOre(String name) {
		super(name, Material.IRON);
		this.NAME = name;
		setHardness(1.6f);
		setResistance(1.6f);
		
	}

}
