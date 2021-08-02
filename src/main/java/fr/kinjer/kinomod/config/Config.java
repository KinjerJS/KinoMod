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
		File dirAS = new File(cfgDirectory, KinoMod.MODID);
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

		ENTITY_CENTAUR = config.getInt("config.category.id.centaur", "general", 120, 120,
				2147483000, "config.category.id.centaur.comment");
		ENTITY_GHAST_BOSS = config.getInt("config.category.id.ghastbossd", "general", 121, 120,
				2147483000, "config.category.id.ghastbossd.comment");
		ENTITY_BISMUTH_BALL = config.getInt("config.category.id.bismuthball", "general", 122, 120,
				2147483000, "config.category.id.bismuthball.comment");

		if (config.hasChanged())
			config.save();
	}
}
