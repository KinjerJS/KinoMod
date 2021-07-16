package fr.kinjer.kinomod.config;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;
import java.util.Set;

import fr.kinjer.kinomod.KinoMod;

public class LogHelperBC {
	
	 private static Logger logger = LogManager.getLogger(KinoMod.MODID);
	
	 public static void log(Level logLevel, Object object) {
	        logger.log(logLevel, String.valueOf(object));
	    }

	    public static void log(Level logLevel, Object object, Throwable throwable) {
	        logger.log(logLevel, String.valueOf(object), throwable);
	    }
	
	 public static void error(String object, Object... format) {
	        log(Level.ERROR, String.format(object, format));
	    }
	 
	 public static void bigError(String format, Object... data) {
	        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
	        error("****************************************");
	        error("* " + format, data);
	        for (int i = 2; i < 8 && i < trace.length; i++) {
	            error("*  at %s%s", trace[i].toString(), i == 7 ? "..." : "");
	        }
	        error("****************************************");
	    }
}
