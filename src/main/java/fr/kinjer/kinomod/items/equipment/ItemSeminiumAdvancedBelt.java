package fr.kinjer.kinomod.items.equipment;

import java.util.List;

import javax.annotation.Nullable;

import fr.kinjer.kinomod.items.base.BaseKinoBaubleBelt;
import fr.kinjer.kinomod.utils.UtilsKeyBoard;
import fr.kinjer.kinomod.utils.UtilsLocalizer;
import fr.kinjer.kinomod.world.WorldEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSeminiumAdvancedBelt extends BaseKinoBaubleBelt {

	public ItemSeminiumAdvancedBelt() {
		super("seminium_belt_advanced");
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
		
		if (player.isPotionActive(MobEffects.NAUSEA)) {
			player.removeActivePotionEffect(MobEffects.NAUSEA);
		}
		
		World world = player.getEntityWorld();
		if (!world.isRemote) {
			int range = 20;
			int verticalRange = 14;
			int posX = (int) Math.round(player.posX - 0.5f);
			int posY = (int) player.posY;
			int posZ = (int) Math.round(player.posZ - 0.5f);

			for (int ix = posX - range; ix <= posX + range; ix++)
				for (int iz = posZ - range; iz <= posZ + range; iz++)
					for (int iy = posY - verticalRange; iy <= posY + verticalRange; iy++) {
						BlockPos pos = new BlockPos(ix, iy, iz);
						Block block = world.getBlockState(pos).getBlock();
						IBlockState state = world.getBlockState(pos);
						
						if (block instanceof BlockCrops) {
							
							if (itemstack.getItemDamage() == 0 && player.ticksExisted % 6 == 0) {
								if(state.getValue(((BlockCrops) block).AGE) < ((BlockCrops) block).getMaxAge()) {
									block.updateTick(world, pos, state, world.rand);
									world.playEvent(2005, pos, 0);
								}
							}
						}
					}
		}
	}
	
	@Override
	public boolean hasEffect(ItemStack par1ItemStack) {
		return true;
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> l, ITooltipFlag flagIn) {

		if (!UtilsKeyBoard.isShiftKeyDown()) {
			l.add(UtilsLocalizer.shiftDetails());
			return;
		}

		l.add("* �a" + UtilsLocalizer.localize("kinomod.seminium_belt.toolip_0"));
		l.add("* �a" + UtilsLocalizer.localize("kinomod.seminium_belt.toolip_1"));

	}
}
