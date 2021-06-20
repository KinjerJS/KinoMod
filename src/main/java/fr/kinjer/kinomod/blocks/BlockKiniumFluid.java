package fr.kinjer.kinomod.blocks;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.init.BlocksMod;
import fr.kinjer.kinomod.init.FluidsMod;
import fr.kinjer.kinomod.init.ItemBlocksMod;
import fr.kinjer.kinomod.utils.MaterialsMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class BlockKiniumFluid extends BlockFluidClassic {
	
	public BlockKiniumFluid(String name, Fluid fluid) {
		super(fluid, Material.LAVA);
		BlocksMod.setBlockName(this, name);
	}
	
	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		super.onBlockAdded(world, pos, state);
		this.checkForMixing(world, pos, state);
	}
	
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block neighborBlock,
			BlockPos neighbourPos) {
		super.neighborChanged(state, world, pos, neighborBlock, neighbourPos);
		this.checkForMixing(world, pos, state);
	}
	
	public boolean checkForMixing(World worldIn, BlockPos pos, IBlockState state)
    {
        if (this.blockMaterial == Material.LAVA)
        {
            boolean flag = false;

            for (EnumFacing enumfacing : EnumFacing.values())
            {
                if (enumfacing != EnumFacing.DOWN && (worldIn.getBlockState(pos.offset(enumfacing)).getMaterial() == Material.WATER || worldIn.getBlockState(pos.offset(enumfacing)).getMaterial() == Material.LAVA))
                {
                    flag = true;
                    break;
                }
            }

            if (flag)
            {
                Integer integer = (Integer)state.getValue(LEVEL);

                if (integer.intValue() == 0)
                {
                    worldIn.setBlockState(pos, net.minecraftforge.event.ForgeEventFactory.fireFluidPlaceBlockEvent(worldIn, pos, pos, BlocksMod.gabbro_polished.getDefaultState()));
//                    this.triggerMixEffects(worldIn, pos);
                    return true;
                }

                if (integer.intValue() > 0)
                {
                    worldIn.setBlockState(pos, net.minecraftforge.event.ForgeEventFactory.fireFluidPlaceBlockEvent(worldIn, pos, pos, BlocksMod.gabbro.getDefaultState()));
//                    this.triggerMixEffects(worldIn, pos);
                    return true;
                }
            }
        }

        return false;
    }

	
//	@Override
//	public EnumBlockRenderType getRenderType(IBlockState state) {
//		return EnumBlockRenderType.MODEL;
//	}
	
	

}
