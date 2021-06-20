package fr.kinjer.kinomod.utils;

import net.minecraft.util.text.translation.I18n;

public class Localizer {
	
	public static String localize(String key) {

		return I18n.translateToLocal(key);
	}
	
	public static String shiftDetails() {
		return I18n.translateToLocal("kinomod.shiftDetails");
	}

}
