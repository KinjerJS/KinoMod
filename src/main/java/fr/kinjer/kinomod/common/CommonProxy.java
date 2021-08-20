package fr.kinjer.kinomod.common;

import java.io.File;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.handler.HandlerGui;
import fr.kinjer.kinomod.handler.HandlerRegistering;
import fr.kinjer.kinomod.init.InitEvents;
import fr.kinjer.kinomod.tileentities.TileExtractor;
import fr.kinjer.kinomod.utils.UtilsDamageSource;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {
	
	public static DamageSource bismuthDamage   = UtilsDamageSource.newType("kinomod.bismuth").setDamageBypassesArmor().setDamageIsAbsolute().setMagicDamage();
	public static DamageSource bismuthProjectileDamage   = UtilsDamageSource.newType("kinomod.bismuth.projectile").setDamageBypassesArmor().setDamageIsAbsolute().setMagicDamage().setProjectile();

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
		TileExtractor.initialize();
	}
	
	public void init() {
		NetworkRegistry.INSTANCE.registerGuiHandler(KinoMod.instance, new HandlerGui());
	}
	public void render() {
	}

	public void registerConfigDataRegistries() {
	}
}
