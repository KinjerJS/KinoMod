package fr.kinjer.kinomod.blocks;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.init.BlocksMod;
import fr.kinjer.kinomod.init.FluidsMod;
import fr.kinjer.kinomod.init.ItemBlocksMod;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class BlockKiniumFluid extends BlockFluidClassic {
	
	public BlockKiniumFluid(String name, Fluid fluid) {
		super(fluid, Material.LAVA);
		
		BlocksMod.setBlockName(this, name);
//		BlocksMod.blocks.add(this);
		ItemBlocksMod.items.add((ItemBlock) new ItemBlock(this).setRegistryName(this.getRegistryName()).setCreativeTab(KinoMod.tabKino));
	}
	
//	@Override
//	public EnumBlockRenderType getRenderType(IBlockState state) {
//		return EnumBlockRenderType.MODEL;
//	}
	
	

}
