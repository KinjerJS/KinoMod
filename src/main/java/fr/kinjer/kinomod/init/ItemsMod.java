package fr.kinjer.kinomod.init;

import java.util.ArrayList;
import java.util.List;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.items.ItemArmorKino;
import fr.kinjer.kinomod.items.ItemBalium;
import fr.kinjer.kinomod.items.ItemBaliumDroplet;
import fr.kinjer.kinomod.items.ItemBaliumEssence;
import fr.kinjer.kinomod.items.ItemBismuth;
import fr.kinjer.kinomod.items.ItemDalium;
import fr.kinjer.kinomod.items.ItemDaliumDroplet;
import fr.kinjer.kinomod.items.ItemDaliumEssence;
import fr.kinjer.kinomod.items.ItemDrowningAdvancedCharm;
import fr.kinjer.kinomod.items.ItemDrowningCharm;
import fr.kinjer.kinomod.items.ItemFireAdvancedCharm;
import fr.kinjer.kinomod.items.ItemFireCharm;
import fr.kinjer.kinomod.items.ItemHungerAdvancedCharm;
import fr.kinjer.kinomod.items.ItemHungerCharm;
import fr.kinjer.kinomod.items.ItemKinium;
import fr.kinjer.kinomod.items.ItemKiniumDroplet;
import fr.kinjer.kinomod.items.ItemKiniumEssence;
import fr.kinjer.kinomod.items.ItemKinoRing;
import fr.kinjer.kinomod.items.ItemPoisonAdvancedCharm;
import fr.kinjer.kinomod.items.ItemPoisonCharm;
import fr.kinjer.kinomod.items.ItemSeminium;
import fr.kinjer.kinomod.items.ItemSeminiumDroplet;
import fr.kinjer.kinomod.items.ItemSeminiumEssence;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber(value = Side.CLIENT, modid = KinoMod.MODID)
public class ItemsMod {
	
	public static final ArmorMaterial armor_bismuth = EnumHelper.addArmorMaterial("armor_bismuth", KinoMod.MODID + ":bismuth", 54694, new int[] {300, 400, 500, 300}, 100, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 20.0f);

	public static List<ItemArmor> itemsarmor = new ArrayList<>();
	
	public static final ItemArmor helmet_item = new ItemArmorKino("helmet_item", armor_bismuth, 0, EntityEquipmentSlot.HEAD);
	public static final ItemArmor chestplate_item = new ItemArmorKino("chestplate_item", armor_bismuth, 0, EntityEquipmentSlot.CHEST);
	public static final ItemArmor leggings_item = new ItemArmorKino("leggings_item", armor_bismuth, 0, EntityEquipmentSlot.LEGS);
	public static final ItemArmor boots_item = new ItemArmorKino("boots_item", armor_bismuth, 0, EntityEquipmentSlot.FEET);
	
	public static List<Item> items = new ArrayList<>();
	
	//ore
	public static final Item kinium = new ItemKinium();
	public static final Item balium = new ItemBalium();
	public static final Item seminium = new ItemSeminium();
	public static final Item dalium = new ItemDalium();
	public static final Item bismuth = new ItemBismuth();
	
	//mystical
	public static final Item kinium_essence = new ItemKiniumEssence();
	public static final Item balium_essence = new ItemBaliumEssence();
	public static final Item seminium_essence = new ItemSeminiumEssence();
	public static final Item dalium_essence = new ItemDaliumEssence();
	public static final Item kinium_droplet = new ItemKiniumDroplet();
	public static final Item balium_droplet = new ItemBaliumDroplet();
	public static final Item seminium_droplet = new ItemSeminiumDroplet();
	public static final Item dalium_droplet = new ItemDaliumDroplet();
	
	//ring
	public static final Item kinium_ring = new ItemKinoRing("kinium_ring");
	public static final Item balium_ring = new ItemKinoRing("balium_ring");
	public static final Item seminium_ring = new ItemKinoRing("seminium_ring");
	public static final Item dalium_ring = new ItemKinoRing("dalium_ring");
	public static final Item bismuth_ring = new ItemKinoRing("bismuth_ring");
	
	//charm
	public static final Item charm_of_hunger = new ItemHungerCharm();
	public static final Item charm_of_hunger_advanced = new ItemHungerAdvancedCharm();
	public static final Item charm_of_fire = new ItemFireCharm();
	public static final Item charm_of_fire_advanced = new ItemFireAdvancedCharm();
	public static final Item charm_of_poison = new ItemPoisonCharm();
	public static final Item charm_of_poison_advanced = new ItemPoisonAdvancedCharm();
	public static final Item charm_of_drowning = new ItemDrowningCharm();
	public static final Item charm_of_drowning_advanced = new ItemDrowningAdvancedCharm();
	
	public static void setItemName(Item item, String name) {
		items.add(item.setRegistryName(KinoMod.MODID, name).setUnlocalizedName(KinoMod.MODID + "." + name));
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void registerItemModels(ModelRegistryEvent event) {
		for(Item item : items) {
			registerModel(item, 0);
		}
		for(ItemArmor item : itemsarmor) {
				registerModel(item, 0);
		}
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