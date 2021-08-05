package fr.kinjer.kinomod.items.equipment;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import fr.kinjer.kinomod.client.gui.GuiAirWater;
import fr.kinjer.kinomod.items.base.BaseKinoBaubleCharm;
import fr.kinjer.kinomod.utils.UtilsKeyBoard;
import fr.kinjer.kinomod.utils.UtilsLocalizer;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBaliumCharm extends BaseKinoBaubleCharm {

	protected Random rand;
	public static final String NAME = "drown";
	public static final String TAG_NAME = "drown air";
	public static final String TAG_BOOLEAN = "doing drown";

	public ItemBaliumCharm() {
		super("balium_charm");
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
		if (itemstack.getItemDamage() == 0 && player.ticksExisted % 39 == 0) {
			player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 40, 1, false, false));
			player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 20 * 13, 0, false, false));
		}

		if (player.isInsideOfMaterial(Material.WATER)) {
			player.setAir(300);
			GuiIngameForge.renderAir = false;
			GuiAirWater.renderAirWater = false;
			player.getEntityData().setInteger(TAG_NAME, 300);

		}

		if (!player.isInsideOfMaterial(Material.WATER)) {

			GuiIngameForge.renderAir = true;
			GuiAirWater.renderAirWater = true;

			int respiration = EnchantmentHelper.getRespirationModifier(player);
			int air = player.getEntityData().getInteger(TAG_NAME);
			air = ((respiration > 0) && (player.getRNG().nextInt(respiration + 1) > 0) ? air : air - 1);

			if (air == -20) {
				air = 0;

				player.attackEntityFrom(DamageSource.DROWN, 2.0F);
			}

			player.getEntityData().setInteger(TAG_NAME, air);
		}

	}

	public int decreaseAirSupply(int air, EntityLivingBase player) {

		int i = EnchantmentHelper.getRespirationModifier(player);
		return i > 0 && this.rand.nextInt(i + 1) > 0 ? air : air - 1;
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> l, ITooltipFlag flagIn) {
		if (!UtilsKeyBoard.isShiftKeyDown()) {
			l.add(UtilsLocalizer.shiftDetails());
			return;
		}

		l.add("* §a" + UtilsLocalizer.localize(MobEffects.HASTE.getName()) + " §a" + UtilsLocalizer.numberLocalize(2));
		l.add("* §a" + UtilsLocalizer.localize(MobEffects.WATER_BREATHING.getName()));
		l.add("* §a" + UtilsLocalizer.localize(MobEffects.NIGHT_VISION.getName()));
		l.add("* §4" + UtilsLocalizer.localize("kinomod.balium_charm.tooltip"));

	}
}
