package fr.kinjer.kinomod.init;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.config.Config;
import fr.kinjer.kinomod.entity.*;
import fr.kinjer.kinomod.entity.projectile.*;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class InitEntity {

	public static void registerEntities() {
		registerEntity("centaur", EntityCentaur.class, Config.ENTITY_CENTAUR, 50, 0xFF865A, 0x9B4D00);
		registerEntity("ghast_boss_d", EntityGhastBossD.class, Config.ENTITY_GHAST_BOSS, 50, 0xFF865A, 0x9B4D00);
		EntityRegistry.registerModEntity(new ResourceLocation(KinoMod.MODID, "bismuth_ball"), EntityBismuthBall.class, "supremium_arrow", Config.ENTITY_BISMUTH_BALL, KinoMod.MODID, 64, 1, true);
	}

	private static void registerEntity(String name, Class<? extends Entity> entity, int id, int range, int color1,
			int color2) {
		EntityRegistry.registerModEntity(new ResourceLocation(KinoMod.MODID, name), entity, name, id, KinoMod.MODID,
				range, 1, true, color1, color2);
	}

	private static void registerProjectile(String name, Class<? extends Entity> entity, int id, Item item) {
		EntityRegistry.registerModEntity(new ResourceLocation(KinoMod.MODID, name), entity, name, id, KinoMod.MODID, 64,
				10, true);
	}
}
