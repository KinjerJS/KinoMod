package fr.kinjer.kinomod.world;

import fr.kinjer.kinomod.init.PotionInit;
import net.minecraftforge.event.entity.living.PotionEvent.PotionRemoveEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

@EventBusSubscriber
public class WorldEvents {

	@SubscribeEvent
	public static void bleedingActive(PlayerTickEvent event) {

		boolean isActive = false;
		if (event.player.isPotionActive(PotionInit.BLEEDING_EFFECT))
			isActive = true;

		if (isActive) {

			// Enlève Régénération + Fait des dégats

		}
	}
}
