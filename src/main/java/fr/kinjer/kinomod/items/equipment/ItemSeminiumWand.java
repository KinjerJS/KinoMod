package fr.kinjer.kinomod.items.equipment;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.items.base.BaseKino;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemSeminiumWand extends BaseKino {

	public static final String NAME = "seminium_wand";

	public ItemSeminiumWand() {
		super(NAME);
		this.maxStackSize = 1;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {

		ItemStack stack = player.getHeldItem(hand);
		if (!world.isRemote) {
			int range = 16;
			int verticalRange = 16;
			int posX = (int) Math.round(player.posX - 0.5f);
			int posY = (int) player.posY;
			int posZ = (int) Math.round(player.posZ - 0.5f);

			for (int ix = posX - range; ix <= posX + range; ix++)
				for (int iz = posZ - range; iz <= posZ + range; iz++)
					for (int iy = posY - verticalRange; iy <= posY + verticalRange; iy++) {
						BlockPos pos = new BlockPos(ix, iy, iz);
						Block block = world.getBlockState(pos).getBlock();
						IBlockState state = world.getBlockState(pos);

						if ((block == Blocks.DIRT || block == Blocks.GRASS)) {
							world.setBlockState(pos, Blocks.FARMLAND.getDefaultState());
							world.scheduleUpdate(pos.toImmutable(), Blocks.FARMLAND, MathHelper.getInt(player.getRNG(), 60, 70));
						}
					}
		}
		player.swingArm(hand);
		return ActionResult.newResult(EnumActionResult.FAIL, stack);
	}
}
