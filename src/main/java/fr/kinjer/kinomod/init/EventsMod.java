package fr.kinjer.kinomod.init;

import fr.kinjer.kinomod.items.ItemArmorKino;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventsMod {
	
	@SubscribeEvent
	public void onDamage(LivingAttackEvent e) {
		ItemArmorKino.onPlayerAttacked(e);
	}

}
