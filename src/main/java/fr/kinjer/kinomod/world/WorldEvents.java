package fr.kinjer.kinomod.world;

import fr.kinjer.kinomod.KinoMod.DamageSourceBismuth;
import fr.kinjer.kinomod.init.PotionInit;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.PotionEvent.PotionRemoveEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

@EventBusSubscriber
public class WorldEvents {

	private static final DamageSource DamageSourceBismuth = new DamageSource("Bismuth").setDamageAllowedInCreativeMode()
			.setDamageBypassesArmor().setDamageIsAbsolute().setMagicDamage();

	@SubscribeEvent
	public static void bleedingActive(PlayerTickEvent event) {

		boolean isActive = false;
		if (event.player.isPotionActive(PotionInit.BLEEDING_EFFECT))
			isActive = true;

		if (isActive) {

			if (event.player.isPotionActive(MobEffects.REGENERATION)) {

				event.player.removeActivePotionEffect(MobEffects.REGENERATION);

			}
			
			event.player.attackEntityFrom(DamageSourceBismuth, 1.0f);
		}
	}
}