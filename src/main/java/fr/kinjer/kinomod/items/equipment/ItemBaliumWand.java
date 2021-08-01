package fr.kinjer.kinomod.items.equipment;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.items.base.BaseKino;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemBaliumWand extends BaseKino {

	public static final String NAME = "balium_wand";

	public ItemBaliumWand() {
		super(NAME);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {

		ItemStack stack = player.getHeldItem(hand);
		if (!world.isRemote) {
			int range = 12;
			int verticalRange = 12;
			int posX = (int) Math.round(player.posX - 0.5f);
			int posY = (int) player.posY;
			int posZ = (int) Math.round(player.posZ - 0.5f);

			for (int ix = posX - range; ix <= posX + range; ix++)
				for (int iz = posZ - range; iz <= posZ + range; iz++)
					for (int iy = posY - verticalRange; iy <= posY + verticalRange; iy++) {
						Block block = world.getBlockState(new BlockPos(ix, iy, iz)).getBlock();
						BlockPos pos = new BlockPos(ix, iy, iz);
						IBlockState state = world.getBlockState(pos);

						if ((block == Blocks.WATER || block == Blocks.FLOWING_WATER)) {
							world.setBlockToAir(pos);
							return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
						}
					}
		}
		player.swingArm(hand);
		return ActionResult.newResult(EnumActionResult.FAIL, stack);
	}
}
