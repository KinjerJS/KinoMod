package fr.kinjer.kinomod.blocks;

import java.util.Random;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.init.BlocksMod;
import fr.kinjer.kinomod.init.ItemsMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

public class BlockKiniumOre extends BlockKino {
	
	public static final String NAME = "kinium_ore";
	
	public BlockKiniumOre() {
		super(NAME, Material.IRON);
		setHardness(1.6f);
		setResistance(1.6f);
		
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return ItemsMod.kinium;
	}

}
