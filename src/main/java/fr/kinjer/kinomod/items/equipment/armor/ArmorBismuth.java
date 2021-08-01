package fr.kinjer.kinomod.items.equipment.armor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.init.InitItems;
import fr.kinjer.kinomod.utils.UtilsLocalizer;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.items.IItemHandler;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.FoodStats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;

public class ArmorBismuth extends ItemArmor {

	public static final String DURABILITY = UtilsLocalizer.localize("tooltip.durability");
	public static final String INFINITE = UtilsLocalizer.localize("tooltip.infinite");

	private static final List<String> damageNegations = new ArrayList<>();

	public ArmorBismuth(String name, ArmorMaterial material, int renderIndex, EntityEquipmentSlot equipmentSlot) {
		super(material, renderIndex, equipmentSlot);
		setCreativeTab(KinoMod.tabKino);
		InitItems.setItemArmorName(this, name);	
		damageNegations.add(DamageSource.IN_FIRE.damageType);
		damageNegations.add(DamageSource.LIGHTNING_BOLT.damageType);
		damageNegations.add(DamageSource.ON_FIRE.damageType);
		damageNegations.add(DamageSource.LAVA.damageType);
		damageNegations.add(DamageSource.HOT_FLOOR.damageType);
		damageNegations.add(DamageSource.IN_WALL.damageType);
		damageNegations.add(DamageSource.CRAMMING.damageType);
		damageNegations.add(DamageSource.DROWN.damageType);
		damageNegations.add(DamageSource.STARVE.damageType);
		damageNegations.add(DamageSource.CACTUS.damageType);
		damageNegations.add(DamageSource.FALL.damageType);
		damageNegations.add(DamageSource.FLY_INTO_WALL.damageType);
	    damageNegations.add(DamageSource.ANVIL.damageType);
	    damageNegations.add(DamageSource.FALLING_BLOCK.damageType);
	    damageNegations.add(DamageSource.DRAGON_BREATH.damageType);
	    damageNegations.add(DamageSource.FIREWORKS.damageType);
	    
	}
	
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> l, ITooltipFlag flagIn) {
		int damage = stack.getMaxDamage() - stack.getItemDamage();
		l.add("§7" + DURABILITY + " §7: " + "§4" +INFINITE);
	}

	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		return repair.getItem() == InitItems.bismuth;
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {

		if (isFullArmor(player)) {
			if (player.isBurning())
				player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 5, 0, true, false));
			player.extinguish();
			FoodStats foodStats = player.getFoodStats();
			if (player.inventory.hasItemStack(new ItemStack(InitItems.tentacle_soup)))
				if (player.ticksExisted % 100 == 0 && foodStats.needFood())
					foodStats.addStats(5, 5F);
			player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(80);
			player.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
			player.stepHeight = 1.0625F;
			player.fallDistance = 0.0f;
			boolean flying = player.capabilities.isFlying;
			boolean swimming = player.isInsideOfMaterial(Material.WATER) || player.isInWater();
			if (world.isRemote) {
				player.capabilities.allowFlying = true;
				player.capabilities.setFlySpeed(0.15F);
			}
			player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 20 * 13, 0, false, false));
			if (player.isInWater()) {
				player.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 5, 0, true, false));
			}
		} else {
			if (world.isRemote) {
				player.capabilities.setFlySpeed(0.05F);
				if (!player.isCreative()) {
					player.capabilities.allowFlying = false;
					player.capabilities.isFlying = false;
				}
			}
			player.stepHeight = 0.5F;
			player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20);
			player.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.0D);
		}
	}

	@SubscribeEvent
	public static void onPlayerAttacked(LivingAttackEvent event) {
		if (event.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			if (isFullArmor(player) && damageNegations.contains(event.getSource().damageType))
				event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public void updatePlayerAbilityStatus(LivingUpdateEvent event) {
		if (event.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();

			Boolean hasSet = isFullArmor(player);
			if (hasSet) {

			}
		}
	}

	public static boolean isFullArmor(EntityPlayer player) {
		Item head = player.inventory.armorInventory.get(3).getItem();
		Item chestplate = player.inventory.armorInventory.get(2).getItem();
		Item leggings = player.inventory.armorInventory.get(1).getItem();
		Item boots = player.inventory.armorInventory.get(0).getItem();
		return head != null && head == InitItems.helmet_item && chestplate != null
				&& chestplate == InitItems.chestplate_item && leggings != null && leggings == InitItems.leggings_item
				&& boots != null && boots == InitItems.boots_item;
	}
}
