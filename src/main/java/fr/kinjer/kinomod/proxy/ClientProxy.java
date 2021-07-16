package fr.kinjer.kinomod.proxy;

import java.io.File;

import org.lwjgl.input.Keyboard;

import fr.kinjer.kinomod.handler.RenderHandler;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy{
	
	public static final String PACKAGE = "fr.kinjer.kinomod.proxy.ClientProxy";

	@Override
	public void preInit(File file) {
		super.preInit(file);
		MinecraftForge.EVENT_BUS.register(new RenderHandler());
	}
	
	@Override
	public void init() {
		super.init();
		
	}
	
	public static KeyBinding[] keyBindings; {
		
		keyBindings = new KeyBinding[2]; 
		  
		keyBindings[0] = new KeyBinding("key.changemod", Keyboard.KEY_F4, "key.kinomod.category");
		keyBindings[1] = new KeyBinding("key.misc", Keyboard.KEY_F8, "key.kinomod.category");
		  
		for (int i = 0; i < keyBindings.length; ++i) 
		{
		    ClientRegistry.registerKeyBinding(keyBindings[i]);
		}
	}
}
