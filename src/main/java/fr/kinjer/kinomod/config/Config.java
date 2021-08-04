package fr.kinjer.kinomod.config;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import fr.kinjer.kinomod.KinoMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;
import java.util.*;

public class Config {

	public static Configuration config;

	private static File dirConfigurationRegistries;

	public static int ENTITY_CENTAUR = 120;
	public static int ENTITY_GHAST_BOSS = 121;
	public static int ENTITY_BISMUTH_BALL = 122;

	public static int gabbroAmount = 70, gabbroVeinSize = 30;
	
	public static int kiniumAmountOverworld = 3;
	public static int kiniumAmountNether = 5;
	public static int kiniumAmountEnd = 7;
	public static int seminiumAmountOverworld = 3;
	public static int seminiumAmountNether = 5;
	public static int seminiumAmountEnd = 7;
	public static int daliumAmountOverworld = 3;
	public static int daliumAmountNether = 5;
	public static int daliumAmountEnd = 7;
	public static int baliumAmountOverworld = 3;
	public static int baliumAmountNether = 5;
	public static int baliumAmountEnd = 7;
	
	public static int kiniumWandRange = 10, kiniumWandVerticalRange = 10, kiniumWandCooldown = 30;
	public static int seminiumWandRange = 15, seminiumWandVerticalRange = 15, seminiumWandCooldown = 5;
	public static int baliumWandRange = 15, baliumWandVerticalRange = 15, baliumWandCooldown = 5;
	public static int daliumWandRange = 15, daliumWandVerticalRange = 15, daliumWandCooldown = 5;
	

	private static List<ConfigEntry> dynamicConfigEntries = new LinkedList<>();
	private static List<ConfigDataAdapter<?>> dataAdapters = new LinkedList<>();

	private static Map<String, Configuration> cachedConfigs = new HashMap<>();

	public static void loadAndSetup(File file) {
		config = new Configuration(file);
		config.load();
		loadData();
		config.save();
		cachedConfigs.put(KinoMod.MODID, config);

		MinecraftForge.EVENT_BUS.register(new Config());
	}

	@SubscribeEvent
	public void onCfgChange(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (KinoMod.MODID.equals(event.getModID())) {
			Configuration cfg = cachedConfigs.get(event.getConfigID());
			if (cfg != null) {
				cfg.save();

				loadData();
				loadConfigRegistries(ConfigDataAdapter.LoadPhase.PRE_INIT);
				loadConfigRegistries(ConfigDataAdapter.LoadPhase.INIT);
				loadConfigRegistries(ConfigDataAdapter.LoadPhase.POST_INIT);
			}
		}
	}

	public static void addDynamicEntry(ConfigEntry entry) {
		if (config != null) {
			entry.loadFromConfig(config);
			config.save();
		}
		dynamicConfigEntries.add(entry);
	}

	public static void addDataRegistry(ConfigDataAdapter<?> dataAdapter) {
		for (ConfigDataAdapter<?> cfg : dataAdapters) {
			if (cfg.getDataFileName().equalsIgnoreCase(dataAdapter.getDataFileName())) {
				throw new IllegalArgumentException("Duplicate DataRegistry names! " + cfg.getDataFileName() + " ("
						+ cfg.getClass().getName() + ") - " + dataAdapter.getDataFileName() + " ("
						+ dataAdapter.getClass().getName() + ")");
			}
		}
		dataAdapters.add(dataAdapter);
	}

	public static void loadDataRegistries(File cfgDirectory) {
		File dirAS = new File(cfgDirectory + KinoMod.MODID, KinoMod.MODID);
		if (!dirAS.exists()) {
			dirAS.mkdirs();
		}
		dirConfigurationRegistries = dirAS;
	}

	public static void loadConfigRegistries(ConfigDataAdapter.LoadPhase phase) {
		for (ConfigDataAdapter<?> cfg : dataAdapters) {
			if (cfg.getLoadPhase() != phase) {
				continue;
			}
			attemptLoad(cfg, new File(dirConfigurationRegistries, cfg.getDataFileName() + ".cfg"));
		}
	}

	private static void attemptLoad(ConfigDataAdapter<?> cfg, File file) {
		cfg.resetRegistry();
		String[] out = cfg.serializeDataSet();

		Configuration config = new Configuration(file);
		config.load();
		config.addCustomCategoryComment("data", cfg.getDescription());
		out = config.getStringList("data", "data", out, "");
		for (String str : out) {
			if (cfg.appendDataSet(str) == null) {
				KinoMod.log.warn(
						"Skipped Entry '" + str + "' for registry " + cfg.getDataFileName() + "! Invalid format!");
			}
		}
		config.save();
		if (!cachedConfigs.containsKey(cfg.getDataFileName())) {
			cachedConfigs.put(cfg.getDataFileName(), config);
		}
	}

	public static Map<String, Configuration> getAvailableConfigurations() {
		return ImmutableMap.copyOf(cachedConfigs);
	}

