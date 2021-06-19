package fr.kinjer.kinomod.init;

import java.util.ArrayList;
import java.util.List;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.itemblocks.ItemBlockBalium;
import fr.kinjer.kinomod.itemblocks.ItemBlockBaliumOre;
import fr.kinjer.kinomod.itemblocks.ItemBlockBismuth;
import fr.kinjer.kinomod.itemblocks.ItemBlockDalium;
import fr.kinjer.kinomod.itemblocks.ItemBlockDaliumOre;
import fr.kinjer.kinomod.itemblocks.ItemBlockKinium;
import fr.kinjer.kinomod.itemblocks.ItemBlockKiniumOre;
import fr.kinjer.kinomod.itemblocks.ItemBlockSeminium;
import fr.kinjer.kinomod.itemblocks.ItemBlockSeminiumOre;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber(value = Side.CLIENT, modid = KinoMod.MODID)
public class ItemBlocksMod {
	
	public static List<ItemBlock> items = new ArrayList<>();
	
	public static final ItemBlock kinium_block = new ItemBlockKinium();
	public static final ItemBlock kinium_ore = new ItemBlockKiniumOre();
	
	public static final ItemBlock balium_block = new ItemBlockBalium();
	public static final ItemBlock balium_ore = new ItemBlockBaliumOre();
	
	public static final ItemBlock seminium_ore = new ItemBlockSeminiumOre();
	public static final ItemBlock seminium_block = new ItemBlockSeminium();
	
	public static final ItemBlock dalium_ore = new ItemBlockDaliumOre();
	public static final ItemBlock dalium_block = new ItemBlockDalium();
	
	public static final ItemBlock bismuth_block = new ItemBlockBismuth();
	
//	public static void setItemName(Item item, String name) {
//		item.setRegistryName(KinoMod.MODID, name).setUnlocalizedName(KinoMod.MODID + "." + name);
//	}


	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void registerItemModels(ModelRegistryEvent event) {
		registerModel(kinium_ore);
		registerModel(balium_ore);
		registerModel(seminium_ore);
		registerModel(dalium_ore);
		registerModel(kinium_block);
		registerModel(balium_block);
		registerModel(seminium_block);
		registerModel(dalium_block);
		registerModel(bismuth_block);
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
	    String resourceName = item.getUnlocalizedName().substring(5).replace('.', ':');
	    ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(resourceName, "inventory"));
	}
}