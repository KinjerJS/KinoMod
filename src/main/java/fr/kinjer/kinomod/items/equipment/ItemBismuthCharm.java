package fr.kinjer.kinomod.items.equipment;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import fr.kinjer.kinomod.items.base.BaseKinoBaubleCharm;
import fr.kinjer.kinomod.utils.UtilsKeyBoard;
import fr.kinjer.kinomod.utils.UtilsLocalizer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBismuthCharm extends BaseKinoBaubleCharm {
	
	private static final List<String> damageNegations = new ArrayList<>();

	public ItemBismuthCharm() {
		super("bismuth_charm");
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
		if (itemstack.getItemDamage() == 0 && player.ticksExisted % 39 == 0) {
			player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 40, 0, true, false));
			player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 40, 0, true, false));
			player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 40, 0, true, false));
			player.removePotionEffect(MobEffects.WITHER);
			damageNegations.add(DamageSource.FALL.damageType);

		}
	}
	
	@SubscribeEvent
	public static void onPlayerAttacked(LivingAttackEvent event) {
		if (event.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			if (damageNegations.contains(event.getSource().damageType))
				event.setCanceled(true);
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

		l.add("* §a" + UtilsLocalizer.localize("kinomod.bismuth_charm.toolip"));
		l.add("* §a" + UtilsLocalizer.localize(MobEffects.HASTE.getName()));
		l.add("* §a" + UtilsLocalizer.localize(MobEffects.REGENERATION.getName()));
		l.add("* §a" + UtilsLocalizer.localize(MobEffects.STRENGTH.getName()));
		l.add("* §a" + UtilsLocalizer.localize("kinomod.seminium_charm.tooltip_0"));

	}
}
