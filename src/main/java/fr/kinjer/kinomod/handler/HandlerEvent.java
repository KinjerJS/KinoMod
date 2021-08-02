package fr.kinjer.kinomod.handler;

import fr.kinjer.kinomod.client.ClientProxy;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class HandlerEvent {

	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public static void onEvent(KeyInputEvent event) {
		
		System.out.println("Key Input Event");

		KeyBinding[] keyBindings = ClientProxy.keyBindings;

		if (keyBindings[0].isPressed()) {
			System.out.println("Key binding =" + keyBindings[0].getKeyDescription());

			// do stuff for this key binding here
			// remember you may need to send packet to server
		}
		if (keyBindings[1].isPressed()) {
			System.out.println("Key binding =" + keyBindings[1].getKeyDescription());

			// do stuff for this key binding here
			// remember you may need to send packet to server
		}
	}
}
