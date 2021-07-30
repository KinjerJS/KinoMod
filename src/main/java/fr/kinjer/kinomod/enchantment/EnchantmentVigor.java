package fr.kinjer.kinomod.enchantment;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.init.InitEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.ResourceLocation;

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
