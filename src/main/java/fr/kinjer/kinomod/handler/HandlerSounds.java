package fr.kinjer.kinomod.handler;

import fr.kinjer.kinomod.KinoMod;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

public class HandlerSounds {
	
	public static SoundEvent ENTITY_CENTAUR_AMBIENT, ENTITY_CENTAUR_HURT, ENTITY_CENTAUR_DEATH;
	public static SoundEvent DASH;
	
	private static SoundEvent registerSound (String name) {
		ResourceLocation location = new ResourceLocation(KinoMod.MODID, name);
		SoundEvent event = new SoundEvent(location);
		event.setRegistryName(location);
		ForgeRegistries.SOUND_EVENTS.register(event);
		return event;
	}
	
	@SubscribeEvent
	public static void registerSounds() {
		ENTITY_CENTAUR_AMBIENT = registerSound("entity.centaur.ambient");
		ENTITY_CENTAUR_HURT = registerSound("entity.centaur.hurt");
		ENTITY_CENTAUR_DEATH = registerSound("entity.centaur.death");
		DASH = registerSound("misc.dash");
	}

	public HandlerSounds() {}
}