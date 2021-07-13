package fr.kinjer.kinomod.init;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.entity.EntityCentaur;
import fr.kinjer.kinomod.entity.EntityGhastBossD;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class EntityInit {
	
	public static final int ENTITY_CENTAUR = 120;
	public static final int ENTITY_GHAST_BOSS = 121;
	
	public static void registerEntities() {
		registerEntity("centaur", 
				EntityCentaur.class, 
				ENTITY_CENTAUR, 50, 0xFF865A, 0x9B4D00);
		registerEntity("ghast_boss_d", 
				EntityGhastBossD.class, 
				ENTITY_GHAST_BOSS, 50, 0xFF865A, 0x9B4D00);
	}
		
	private static void registerEntity(String name, Class<? extends Entity> entity, int id, int range, int color1, int color2)
	{
		EntityRegistry.registerModEntity(
				new ResourceLocation(KinoMod.MODID, name), 
				entity, name, id,
				KinoMod.MODID, range, 1, true, color1, color2);
	}

}
