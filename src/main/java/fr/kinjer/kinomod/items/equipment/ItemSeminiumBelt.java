package fr.kinjer.kinomod.items.equipment;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.mojang.authlib.properties.Property;

import fr.kinjer.kinomod.items.base.BaseKinoBelt;
import fr.kinjer.kinomod.utils.UtilsKeyBoard;
import fr.kinjer.kinomod.utils.UtilsLocalizer;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

public class ItemSeminiumBelt extends BaseKinoBelt {

	public ItemSeminiumBelt() {
		super("seminium_belt");
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
		
		player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 13*20, 0, false, false));
		player.addPotionEffect(new PotionEffect(MobEffects.UNLUCK, 5, 0, false, false));
		
		World world = player.getEntityWorld();
		if (!world.isRemote) {
			int range = 10;
			int verticalRange = 7;
			int posX = (int) Math.round(player.posX - 0.5f);
			int posY = (int) player.posY;
			int posZ = (int) Math.round(player.posZ - 0.5f);

			for (int ix = posX - range; ix <= posX + range; ix++)
				for (int iz = posZ - range; iz <= posZ + range; iz++)
					for (int iy = posY - verticalRange; iy <= posY + verticalRange; iy++) {
						Block block = world.getBlockState(new BlockPos(ix, iy, iz)).getBlock();
						IBlockState state = world.getBlockState(new BlockPos(ix, iy, iz));

						if (block instanceof BlockCrops) {
							
							if (itemstack.getItemDamage() == 0 && player.ticksExisted % 14 == 0) {
								if(state.getValue(((BlockCrops) block).AGE) < ((BlockCrops) block).getMaxAge()) {
									block.updateTick(world, new BlockPos(ix, iy, iz), state, world.rand);
									world.playEvent(2005, new BlockPos(ix, iy, iz), 0);
								}
							}
						}
					}
		}
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> l, ITooltipFlag flagIn) {

		if (!UtilsKeyBoard.isShiftKeyDown()) {
			l.add(UtilsLocalizer.shiftDetails());
			return;
		}

		l.add("* §a" + UtilsLocalizer.localize("kinomod.seminium_belt.toolip_0"));
		l.add("* §4" + UtilsLocalizer.localize(MobEffects.NAUSEA.getName()));
		l.add("* §4" + UtilsLocalizer.localize(MobEffects.UNLUCK.getName()));

	}
}
