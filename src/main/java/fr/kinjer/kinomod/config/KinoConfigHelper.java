package fr.kinjer.kinomod.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public interface KinoConfigHelper {

    Configuration createConfiguration(FMLPreInitializationEvent event);

    default String getCategoryComment(String category) {
        return "";
    }

    default void onConfigChanged(String propertyName, String propertyCategory) {}

    default void onConfigLoaded(){}
}