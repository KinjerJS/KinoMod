package fr.kinjer.kinomod.init;

import java.util.ArrayList;
import java.util.List;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.items.*;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemTool;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber(value = Side.CLIENT, modid = KinoMod.MODID)
public class ItemsMod {

	public static final ArmorMaterial armor_bismuth = EnumHelper.addArmorMaterial("armor_bismuth",
			KinoMod.MODID + ":bismuth", -1, new int[]{5, 9, 10, 6}, 45, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 1.5F);

	public static List<ItemArmor> itemsarmor = new ArrayList<>();

	// Armor
	public static final ItemArmor helmet_item = new ItemArmorKino("helmet_item", armor_bismuth, 0,
			EntityEquipmentSlot.HEAD);
	public static final ItemArmor chestplate_item = new ItemArmorKino("chestplate_item", armor_bismuth, 0,
			EntityEquipmentSlot.CHEST);
	public static final ItemArmor leggings_item = new ItemArmorKino("leggings_item", armor_bismuth, 0,
			EntityEquipmentSlot.LEGS);
	public static final ItemArmor boots_item = new ItemArmorKino("boots_item", armor_bismuth, 0,
			EntityEquipmentSlot.FEET);

	public static final ToolMaterial bismuth_material = EnumHelper.addToolMaterial("bismuth_material", 3, -1, 3f, 1,
			30);

	public static List<ItemTool> itemtool = new ArrayList<>();

	// Tools
	public static final ItemSwordKino bismuth_sword = new ItemSwordKino(0, 0, 0, bismuth_material, null);

	public static List<Item> items = new ArrayList<>();

	// ore
	public static final Item kinium = new ItemKinium();
	public static final Item balium = new ItemBalium();
	public static final Item seminium = new ItemSeminium();
	public static final Item dalium = new ItemDalium();
	public static final Item bismuth = new ItemBismuth();

	// mystical
	public static final Item kinium_essence = new ItemKiniumEssence();
	public static final Item balium_essence = new ItemBaliumEssence();
	public static final Item seminium_essence = new ItemSeminiumEssence();
	public static final Item dalium_essence = new ItemDaliumEssence();
	public static final Item kinium_droplet = new ItemKiniumDroplet();
	public static final Item balium_droplet = new ItemBaliumDroplet();
	public static final Item seminium_droplet = new ItemSeminiumDroplet();
	public static final Item dalium_droplet = new ItemDaliumDroplet();

	// ring
	public static final Item kinium_ring = new ItemKinoRing("kinium_ring");
	public static final Item balium_ring = new ItemKinoRing("balium_ring");
	public static final Item seminium_ring = new ItemKinoRing("seminium_ring");
	public static final Item dalium_ring = new ItemKinoRing("dalium_ring");
	public static final Item bismuth_ring = new ItemKinoRing("bismuth_ring");

	// charm
	public static final Item charm_of_hunger = new ItemHungerCharm();
	public static final Item charm_of_hunger_advanced = new ItemHungerAdvancedCharm();
	public static final Item charm_of_fire = new ItemFireCharm();
	public static final Item charm_of_fire_advanced = new ItemFireAdvancedCharm();
	public static final Item charm_of_poison = new ItemPoisonCharm();
	public static final Item charm_of_poison_advanced = new ItemPoisonAdvancedCharm();

	public static final Item charm_of_drowning = new ItemDrowningCharm();
	public static final Item charm_of_drowning_advanced = new ItemDrowningAdvancedCharm();

	// loot
	public static final Item sword_part = new ItemSwordPart();
	public static final Item ghast_boss_tentacle_d = new ItemGhastBossDTentacle();
	public static final Item ghast_boss_tentacle_k = new ItemGhastBossKTentacle();
	public static final Item ghast_boss_tentacle_s = new ItemGhastBossSTentacle();
	public static final Item ghast_boss_tentacle_b = new ItemGhastBossBTentacle();
	public static final Item tentacle_soup = new ItemTentacleSoup();

	public static void setItemName(Item item, String name) {
		items.add(item.setRegistryName(KinoMod.MODID, name).setUnlocalizedName(KinoMod.MODID + "." + name));
	}
	public static void setItemToolName(ItemTool item, String name) {
		itemtool.add((ItemTool) item.setRegistryName(KinoMod.MODID, name).setUnlocalizedName(KinoMod.MODID + "." + name));
	}
	public static void setItemArmorName(ItemArmor item, String name) {
		itemsarmor.add((ItemArmor) item.setRegistryName(KinoMod.MODID, name).setUnlocalizedName(KinoMod.MODID + "." + name));
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void registerItemModels(ModelRegistryEvent event) {
		for (Item item : items) {
			registerModel(item, 0);
		}
		for (ItemArmor item : itemsarmor) {
			registerModel(item, 0);
		}
		for (ItemTool item : itemtool) {
			registerModel(item, 0);
		}
	}

	@SideOnly(Side.CLIENT)
	public static void registerModel(Item item, int metadata) {
		if (metadata < 0)
			metadata = 0;
		String resourceName = item.getUnlocalizedName().substring(5).replace('.', ':');
		if (metadata > 0)
			resourceName += "_m" + String.valueOf(metadata);

		ModelLoader.setCustomModelResourceLocation(item, metadata,
				new ModelResourceLocation(resourceName, "inventory"));
	}

	@SideOnly(Side.CLIENT)
	public static void registerModel(Item item) {
		registerModel(item, 0);
	}
}