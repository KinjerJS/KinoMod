package fr.kinjer.kinomod.items;

import java.util.HashSet;
import java.util.Set;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.init.ItemsMod;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class ItemArmorKino extends ItemArmor {
	
	private static final Set<String> damageNegations = new HashSet<>();
	
	public ItemArmorKino(String name, ArmorMaterial material, int renderIndex, EntityEquipmentSlot equipmentSlot) {
		super(material, renderIndex, equipmentSlot);
		setCreativeTab(KinoMod.tabKino);
		
		damageNegations.add(DamageSource.DROWN.damageType);
		damageNegations.add(DamageSource.FALL.damageType);
		damageNegations.add(DamageSource.LAVA.damageType);
		damageNegations.add(DamageSource.IN_WALL.damageType);
		damageNegations.add(DamageSource.STARVE.damageType);
		damageNegations.add(DamageSource.IN_FIRE.damageType);
		damageNegations.add(DamageSource.ON_FIRE.damageType);
		damageNegations.add(DamageSource.HOT_FLOOR.damageType);
		damageNegations.add(DamageSource.FLY_INTO_WALL.damageType);
		
		ItemsMod.itemsarmor.add((ItemArmor) this.setRegistryName(KinoMod.MODID, name).setUnlocalizedName(KinoMod.MODID + "." + name));
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		if(world.isRemote) {
		if(isFullArmor(player)) {
			if (player.isBurning()) {
				player.extinguish();
			}
			player.capabilities.allowFlying = true;
			player.stepHeight = 1.0625F;
			player.fallDistance = 0.0f;
			player.capabilities.setFlySpeed(0.15F);
			player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(80);
			player.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
			player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 20*13,0,false,false));
			
			if (player.isInWater()) {
				player.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 40, 0, false, false));
			}
			
		}else {
			player.stepHeight = 0.5F;
			player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20);
			player.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.0D);
			player.capabilities.setFlySpeed(0.05F);
			if(!player.isCreative()) {
			player.capabilities.allowFlying = false;
			player.capabilities.isFlying = false;
			}
		}
		}
	}
	
	public static void onPlayerAttacked(LivingDamageEvent event) {
		if (event.getEntity() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntity();
			if (damageNegations.contains(event.getSource().damageType)
					&& isFullArmor(player)) {
				event.setCanceled(true);
			}
		}
	}
	
	public static boolean isFullArmor(EntityPlayer player) {
		boolean head = player.inventory.armorInventory.get(3).getItem() == ItemsMod.helmet_item;
		boolean chestplate = player.inventory.armorInventory.get(2).getItem() == ItemsMod.chestplate_item;
		boolean leggings = player.inventory.armorInventory.get(1).getItem() == ItemsMod.leggings_item;
		boolean boots = player.inventory.armorInventory.get(0).getItem() == ItemsMod.boots_item;
		return head && chestplate && leggings && boots;
	}
	
}
