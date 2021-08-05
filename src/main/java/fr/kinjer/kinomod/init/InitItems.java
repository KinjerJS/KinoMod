package fr.kinjer.kinomod.init;

import java.util.ArrayList;
import java.util.List;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.items.*;
import fr.kinjer.kinomod.items.base.*;
import fr.kinjer.kinomod.items.equipment.*;
import fr.kinjer.kinomod.items.equipment.armor.*;
import fr.kinjer.kinomod.items.equipment.tool.*;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemTool;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber(value = Side.CLIENT, modid = KinoMod.MODID)
public class InitItems {

	public static final ArmorMaterial armor_bismuth = EnumHelper.addArmorMaterial("armor_bismuth",
			KinoMod.MODID + ":bismuth", -1, new int[] { 5, 9, 10, 6 }, 45, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 1.5F);

	public static List<ItemArmor> itemsarmor = new ArrayList<>();

	// Armor
	public static final ItemArmor helmet_item = new ArmorBismuth("helmet_item", armor_bismuth, 0,
			EntityEquipmentSlot.HEAD);
	public static final ItemArmor chestplate_item = new ArmorBismuth("chestplate_item", armor_bismuth, 0,
			EntityEquipmentSlot.CHEST);
	public static final ItemArmor leggings_item = new ArmorBismuth("leggings_item", armor_bismuth, 0,
			EntityEquipmentSlot.LEGS);
	public static final ItemArmor boots_item = new ArmorBismuth("boots_item", armor_bismuth, 0,
			EntityEquipmentSlot.FEET);

	public static final ToolMaterial bismuth_material = EnumHelper.addToolMaterial("bismuth_material", 3, -1, 3.0F, 1.0F,
			30);

	public static List<ItemTool> itemtool = new ArrayList<>();

	// Tools
	public static final ToolSwordKino bismuth_sword = new ToolSwordKino(0, 0, 0, bismuth_material, null);

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
	public static final Item kinium_ring = new BaseKinoBaubleRing("kinium_ring");
	public static final Item balium_ring = new BaseKinoBaubleRing("balium_ring");
	public static final Item seminium_ring = new BaseKinoBaubleRing("seminium_ring");
	public static final Item dalium_ring = new BaseKinoBaubleRing("dalium_ring");
	public static final Item bismuth_ring = new ItemBismuthRing();

	// charm
	public static final Item dalium_charm = new ItemDaliumCharm();
	public static final Item dalium_charm_advanced = new ItemDaliumAdvancedCharm();
	public static final Item kinium_charm = new ItemKiniumCharm();
	public static final Item kinium_charm_advanced = new ItemKiniumAdvancedCharm();
	public static final Item seminium_charm = new ItemSeminiumCharm();
	public static final Item seminium_charm_advanced = new ItemSeminiumAdvancedCharm();
	public static final Item balium_charm = new ItemBaliumCharm();
	public static final Item balium_charm_advanced = new ItemBaliumAdvancedCharm();
	public static final Item bismuth_charm = new ItemBismuthCharm();
	
	// belt
	public static final Item dalium_belt = new ItemDaliumBelt();
	public static final Item dalium_belt_advanced = new ItemDaliumAdvancedBelt();
	public static final Item kinium_belt = new ItemKiniumBelt();
	public static final Item kinium_belt_advanced = new ItemKiniumAdvancedBelt();
	public static final Item seminium_belt = new ItemSeminiumBelt();
	public static final Item seminium_belt_advanced = new ItemSeminiumAdvancedBelt();
	public static final Item balium_belt = new ItemBaliumBelt();
	public static final Item balium_belt_advanced = new ItemBaliumAdvancedBelt();
	public static final Item bismuth_belt = new ItemBismuthBelt();
	
	//wand
	public static final Item dalium_wand = new ItemDaliumWand();
	public static final Item kinium_wand = new ItemKiniumWand();
	public static final Item seminium_wand = new ItemSeminiumWand();
	public static final Item balium_wand = new ItemBaliumWand();
	public static final Item bismuth_wand = new ItemBismuthWand();
	
	//projectile
	public static ItemBismuthBall item_bismuth_ball = new ItemBismuthBall("bismuth_ball");

	// loot
	public static final Item sword_part = new ItemSwordPart();
	public static final Item ghast_boss_tentacle_d = new ItemGhastBossDTentacle();
	public static final Item ghast_boss_tentacle_k = new ItemGhastBossKTentacle();
	public static final Item ghast_boss_tentacle_s = new ItemGhastBossSTentacle();
	public static final Item ghast_boss_tentacle_b = new ItemGhastBossBTentacle();
	public static final Item tentacle_soup = new ItemTentacleSoup();
	public static final Item bismuth_orb = new ItemBismuthOrb();

	public static void setItemName(Item item, String name) {
		items.add(item.setRegistryName(KinoMod.MODID, name).setUnlocalizedName(KinoMod.MODID + "." + name));
	}

	public static void setItemToolName(ItemTool item, String name) {
		itemtool.add(
				(ItemTool) item.setRegistryName(KinoMod.MODID, name).setUnlocalizedName(KinoMod.MODID + "." + name));
	}

	public static void setItemArmorName(ItemArmor item, String name) {
		itemsarmor.add(
				(ItemArmor) item.setRegistryName(KinoMod.MODID, name).setUnlocalizedName(KinoMod.MODID + "." + name));
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
