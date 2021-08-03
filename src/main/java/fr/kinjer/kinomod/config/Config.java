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
	public static int seminiumAmountOverworld = 3;
	public static int daliumAmountOverworld = 3;
	public static int baliumAmountOverworld = 3;
	
	public static int kiniumAmountNether = 6;
	public static int seminiumAmountNether = 6;
	public static int daliumAmountNether = 6;
	public static int baliumAmountNether = 6;
	
	public static int kiniumAmountEnd = 8;
	public static int seminiumAmountEnd = 8;
	public static int daliumAmountEnd = 8;
	public static int baliumAmountEnd = 8;
	

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

		ENTITY_CENTAUR = config.getInt("config.category.id.centaur", "entity", 120, 120, 2147483000, "config.category.id.centaur.comment");
		ENTITY_GHAST_BOSS = config.getInt("config.category.id.ghastbossd", "entity", 121, 120, 2147483000, "config.category.id.ghastbossd.comment");
		ENTITY_BISMUTH_BALL = config.getInt("config.category.id.bismuthball", "entity", 122, 120, 2147483000, "config.category.id.bismuthball.comment");
		
		gabbroAmount = config.getInt("config.category.gen.overworld.gabbroAmount", "worldgen.overworld", 70, 0, 100, "config.category.gen.overworld.gabbroAmount.comment");
		gabbroVeinSize = config.getInt("config.category.gen.overworld.gabbroSize", "worldgen.overworld", 30, 1, 60, "config.category.gen.overworld.gabbroSize.comment");
		
		kiniumAmountOverworld = config.getInt("config.category.gen.overworld.kiniumAmount", "worldgen.overworld", 3, 0, 2048, "config.category.gen.overworld.kiniumAmount.comment");
		seminiumAmountOverworld = config.getInt("config.category.gen.overworld.seminiumAmount", "worldgen.overworld", 3, 0, 2048, "config.category.gen.overworld.seminiumAmount.comment");
		daliumAmountOverworld = config.getInt("config.category.gen.overworld.daliumAmount", "worldgen.overworld", 3, 0, 2048, "config.category.gen.overworld.daliumAmount.comment");
		baliumAmountOverworld = config.getInt("config.category.gen.overworld.baliumAmount", "worldgen.overworld", 3, 0, 2048, "config.category.gen.overworld.baliumAmount.comment");
		
		kiniumAmountNether = config.getInt("config.category.gen.nether.kiniumAmount", "worldgen.nether", 6, 0, 2048, "config.category.gen.nether.kiniumAmount.comment");
		seminiumAmountNether = config.getInt("config.category.gen.nether.seminiumAmount", "worldgen.nether", 6, 0, 2048, "config.category.gen.nether.seminiumAmount.comment");
		daliumAmountNether = config.getInt("config.category.gen.nether.daliumAmount", "worldgen.nether", 6, 0, 2048, "config.category.gen.nether.daliumAmount.comment");
		baliumAmountNether = config.getInt("config.category.gen.nether.baliumAmount", "worldgen.nether", 6, 0, 2048, "config.category.gen.nether.baliumAmount.comment");
		
		kiniumAmountEnd = config.getInt("config.category.gen.end.kiniumAmount", "worldgen.end", 8, 0, 2048, "config.category.gen.end.kiniumAmount.comment");
		seminiumAmountEnd = config.getInt("config.category.gen.end.seminiumAmount", "worldgen.end", 8, 0, 2048, "config.category.gen.end.seminiumAmount.comment");
		daliumAmountEnd = config.getInt("config.category.gen.end.daliumAmount", "worldgen.end", 8, 0, 2048, "config.category.gen.end.daliumAmount.comment");
		baliumAmountEnd = config.getInt("config.category.gen.end.baliumAmount", "worldgen.end", 8, 0, 2048, "config.category.gen.end.baliumAmount.comment");

		if (config.hasChanged())
			config.save();
	}
}
