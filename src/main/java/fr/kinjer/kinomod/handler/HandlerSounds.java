package fr.kinjer.kinomod.handler;

import fr.kinjer.kinomod.KinoMod;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class HandlerSounds {
	
	public static final SoundEvent soundcentaur = makeSoundEvent("soundcentaur");
	public static final SoundEvent dash = makeSoundEvent("dash");
	
	private static SoundEvent makeSoundEvent(String name) {
		ResourceLocation loc = new ResourceLocation(KinoMod.MODID, name);
		return new SoundEvent(loc).setRegistryName(loc);
	}
	
	@SubscribeEvent
	public static void registerSounds(RegistryEvent.Register<SoundEvent> evt) {
		evt.getRegistry().registerAll(
				soundcentaur
				);
	}

	public HandlerSounds() {}
}