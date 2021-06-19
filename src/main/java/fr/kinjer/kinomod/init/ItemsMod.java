package fr.kinjer.kinomod.init;

import java.util.ArrayList;
import java.util.List;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.items.ItemBalium;
import fr.kinjer.kinomod.items.ItemBismuth;
import fr.kinjer.kinomod.items.ItemDalium;
import fr.kinjer.kinomod.items.ItemHungerAdvancedCharm;
import fr.kinjer.kinomod.items.ItemHungerCharm;
import fr.kinjer.kinomod.items.ItemKinium;
import fr.kinjer.kinomod.items.ItemSeminium;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber(value = Side.CLIENT, modid = KinoMod.MODID)
public class ItemsMod {
	
	public static List<Item> items = new ArrayList<>();
	
	public static final Item kinium = new ItemKinium();
	public static final Item balium = new ItemBalium();
	public static final Item seminium = new ItemSeminium();
	public static final Item dalium = new ItemDalium();
	public static final Item bismuth = new ItemBismuth();
	
	public static final Item charm_of_hunger = new ItemHungerCharm();
	public static final Item charm_of_hunger_advanced = new ItemHungerAdvancedCharm();
	
	public static void setItemName(Item item, String name) {
		items.add(item.setRegistryName(KinoMod.MODID, name).setUnlocalizedName(KinoMod.MODID + "." + name));
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void registerItemModels(ModelRegistryEvent event) {
		registerModel(kinium);
		registerModel(balium);
		registerModel(seminium);
		registerModel(dalium);
		registerModel(bismuth);
		registerModel(charm_of_hunger);
		registerModel(charm_of_hunger_advanced);
	}

	@SideOnly(Side.CLIENT)
	public static void registerModel(Item item, int metadata) {
		if (metadata < 0) metadata = 0;
	    String resourceName = item.getUnlocalizedName().substring(5).replace('.', ':');
	    if (metadata > 0) resourceName += "_m" + String.valueOf(metadata);
	 
	    ModelLoader.setCustomModelResourceLocation(item, metadata, new ModelResourceLocation(resourceName, "inventory"));
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerModel(Item item) {
		registerModel(item, 0);
	}
}