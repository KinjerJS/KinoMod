package fr.kinjer.kinomod.items;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.init.ItemsMod;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ItemArmorKino extends ItemArmor {
	
	public ItemArmorKino(String name, ArmorMaterial material, int renderIndex, EntityEquipmentSlot equipmentSlot) {
		super(material, renderIndex, equipmentSlot);
		setCreativeTab(KinoMod.tabKino);
		ItemsMod.itemsarmor.add((ItemArmor) this.setRegistryName(KinoMod.MODID, name).setUnlocalizedName(KinoMod.MODID + "." + name));
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		if(isFullArmor(player)) {
			player.capabilities.allowFlying = true;
			player.stepHeight = 1.0625F;
			player.fallDistance = 0.0f;
			player.capabilities.setFlySpeed(0.15F);
			player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(80);
			player.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
			player.setFire(0);
			player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 20*13,0,false,false));
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
	
	public static boolean isFullArmor(EntityPlayer player) {
		boolean head = player.inventory.armorInventory.get(3).getItem() == ItemsMod.helmet_item;
		boolean chestplate = player.inventory.armorInventory.get(2).getItem() == ItemsMod.chestplate_item;
		boolean leggings = player.inventory.armorInventory.get(1).getItem() == ItemsMod.leggings_item;
		boolean boots = player.inventory.armorInventory.get(0).getItem() == ItemsMod.boots_item;
		return head && chestplate && leggings && boots;
	}
	
}
