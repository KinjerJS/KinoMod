package fr.kinjer.kinomod.init;

import fr.kinjer.kinomod.items.ItemArmorKino;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventsMod {
	
	@SubscribeEvent
	public void isOnFire(LivingDamageEvent e) {
		System.out.println(e.getSource().getDamageType());
		if(e.getEntity() instanceof EntityPlayer && ItemArmorKino.isFullArmor((EntityPlayer) e.getEntity()))
		if(e.getSource() == DamageSource.ON_FIRE || e.getSource() == DamageSource.IN_FIRE || e.getSource() == DamageSource.LAVA) {
			e.setCanceled(true);
		}
	}

}
