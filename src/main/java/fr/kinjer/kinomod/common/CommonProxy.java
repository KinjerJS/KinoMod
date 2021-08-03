package fr.kinjer.kinomod.common;

import java.io.File;

import fr.kinjer.kinomod.handler.HandlerRegistering;
import fr.kinjer.kinomod.init.InitEvents;
import fr.kinjer.kinomod.utils.UtilsDamageSource;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;

public class CommonProxy {
	
	public static DamageSource bismuthDamage   = UtilsDamageSource.newType("kinomod.bismuth").setDamageBypassesArmor().setDamageIsAbsolute().setMagicDamage();
	public static DamageSource bismuthProjectileDamage   = UtilsDamageSource.newType("kinomod.bismuth").setDamageBypassesArmor().setDamageIsAbsolute().setMagicDamage().setProjectile();

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
