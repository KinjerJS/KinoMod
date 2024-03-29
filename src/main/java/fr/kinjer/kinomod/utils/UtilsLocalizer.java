package fr.kinjer.kinomod.utils;

import org.lwjgl.input.Keyboard;

import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;

public class UtilsLocalizer {
	
	public static String localize(String string){
		return new TextComponentTranslation(string).getFormattedText();
	}
	
	public static String shiftDetails() {
		return I18n.translateToLocal("info.holdShiftForDetails");
	}
	
	public static String numberLocalize(int level)
    {
        return I18n.translateToLocal("enchantment.level." + level);
    }
	
	public static String shiftForDetails() {

		return localize("info.holdShiftForDetails");
	}
	
	public static String getInfoText(String key) {

		return BRIGHT_GREEN + localize(key) + END;
	}
	
//	public static boolean displayShiftForDetail = true;
	
	public static final String BRIGHT_GREEN = (char) 167 + "a";
	public static final String END = (char) 167 + "r";

}
