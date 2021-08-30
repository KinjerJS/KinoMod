package fr.kinjer.kinomod.init;

import java.util.ArrayList;
import java.util.List;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.blocks.BlockBalium;
import fr.kinjer.kinomod.blocks.BlockBaliumOre;
import fr.kinjer.kinomod.blocks.BlockBismuth;
import fr.kinjer.kinomod.blocks.BlockDalium;
import fr.kinjer.kinomod.blocks.BlockDaliumOre;
import fr.kinjer.kinomod.blocks.BlockGabbro;
import fr.kinjer.kinomod.blocks.BlockGabbroPolished;
import fr.kinjer.kinomod.blocks.BlockKinium;
import fr.kinjer.kinomod.blocks.fluid.FluidBlockKinium;
import fr.kinjer.kinomod.blocks.BlockKiniumOre;
import fr.kinjer.kinomod.blocks.BlockMachineFrame;
import fr.kinjer.kinomod.blocks.BlockSeminium;
import fr.kinjer.kinomod.blocks.BlockSeminiumOre;
import fr.kinjer.kinomod.blocks.BlockSpecialBalium;
import fr.kinjer.kinomod.blocks.BlockSpecialDalium;
import fr.kinjer.kinomod.blocks.BlockSpecialKinium;
import fr.kinjer.kinomod.blocks.BlockSpecialSeminium;
import fr.kinjer.kinomod.blocks.container.BlockCrystaliser;
import fr.kinjer.kinomod.blocks.container.BlockExtractor;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class InitBlocks {
	
	public static List<Block> blocks = new ArrayList<>();

	//Ore
	public static final Block kinium_block = new BlockKinium();
	public static final Block kinium_ore = new BlockKiniumOre("kinium_ore");
	public static final Block kinium_ore_end = new BlockKiniumOre("kinium_ore_end");
	public static final Block kinium_ore_nether = new BlockKiniumOre("kinium_ore_nether");
	
	public static final Block balium_block = new BlockBalium();
	public static final Block balium_ore = new BlockBaliumOre("balium_ore");
	public static final Block balium_ore_end = new BlockBaliumOre("balium_ore_end");
	public static final Block balium_ore_nether = new BlockBaliumOre("balium_ore_nether");
	
	public static final Block seminium_block = new BlockSeminium();
	public static final Block seminium_ore = new BlockSeminiumOre("seminium_ore");
	public static final Block seminium_ore_end = new BlockSeminiumOre("seminium_ore_end");
	public static final Block seminium_ore_nether = new BlockSeminiumOre("seminium_ore_nether");
	
	public static final Block dalium_block = new BlockDalium();
	public static final Block dalium_ore = new BlockDaliumOre("dalium_ore");
	public static final Block dalium_ore_end = new BlockDaliumOre("dalium_ore_end");
	public static final Block dalium_ore_nether = new BlockDaliumOre("dalium_ore_nether");
	
	public static final Block bismuth_block = new BlockBismuth();
	
	//Fluid
	public static final Block kinium_molten = new FluidBlockKinium("kinium_molten", InitFluids.KINIUM);
	public static final Block balium_molten = new FluidBlockKinium("balium_molten", InitFluids.BALIUM);
	public static final Block seminium_molten = new FluidBlockKinium("seminium_molten", InitFluids.SEMINIUM);
	public static final Block dalium_molten = new FluidBlockKinium("dalium_molten", InitFluids.DALIUM);
	
	//Blocks
	public static final Block gabbro = new BlockGabbro();
	public static final Block gabbro_polished = new BlockGabbroPolished();
	public static final Block machine_frame = new BlockMachineFrame();
	
	public static final Block special_kinium = new BlockSpecialKinium();
	public static final Block special_balium = new BlockSpecialBalium();
	public static final Block special_seminium = new BlockSpecialSeminium();
	public static final Block special_dalium = new BlockSpecialDalium();
	
	//Machines
	public static final Block extractor = new BlockExtractor();
	public static final Block crystaliser = new BlockCrystaliser();
	
	public static void setBlockName(Block block, String name)
	{
	    blocks.add(block.setRegistryName(KinoMod.MODID, name).setUnlocalizedName(KinoMod.MODID + "." + name));
	    InitItemBlocks.items.add((ItemBlock) new ItemBlock(block).setRegistryName(KinoMod.MODID, name));
	}
}
