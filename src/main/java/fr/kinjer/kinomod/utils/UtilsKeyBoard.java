package fr.kinjer.kinomod.utils;

import org.lwjgl.input.Keyboard;

import ca.weblite.objc.Proxy;
import fr.kinjer.kinomod.proxy.ProxyClient;
import net.minecraft.client.settings.KeyBinding;

public class UtilsKeyBoard {
	
	public static boolean isShiftKeyDown() {

		return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
	}
	
	public String getUUID() {

		return "kinomod.multimode";
	}

	public static boolean isKeyBoundDown() {

		return Keyboard.isKeyDown(Keyboard.KEY_F4);
	}
}
