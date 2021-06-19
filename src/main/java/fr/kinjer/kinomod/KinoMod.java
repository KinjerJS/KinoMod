package fr.kinjer.kinomod;

import org.apache.logging.log4j.Logger;

import fr.kinjer.kinomod.fluids.KiniumFluid;
import fr.kinjer.kinomod.gen.WorldGen;
import fr.kinjer.kinomod.handler.RegisteringHandler;
import fr.kinjer.kinomod.init.FluidsMod;
import fr.kinjer.kinomod.init.ItemsMod;
import fr.kinjer.kinomod.init.RecipesMod;
import fr.kinjer.kinomod.proxy.ClientProxy;
import fr.kinjer.kinomod.proxy.CommonProxy;
import fr.kinjer.kinomod.utils.MaterialsMod;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
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

@Mod(modid = KinoMod.MODID, name = KinoMod.NAME, version = KinoMod.VERSION)
public class KinoMod {
	
	public static final String MODID = "kinomod";
	public static final String NAME = "KinoMod";
	public static final String VERSION = "1.0.0";
	
	@Instance(MODID)
	public static KinoMod instance;
	
	@SidedProxy(serverSide = CommonProxy.PACKAGE, clientSide = ClientProxy.PACKAGE)
	public static CommonProxy proxy;
	
	public static Logger logger;
	
	WorldGen worldgeneration = new WorldGen(); 
	
	public KinoMod() {
		MinecraftForge.EVENT_BUS.register(new RegisteringHandler());
		FluidRegistry.enableUniversalBucket();
	}
	
	public static final CreativeTabs tabKino = new CreativeTabs("tabKino")
    {
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem()
        {
            return new ItemStack(ItemsMod.kinium);
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
		RecipesMod.init();
	}
	
	@EventHandler
	public void postInig(FMLPostInitializationEvent e) {
		
	}

}
