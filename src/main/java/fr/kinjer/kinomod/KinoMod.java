package fr.kinjer.kinomod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.kinjer.kinomod.gen.GenWorld;
import fr.kinjer.kinomod.handler.HandlerRegistering;
import fr.kinjer.kinomod.handler.HandlerRenderGui;
import fr.kinjer.kinomod.init.InitItems;
import fr.kinjer.kinomod.init.InitRecipes;
//import fr.kinjer.kinomod.items.EntityBismuthBall;
import fr.kinjer.kinomod.client.ClientProxy;
import fr.kinjer.kinomod.common.CommonProxy;
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

@Mod(modid = KinoMod.MODID, name = KinoMod.NAME, version = KinoMod.VERSION, dependencies = KinoMod.DEPENDENCIES)
public class KinoMod {

	public static final String MODID = "kinomod";
	public static final String NAME = "KinoMod";
	public static final String VERSION = "1.0.0";
	public static final String DEPENDENCIES = "required-after:baubles";

	public static Logger log = LogManager.getLogger(NAME);

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

/*	public static DamageSource causeBismuthDamage(EntityBismuthBall entityBismuthBall, Object object) {

		return (new EntityDamageSource("Bismuth", entityBismuthBall)).setDamageBypassesArmor().setDamageIsAbsolute()
				.setMagicDamage();
	}
*/
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
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
		proxy.init();
		InitRecipes.init();
		HandlerRegistering.initRegistries();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		MinecraftForge.EVENT_BUS.register(new HandlerRenderGui());

	}
}
