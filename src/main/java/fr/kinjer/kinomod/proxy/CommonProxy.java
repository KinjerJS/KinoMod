package fr.kinjer.kinomod.proxy;

import java.io.File;

import fr.kinjer.kinomod.handler.ModSounds;
import fr.kinjer.kinomod.handler.RegisteringHandler;
import fr.kinjer.kinomod.init.InitEvents;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;

public class CommonProxy {
	
	public static final String PACKAGE = "fr.kinjer.kinomod.proxy.CommonProxy";
	
	public EntityPlayer getPlayer() {
		return null;
	}
	
	public void registerEventHandlers() {
	}
	
	public void preInit(File file) {
		MinecraftForge.EVENT_BUS.register(new RegisteringHandler());
		MinecraftForge.EVENT_BUS.register(new InitEvents());
	}
	
	public void init() {}
}
