package fr.kinjer.kinomod.init;

import java.util.ArrayList;
import java.util.List;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.blocks.BlockBalium;
import fr.kinjer.kinomod.blocks.BlockBaliumOre;
import fr.kinjer.kinomod.blocks.BlockBismuth;
import fr.kinjer.kinomod.blocks.BlockDalium;
import fr.kinjer.kinomod.blocks.BlockDaliumOre;
import fr.kinjer.kinomod.blocks.BlockKinium;
import fr.kinjer.kinomod.blocks.BlockKiniumFluid;
import fr.kinjer.kinomod.blocks.BlockKiniumOre;
import fr.kinjer.kinomod.blocks.BlockSeminium;
import fr.kinjer.kinomod.blocks.BlockSeminiumOre;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class BlocksMod {
	
	public static List<Block> blocks = new ArrayList<>();

	public static final Block kinium_block = new BlockKinium();
	public static final Block kinium_ore = new BlockKiniumOre();
	
	public static final Block balium_block = new BlockBalium();
	public static final Block balium_ore = new BlockBaliumOre();
	
	public static final Block seminium_ore = new BlockSeminiumOre();
	public static final Block seminium_block = new BlockSeminium();

	public static final Block dalium_ore = new BlockDaliumOre();
	public static final Block dalium_block = new BlockDalium();
	
	public static final Block bismuth_block = new BlockBismuth();
	
	public static final Block kinium_molten = new BlockKiniumFluid("kinium_molten", FluidsMod.KINIUM);
	public static final Block balium_molten = new BlockKiniumFluid("balium_molten", FluidsMod.BALIUM);
	public static final Block seminium_molten = new BlockKiniumFluid("seminium_molten", FluidsMod.SEMINIUM);
	public static final Block dalium_molten = new BlockKiniumFluid("dalium_molten", FluidsMod.DALIUM);
	
	
	public static void setBlockName(Block block, String name)
	{
	    blocks.add(block.setRegistryName(KinoMod.MODID, name).setUnlocalizedName(KinoMod.MODID + "." + name));
	    
	}
	
}
