package fr.kinjer.kinomod.common;

import java.io.File;

import fr.kinjer.kinomod.handler.HandlerRegistering;
import fr.kinjer.kinomod.init.InitEvents;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;

public class CommonProxy {

	public static final String PACKAGE = "fr.kinjer.kinomod.common.CommonProxy";

	public void setupConfiguration() {
	}

	public EntityPlayer getPlayer() {
		return null;
	}

	public void registerEventHandlers() {
	}

	public void preInit(File file) {
		MinecraftForge.EVENT_BUS.register(new HandlerRegistering());
		MinecraftForge.EVENT_BUS.register(new InitEvents());
	}

	public void init() {
	}

	public void render() {
	}

	public void registerConfigDataRegistries() {
	}
}
