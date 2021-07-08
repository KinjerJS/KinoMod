package fr.kinjer.kinomod.proxy;

import java.io.File;

import fr.kinjer.kinomod.handler.RenderHandler;
import net.minecraftforge.common.MinecraftForge;

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
	
}
