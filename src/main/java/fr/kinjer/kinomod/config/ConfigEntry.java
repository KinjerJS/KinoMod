package fr.kinjer.kinomod.config;

import net.minecraftforge.common.config.Configuration;

public abstract class ConfigEntry {

    private final Section section;
    private final String key;

    protected ConfigEntry(Section section, String key) {
        this.section = section;
        this.key = key;
    }

    public String getConfigurationSection() {
        return section.name().toLowerCase() + "." + key;
    }

    public String getKey() {
        return key;
    }

    public abstract void loadFromConfig(Configuration cfg);

    public static enum Section {

        GENERAL
    }

}