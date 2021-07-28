package fr.kinjer.kinomod.enchantment;

import java.util.Random;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.init.EnchantmentInit;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

public class EnchantmentBismuthReflection extends Enchantment {

	public EnchantmentBismuthReflection(Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots) {
		super(Rarity.UNCOMMON, EnumEnchantmentType.ARMOR, new EntityEquipmentSlot[] { EntityEquipmentSlot.HEAD,
				EntityEquipmentSlot.CHEST, EntityEquipmentSlot.LEGS, EntityEquipmentSlot.FEET });
		this.setName("bismuth_reflection");
		this.setRegistryName(new ResourceLocation(KinoMod.MODID + ":bismuth_reflection"));

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
		return 3;
	}

	@Override
	public boolean canApply(ItemStack stack) {
		return stack.getItem() instanceof ItemArmor ? true : super.canApply(stack);
	}

	@Override
	public void onUserHurt(EntityLivingBase user, Entity attacker, int level) {
		ItemStack itemstack = EnchantmentHelper.getEnchantedItem(EnchantmentInit.BISMUTH_REFLECTION, user);

		if (attacker != null) {
			attacker.attackEntityFrom(KinoMod.DamageSourceBismuth(user), (float) getDamage(level));
		}

		if (!itemstack.isEmpty()) {
			damageArmor(itemstack, 1, user);
		}
	}

	public static boolean shouldHit(int level, Random rnd) {
		if (level <= 0) {
			return false;
		} else {
			return rnd.nextFloat() < 0.15F * (float) level;
		}
	}

	public static int getDamage(int level) {
		return level > 10 ? level - 10 : 5;
	}

	private void damageArmor(ItemStack stack, int amount, EntityLivingBase entity) {
		int slot = -1;
		int x = 0;
		for (ItemStack i : entity.getArmorInventoryList()) {
			if (i == stack) {
				slot = x;
				break;
			}
			x++;
		}
		if (slot == -1 || !(stack.getItem() instanceof net.minecraftforge.common.ISpecialArmor)) {
			stack.damageItem(1, entity);
			return;
		}
		net.minecraftforge.common.ISpecialArmor armor = (net.minecraftforge.common.ISpecialArmor) stack.getItem();
		armor.damageArmor(entity, stack, KinoMod.DamageSourceBismuth(entity), amount, slot);
	}
}
