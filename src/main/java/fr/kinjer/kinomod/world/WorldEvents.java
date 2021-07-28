package fr.kinjer.kinomod.world;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.init.PotionInit;
import net.minecraft.init.MobEffects;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class WorldEvents {

	@SubscribeEvent
	public static void bleedingActive(LivingUpdateEvent event) {

		boolean isActive = false;
		if (event.getEntityLiving().isPotionActive(PotionInit.BLEEDING_EFFECT))
			isActive = true;

		if (isActive) {

			if (event.getEntityLiving().isPotionActive(MobEffects.REGENERATION)) {

				event.getEntityLiving().removeActivePotionEffect(MobEffects.REGENERATION);

			}

			if (event.getEntityLiving().isPotionActive(MobEffects.ABSORPTION)) {

				event.getEntityLiving().removeActivePotionEffect(MobEffects.ABSORPTION);

			}

			event.getEntityLiving().attackEntityFrom(KinoMod.Bismuth, 1.0f);
		}
	}
}