	public static void loadData() {

		ENTITY_CENTAUR = config.getInt("config.category.id.centaur", "entity", 120, 120, 2147483000, "Change the ID of Centaur.");
		ENTITY_GHAST_BOSS = config.getInt("config.category.id.ghastbossd", "entity", 121, 120, 2147483000, "Change the ID of Dalium Ghast.");
		ENTITY_BISMUTH_BALL = config.getInt("config.category.id.bismuthball", "entity", 122, 120, 2147483000, "Change the ID of Bismuth Wand Projectile.");
		
		gabbroAmount = config.getInt("config.category.gen.overworld.gabbroAmount", "worldgen.overworld", 70, 0, 100, "Defines how many gabbro veins are generated per chunk. 0 = Disabled");
		gabbroVeinSize = config.getInt("config.category.gen.overworld.gabbroSize", "worldgen.overworld", 30, 1, 60, "Defines how big generated gabbro veins are.");
		
		kiniumAmountOverworld = config.getInt("config.category.gen.overworld.kiniumAmount", "worldgen.overworld", 3, 0, 2048, "Defines how many kinium ores it'll attempt to generate in per chunk. 0 = Disabled");
		seminiumAmountOverworld = config.getInt("config.category.gen.overworld.seminiumAmount", "worldgen.overworld", 3, 0, 2048, "Defines how many seminium ores it'll attempt to generate in per chunk. 0 = Disabled");
		daliumAmountOverworld = config.getInt("config.category.gen.overworld.daliumAmount", "worldgen.overworld", 3, 0, 2048, "Defines how many dalium ores it'll attempt to generate in per chunk. 0 = Disabled");
		baliumAmountOverworld = config.getInt("config.category.gen.overworld.baliumAmount", "worldgen.overworld", 3, 0, 2048, "Defines how many balium ores it'll attempt to generate in per chunk. 0 = Disabled");
		
		kiniumAmountNether = config.getInt("config.category.gen.nether.kiniumAmount", "worldgen.nether", 5, 0, 2048, "Defines how many kinium ores it'll attempt to generate in per chunk. 0 = Disabled");
		seminiumAmountNether = config.getInt("config.category.gen.nether.seminiumAmount", "worldgen.nether", 5, 0, 2048, "Defines how many seminium ores it'll attempt to generate in per chunk. 0 = Disabled");
		daliumAmountNether = config.getInt("config.category.gen.nether.daliumAmount", "worldgen.nether", 5, 0, 2048, "Defines how many dalium ores it'll attempt to generate in per chunk. 0 = Disabled");
		baliumAmountNether = config.getInt("config.category.gen.nether.baliumAmount", "worldgen.nether", 5, 0, 2048, "Defines how many balium ores it'll attempt to generate in per chunk. 0 = Disabled");
		
		kiniumAmountEnd = config.getInt("config.category.gen.end.kiniumAmount", "worldgen.end", 7, 0, 2048, "Defines how many kinium ores it'll attempt to generate in per chunk. 0 = Disabled");
		seminiumAmountEnd = config.getInt("config.category.gen.end.seminiumAmount", "worldgen.end", 7, 0, 2048, "Defines how many seminium ores it'll attempt to generate in per chunk. 0 = Disabled");
		daliumAmountEnd = config.getInt("config.category.gen.end.daliumAmount", "worldgen.end", 7, 0, 2048, "Defines how many dalium ores it'll attempt to generate in per chunk. 0 = Disabled");
		baliumAmountEnd = config.getInt("config.category.gen.end.baliumAmount", "worldgen.end", 7, 0, 2048, "Defines how many balium ores it'll attempt to generate in per chunk. 0 = Disabled");
		
		kiniumWandRange = config.getInt("config.category.item.equipment.wand.kiniumWandRange", "item.equipment", 10, 1, 30, "Set horizontal range for the Kinium Wand");
		kiniumWandVerticalRange = config.getInt("config.category.item.equipment.wand.kiniumWandVerticalRange", "item.equipment", 10, 1, 30, "Set vertical range for the Kinium Wand");
		seminiumWandRange = config.getInt("config.category.item.equipment.wand.seminiumWandRange", "item.equipment", 15, 1, 30, "Set horizontal range for the Seminium Wand");
		seminiumWandVerticalRange = config.getInt("config.category.item.equipment.wand.seminiumWandVerticalRange", "item.equipment", 15, 1, 30, "Set vertical range for the Seminium Wand");
		baliumWandRange = config.getInt("config.category.item.equipment.wand.baliumWandRange", "item.equipment", 15, 1, 30, "Set horizontal range for the Balium Wand");
		baliumWandVerticalRange = config.getInt("config.category.item.equipment.wand.baliumWandVerticalRange", "item.equipment", 15, 1, 30, "Set vertical range for the Balium Wand");
		daliumWandRange = config.getInt("config.category.item.equipment.wand.daliumWandRange", "item.equipment", 15, 1, 30, "Set horizontal range for the Dalium Wand");
		daliumWandVerticalRange = config.getInt("config.category.item.equipment.wand.daliumWandVerticalRange", "item.equipment", 15, 1, 30, "Set vertical range for the Dalium Wand");
		
		kiniumWandCooldown = config.getInt("config.category.item.equipment.wand.cooldown.kiniumWandCooldown", "item.equipment.cooldown", 30, 1, 3600, "Set cooldown for the Kinium Wand (in seconds)");
		seminiumWandCooldown = config.getInt("config.category.item.equipment.wand.cooldown.seminiumWandCooldown", "item.equipment.cooldown", 5, 1, 3600, "Set cooldown for the Seminium Wand (in seconds)");
		daliumWandCooldown = config.getInt("config.category.item.equipment.wand.cooldown.baliumWandCooldown", "item.equipment.cooldown", 5, 1, 3600, "Set cooldown for the Balium Wand (in seconds)");
		baliumWandCooldown = config.getInt("config.category.item.equipment.wand.cooldown.daliumWandCooldown", "item.equipment.cooldown", 5, 1, 3600, "Set cooldown for the Dalium Wand (in seconds)");

		if (config.hasChanged())
			config.save();
	}
}
