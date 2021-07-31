package fr.kinjer.kinomod.items.equipment;

import java.util.List;

import javax.annotation.Nullable;

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

public class ItemBaliumAdvancedCharm extends BaseKinoBaubleCharm {
	public ItemBaliumAdvancedCharm() {
		super("balium_charm_advanced");
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
		if (itemstack.getItemDamage() == 0 && player.ticksExisted % 39 == 0) {
			player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 40, 3, true, true));
			player.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 40, 0, true, true));
			player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 20 * 13, 0, true, true));
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

		l.add("* " + UtilsLocalizer.localize(MobEffects.HASTE.getName()) + " §7" + UtilsLocalizer.numberLocalize(4));
		l.add("* " + UtilsLocalizer.localize(MobEffects.WATER_BREATHING.getName()));
		l.add("* " + UtilsLocalizer.localize(MobEffects.NIGHT_VISION.getName()));

	}
}
