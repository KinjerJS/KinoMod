package fr.kinjer.kinomod.utils;

import org.lwjgl.input.Keyboard;

public class KeyBoard {
	
	public static boolean isShiftKeyDown() {

		return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
	}

}
