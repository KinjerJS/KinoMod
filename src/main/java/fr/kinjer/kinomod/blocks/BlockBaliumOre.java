package fr.kinjer.kinomod.blocks;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.init.BlocksMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockBaliumOre extends BlockKino {
	
	public static final String NAME = "balium_ore";
	
	public BlockBaliumOre() {
		super(NAME, Material.IRON);
		setHardness(1.6f);
		setResistance(1.6f);
		
	}

}
