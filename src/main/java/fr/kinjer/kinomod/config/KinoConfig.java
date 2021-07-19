package fr.kinjer.kinomod.config;

import fr.kinjer.kinomod.KinoMod;
import com.google.common.collect.Sets;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@ModConfigContainer(modid = KinoMod.MODID)
public class KinoConfig implements KinoConfigHelper {
	
	public static Map<String, String> comments = new HashMap<String, String>();

    @Override
    public Configuration createConfiguration(FMLPreInitializationEvent event) {
        return new Configuration();
    }
    
	@ModConfigProperty(category = "Tweaks", name = "centaurHealth", comment = "Allows you to tweak the Centaur health (will only affect new Centaur).")
    public static int centaurHealth = 4000;

}
