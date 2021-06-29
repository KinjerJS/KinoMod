package fr.kinjer.kinomod.init;

import fr.kinjer.kinomod.KinoMod;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class EntityInit {
	
	private static void registerEntity(String name)
	{
		EntityRegistry.registerModEntity(new ResourceLocation(KinoMod.MODID + ":" + name), null, null, 0, null, 0, 0, false, 0, 0);
	}

}
