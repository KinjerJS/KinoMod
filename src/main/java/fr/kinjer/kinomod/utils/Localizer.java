package fr.kinjer.kinomod.utils;

import org.lwjgl.input.Keyboard;

import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;

public class Localizer {
	
	public static String localize(String key) {

		return I18n.translateToLocal(key);
	}
	
	public static String shiftDetails() {
		return I18n.translateToLocal("kinomod.shiftDetails");
	}
	
	public static String numberLocalize(int level)
    {
        return I18n.translateToLocal("enchantment.level." + level);
    }
	
	public static String getKeyName(int key) {

        if (key < 0) {
            return I18n.translateToLocalFormatted("key.mouseButton", key + 101);
        } else if (key > Keyboard.KEYBOARD_SIZE) {
            return Keyboard.getKeyName(0);
        }
        return Keyboard.getKeyName(key);
    }

}
