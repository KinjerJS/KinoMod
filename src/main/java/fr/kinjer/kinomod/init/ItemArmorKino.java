package fr.kinjer.kinomod.init;

import fr.kinjer.kinomod.KinoMod;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemArmorKino extends ItemArmor {
	
	public ItemArmorKino(String name, ArmorMaterial material, int renderIndex, EntityEquipmentSlot equipmentSlot) {
		super(material, renderIndex, equipmentSlot);
		setCreativeTab(KinoMod.tabKino);
		ItemsMod.itemsarmor.add((ItemArmor) this.setRegistryName(KinoMod.MODID, name).setUnlocalizedName(KinoMod.MODID + "." + name));
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		boolean head = player.inventory.armorInventory.get(3).getItem() == ItemsMod.helmet_item;
		boolean chestplate = player.inventory.armorInventory.get(2).getItem() == ItemsMod.chestplate_item;
		boolean leggings = player.inventory.armorInventory.get(1).getItem() == ItemsMod.leggings_item;
		boolean boots = player.inventory.armorInventory.get(0).getItem() == ItemsMod.boots_item;
		if(head && chestplate && leggings && boots) {
			player.capabilities.allowFlying = true;
			player.fallDistance = 0.0f;
			player.capabilities.setFlySpeed(0.15F);
			player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(80);
			player.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
		}else {
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
