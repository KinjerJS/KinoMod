package fr.kinjer.kinomod.proxy;

import java.io.File;

import fr.kinjer.kinomod.handler.RegisteringHandler;
import fr.kinjer.kinomod.init.EventsMod;
import net.minecraft.init.SoundEvents;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;

public class CommonProxy {
	public static final String PACKAGE = "fr.kinjer.kinomod.proxy.CommonProxy";

	
	public void preInit(File file) {
		MinecraftForge.EVENT_BUS.register(new RegisteringHandler());
		MinecraftForge.EVENT_BUS.register(new EventsMod());
		
	}
	
	public void init() {
		
	}
}
