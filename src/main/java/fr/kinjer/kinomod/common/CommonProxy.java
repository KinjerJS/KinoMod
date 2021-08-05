package fr.kinjer.kinomod.common;

import java.io.File;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.handler.HandlerGui;
import fr.kinjer.kinomod.handler.HandlerRegistering;
import fr.kinjer.kinomod.init.InitEvents;
import fr.kinjer.kinomod.tileentitys.TileExtractor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy {
	
	public static final String PACKAGE = "fr.kinjer.kinomod.common.CommonProxy";
	
	public EntityPlayer getPlayer() {
		return null;
	}
	
	public void registerEventHandlers() {
	}
	
	public void preInit(File file) {
		MinecraftForge.EVENT_BUS.register(new HandlerRegistering());
		MinecraftForge.EVENT_BUS.register(new InitEvents());
		TileEntity.register("modid:tile_extractor", TileExtractor.class);
	}
	
	public void init() {
		NetworkRegistry.INSTANCE.registerGuiHandler(KinoMod.instance, new HandlerGui());
		
	}
}
