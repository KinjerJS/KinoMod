package fr.kinjer.kinomod.items;

import java.util.List;

import javax.annotation.Nullable;

import fr.kinjer.kinomod.init.PotionInit;
import fr.kinjer.kinomod.utils.KeyBoard;
import fr.kinjer.kinomod.utils.Localizer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSeminiumCharm extends ItemCharm {
	public ItemSeminiumCharm() {
		super("seminium_charm");
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
		if (itemstack.getItemDamage() == 0 && player.ticksExisted % 39 == 0) {
			player.addPotionEffect(new PotionEffect(MobEffects.POISON, 40, 1, true, true));
			player.removePotionEffect(MobEffects.WITHER);
			player.removePotionEffect(PotionInit.BLEEDING_EFFECT);
		}
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> l, ITooltipFlag flagIn) {
		if (!KeyBoard.isShiftKeyDown()) {
			l.add(Localizer.shiftDetails());
			return;
		}

		l.add("* " + Localizer.localize("kinomod.charmpoison.damageremovewither"));
		l.add("* " + Localizer.localize("kinomod.charmpoison.damageremovebleeding"));
		l.add("* §4" + Localizer.localize(MobEffects.POISON.getName()) + " " + Localizer.numberLocalize(2));

	}
}
