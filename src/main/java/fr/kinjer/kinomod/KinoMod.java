package fr.kinjer.kinomod;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.kinjer.kinomod.gen.GenWorld;
import fr.kinjer.kinomod.handler.*;
import fr.kinjer.kinomod.init.*;
import fr.kinjer.kinomod.client.ClientProxy;
import fr.kinjer.kinomod.common.CommonProxy;
import fr.kinjer.kinomod.config.*;
import fr.kinjer.kinomod.entity.EntityCentaur;
import fr.kinjer.kinomod.entity.projectile.EntityBismuthBall;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = KinoMod.MODID, name = KinoMod.NAME, version = KinoMod.VERSION, dependencies = KinoMod.DEPENDENCIES, guiFactory = "fr.kinjer.kinomod.config.ingame.ConfigGuiFactory")
public class KinoMod {

	public static final String MODID = "kinomod";
	public static final String NAME = "KinoMod";
	public static final String VERSION = "1.0.0";
	public static final String DEPENDENCIES = "required-after:baubles";

	public static Logger log = LogManager.getLogger(NAME);
	public static File config;

	@Instance(KinoMod.MODID)
	public static KinoMod instance;

	@SidedProxy(serverSide = CommonProxy.PACKAGE, clientSide = ClientProxy.PACKAGE)
	public static CommonProxy proxy;

	public static Logger logger;

	GenWorld worldgeneration = new GenWorld();
	{
		FluidRegistry.enableUniversalBucket();
	}

	public static DamageSource Bismuth = new DamageSource("Bismuth").setDamageBypassesArmor().setDamageIsAbsolute()
			.setMagicDamage();

	public static DamageSource DamageSourceBismuth(EntityLivingBase user) {

		return (new EntityDamageSource("Bismuth", user)).setDamageBypassesArmor().setDamageIsAbsolute()
				.setMagicDamage();
	}

	public static DamageSource causeBismuthDamageBall(EntityBismuthBall entityBismuthBall) {

		return (new EntityDamageSource("Bismuth", entityBismuthBall)).setDamageBypassesArmor().setDamageIsAbsolute()
				.setMagicDamage();
	}

	public static DamageSource causeBismuthDamageCentaur(EntityCentaur entityCentaur) {

		return (new EntityDamageSource("Bismuth", entityCentaur)).setDamageBypassesArmor().setDamageIsAbsolute()
				.setMagicDamage();
	}

	public static class DamageSourceBismuth extends EntityDamageSource {
		public DamageSourceBismuth(Entity entity) {
			super("Bismuth", entity);
			DamageSourceBismuth.isBypassArmor();
		}

		public static boolean isBypassArmor() {
			return true;
		}
	}

	public static final CreativeTabs tabKino = new CreativeTabs("tabKino") {
		@SideOnly(Side.CLIENT)
		public ItemStack getTabIconItem() {
			return new ItemStack(InitItems.kinium);
		}
	};

	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		proxy.preInit(e.getSuggestedConfigurationFile());
		logger = e.getModLog();
		GameRegistry.registerWorldGenerator(worldgeneration, 0);

		proxy.setupConfiguration();

		Config.loadAndSetup(e.getSuggestedConfigurationFile());

		proxy.registerConfigDataRegistries();
		Config.loadDataRegistries(e.getModConfigurationDirectory());
		Config.loadConfigRegistries(ConfigDataAdapter.LoadPhase.PRE_INIT);
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
		proxy.init();
		Config.loadConfigRegistries(ConfigDataAdapter.LoadPhase.INIT);
		InitRecipes.init();
		HandlerRegistering.initRegistries();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		MinecraftForge.EVENT_BUS.register(new HandlerRenderGui());
		Config.loadConfigRegistries(ConfigDataAdapter.LoadPhase.POST_INIT);

	}
}
