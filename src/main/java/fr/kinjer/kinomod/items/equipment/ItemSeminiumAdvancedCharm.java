package fr.kinjer.kinomod.items.equipment;

import java.util.List;

import javax.annotation.Nullable;

import fr.kinjer.kinomod.init.InitPotion;
import fr.kinjer.kinomod.items.base.BaseKinoBaubleCharm;
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

public class ItemSeminiumAdvancedCharm extends BaseKinoBaubleCharm {
	public ItemSeminiumAdvancedCharm() {
		super("seminium_charm_advanced");
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
		if (itemstack.getItemDamage() == 0 && player.ticksExisted % 39 == 0) {
			player.removePotionEffect(MobEffects.WITHER);
			player.removePotionEffect(InitPotion.BLEEDING_EFFECT);
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

		l.add("* �a" + UtilsLocalizer.localize("kinomod.seminium_charm.tooltip_0"));
		l.add("* �a" + UtilsLocalizer.localize("kinomod.seminium_charm.tooltip_1"));

	}
}
