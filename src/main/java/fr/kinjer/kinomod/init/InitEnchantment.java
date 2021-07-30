package fr.kinjer.kinomod.init;

import java.util.ArrayList;
import java.util.List;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.enchantment.EnchantmentBeedingSpell;
import fr.kinjer.kinomod.enchantment.EnchantmentBismuthReflection;
import fr.kinjer.kinomod.enchantment.EnchantmentVigor;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = KinoMod.MODID)
public class InitEnchantment {

	public static final List<Enchantment> ENCHANTMENT = new ArrayList<Enchantment>();

	public static final Enchantment VIGOR = new EnchantmentVigor(Rarity.UNCOMMON, EnumEnchantmentType.ARMOR,
			new EntityEquipmentSlot[] { EntityEquipmentSlot.HEAD, EntityEquipmentSlot.CHEST, EntityEquipmentSlot.LEGS,
					EntityEquipmentSlot.FEET });
	public static final Enchantment BISMUTH_REFLECTION = new EnchantmentBismuthReflection(Rarity.RARE, EnumEnchantmentType.ARMOR,
			new EntityEquipmentSlot[] { EntityEquipmentSlot.HEAD, EntityEquipmentSlot.CHEST, EntityEquipmentSlot.LEGS,
					EntityEquipmentSlot.FEET });
	public static final Enchantment BLEEDING_SPELL = new EnchantmentBeedingSpell(Rarity.VERY_RARE, EnumEnchantmentType.WEAPON,
			new EntityEquipmentSlot[] { EntityEquipmentSlot.MAINHAND, EntityEquipmentSlot.OFFHAND});

	@SubscribeEvent
	public static void vigorFunction(LivingUpdateEvent event) {

		EntityLivingBase living = event.getEntityLiving();
		int level = EnchantmentHelper.getMaxEnchantmentLevel(VIGOR, living);
		BlockPos pos = living.getPosition();
		World world = event.getEntity().world;

		living.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20 + (2 * (level / 2)));

	}
	
	@SubscribeEvent
	public static void bismuthReflectionFunction(LivingUpdateEvent event) {

		EntityLivingBase living = event.getEntityLiving();
		int level = EnchantmentHelper.getMaxEnchantmentLevel(BISMUTH_REFLECTION, living);
		BlockPos pos = living.getPosition();
		World world = event.getEntity().world;

	}
}
