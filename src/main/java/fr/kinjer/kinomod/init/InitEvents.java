package fr.kinjer.kinomod.init;

import fr.kinjer.kinomod.items.equipment.armor.ItemArmorKino;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class InitEvents {
	
	@SubscribeEvent
	public void onDamage(LivingAttackEvent e) {
		ItemArmorKino.onPlayerAttacked(e);
	}
}
