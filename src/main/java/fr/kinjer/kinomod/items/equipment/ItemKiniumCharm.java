package fr.kinjer.kinomod.items.equipment;

import java.util.List;

import javax.annotation.Nullable;

import fr.kinjer.kinomod.items.base.ItemKinoCharm;
import fr.kinjer.kinomod.utils.UtilsKeyBoard;
import fr.kinjer.kinomod.utils.UtilsLocalizer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemKiniumCharm extends ItemKinoCharm {
	public ItemKiniumCharm() {
		super("kinium_charm");
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
		if (itemstack.getItemDamage() == 0 && player.ticksExisted % 39 == 0) {
			player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 40, 1, true, true));
			player.setFire(3);
		}
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> l, ITooltipFlag flagIn) {
		if (!UtilsKeyBoard.isShiftKeyDown()) {
			l.add(UtilsLocalizer.shiftDetails());
			return;
		}

		l.add("* " + UtilsLocalizer.localize(MobEffects.STRENGTH.getName()) + " §7" + UtilsLocalizer.numberLocalize(2));
		l.add("* §4" + UtilsLocalizer.localize("kinomod.kinium_charm.tooltip"));

	}
}
