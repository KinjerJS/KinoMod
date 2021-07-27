package fr.kinjer.kinomod.enchantment;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.init.EnchantmentInit;
import fr.kinjer.kinomod.init.PotionInit;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class EnchantmentBeedingSpell extends Enchantment {

	public EnchantmentBeedingSpell(Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots) {
		super(Rarity.VERY_RARE, EnumEnchantmentType.WEAPON,
				new EntityEquipmentSlot[] { EntityEquipmentSlot.MAINHAND, EntityEquipmentSlot.OFFHAND });
		this.setName("bleeding_spell");
		this.setRegistryName(new ResourceLocation(KinoMod.MODID + ":bleeding_spell"));

		EnchantmentInit.ENCHANTMENT.add(this);
	}

	@Override
	public int getMinEnchantability(int enchantmentLevel) {
		return 10 + 20 * (enchantmentLevel - 1);
	}

	@Override
	public int getMaxEnchantability(int enchantmentLevel) {
		return super.getMinEnchantability(enchantmentLevel) + 50;
	}

	@Override
	public int getMaxLevel() {
		return 1;
	}

	@Override
	public void onEntityDamaged(EntityLivingBase user, Entity target, int level) {
		
		((EntityLivingBase) target).addPotionEffect(new PotionEffect(PotionInit.BLEEDING_EFFECT, 20));
		
	}
}
