package fr.kinjer.kinomod.items.equipment;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import fr.kinjer.kinomod.items.base.BaseKinoBelt;
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

public class ItemDaliumAdvancedBelt extends BaseKinoBelt {

private static final List<String> damageNegations = new ArrayList<>();
	
	public ItemDaliumAdvancedBelt() {
		super("dalium_belt_advanced");
		damageNegations.add(DamageSource.IN_FIRE.damageType);
		damageNegations.add(DamageSource.ON_FIRE.damageType);
		damageNegations.add(DamageSource.LAVA.damageType);
		damageNegations.add(DamageSource.HOT_FLOOR.damageType);
	    damageNegations.add(DamageSource.DRAGON_BREATH.damageType);
	    damageNegations.add(DamageSource.FIREWORKS.damageType);
	}

	@Override
	public void onWornTick(ItemStack stack, EntityLivingBase player) {
		if(player.isBurning()) {
			player.extinguish();
		}
		
		player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 5, 0, false, false));
	}
	
	@SubscribeEvent
	public static void onPlayerAttacked(LivingAttackEvent event) {
		if (event.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			if (damageNegations.contains(event.getSource().damageType))
				event.setCanceled(true);
		}
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> l, ITooltipFlag flagIn) {

		if (!UtilsKeyBoard.isShiftKeyDown()) {
			l.add(UtilsLocalizer.shiftDetails());
			return;
		}

		l.add("* §a" + UtilsLocalizer.localize("kinomod.dalium_belt.toolip_0"));
		l.add("* §a" + UtilsLocalizer.localize("kinomod.dalium_belt.toolip_1"));
		l.add("* §a" + UtilsLocalizer.localize(MobEffects.SPEED.getName()));

	}
}
