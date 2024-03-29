package fr.kinjer.kinomod.blocks;

import java.util.Random;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.blocks.base.BaseBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

public class BlockKiniumOre extends BaseBlock {
	
	public String NAME;
	
	public BlockKiniumOre(String name) {
		super(name, Material.IRON);
		this.NAME = name;
		setHardness(38.0f);
		setResistance(1.6f);
	}

}
