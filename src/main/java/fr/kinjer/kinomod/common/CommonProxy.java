package fr.kinjer.kinomod.common;

import java.io.File;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.handler.HandlerGui;
import fr.kinjer.kinomod.handler.HandlerRegistering;
import fr.kinjer.kinomod.init.InitEvents;
<<<<<<< HEAD
import fr.kinjer.kinomod.tileentitys.TileExtractor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
=======
import fr.kinjer.kinomod.utils.UtilsDamageSource;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
>>>>>>> bde4654c4c81ec7a8b9a8079723242184cb21725
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.NetworkRegistry;

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
		TileEntity.register("modid:tile_extractor", TileExtractor.class);
	}
<<<<<<< HEAD
	
	public void init() {
		NetworkRegistry.INSTANCE.registerGuiHandler(KinoMod.instance, new HandlerGui());
		
=======

	public void init() {
	}

	public void render() {
	}

	public void registerConfigDataRegistries() {
>>>>>>> bde4654c4c81ec7a8b9a8079723242184cb21725
	}
}
