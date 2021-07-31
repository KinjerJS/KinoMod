package fr.kinjer.kinomod.enchantment;

import java.util.UUID;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.init.InitEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.codec.binary.StringUtils;

public class EnchantmentVigor extends Enchantment {
	
	public EnchantmentVigor(Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots) {
		super(Rarity.UNCOMMON, EnumEnchantmentType.ARMOR, new EntityEquipmentSlot[] { EntityEquipmentSlot.HEAD,
				EntityEquipmentSlot.CHEST, EntityEquipmentSlot.LEGS, EntityEquipmentSlot.FEET });
		this.setName("vigor");
		this.setRegistryName(new ResourceLocation(KinoMod.MODID + ":vigor"));
		
		InitEnchantment.ENCHANTMENT.add(this);
	}
	
	@Override
	public int getMinEnchantability(int enchantmentLevel) {
		return 20 * (enchantmentLevel - 1);
	}

	@Override
	public int getMaxEnchantability(int enchantmentLevel) {
		return super.getMinEnchantability(enchantmentLevel) + 10;
	}

	@Override
	public int getMaxLevel() {
		return 3;
	}

	@Override
	protected boolean canApplyTogether(Enchantment ench) {
		return super.canApplyTogether(ench);
	}
}
