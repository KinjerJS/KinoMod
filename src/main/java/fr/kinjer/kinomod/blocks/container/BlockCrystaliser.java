package fr.kinjer.kinomod.blocks.container;

import net.minecraft.block.Block;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.init.InitBlocks;
import fr.kinjer.kinomod.tileentities.TileCrystaliser;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCrystaliser extends BlockContainer {

	public BlockCrystaliser() {
			super(Material.ROCK);
			setCreativeTab(KinoMod.tabKino);
			InitBlocks.setBlockName(this, "crystaliser");
		}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileCrystaliser();
	}

	@Override
	public boolean hasTileEntity() {
		return true;
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntity tileentity = worldIn.getTileEntity(pos);

		if (tileentity instanceof TileCrystaliser) {
			InventoryHelper.dropInventoryItems(worldIn, pos, (TileCrystaliser) tileentity);
		}

		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			TileEntity tileentity = world.getTileEntity(pos);

			if (tileentity instanceof TileCrystaliser) {
				player.openGui(KinoMod.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
			}
		}
		return true;

	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		if (stack.hasDisplayName()) {
			TileEntity tileentity = worldIn.getTileEntity(pos);
			if (tileentity instanceof TileCrystaliser) {
				((TileCrystaliser) tileentity).setCustomName(stack.getDisplayName());
			}
		}
	}

}
