package fr.kinjer.kinomod.init;

import fr.kinjer.kinomod.potion.CustomPotion;
import net.minecraft.client.gui.Gui;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.potion.PotionType;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class InitPotion {

	public static final Potion BLEEDING_EFFECT = new CustomPotion("bleeding_effect", true, 13505316, 0, 0);
	
	public static final PotionType BLEEDING = new PotionType("bleeding",
			new PotionEffect[] { new PotionEffect(BLEEDING_EFFECT, 300) }).setRegistryName("bleeding");
	public static final PotionType LONG_BLEEDING = new PotionType("bleeding",
			new PotionEffect[] { new PotionEffect(BLEEDING_EFFECT, 600) }).setRegistryName("long_bleeding");

	public static void registerPotions() {

		registerPotions(BLEEDING, LONG_BLEEDING, BLEEDING_EFFECT);

		registerPotionMixes();

	}

	private static void registerPotions(PotionType defaultPotion, PotionType longPotion, Potion effect) {

		ForgeRegistries.POTIONS.register(effect);
		ForgeRegistries.POTION_TYPES.register(defaultPotion);
		ForgeRegistries.POTION_TYPES.register(longPotion);

	}
	
	private static void registerPotionsEffectOnly(Potion effect) {

		ForgeRegistries.POTIONS.register(effect);

	}

	private static void registerPotionMixes() {

		PotionHelper.addMix(BLEEDING, Items.REDSTONE, LONG_BLEEDING);
		PotionHelper.addMix(PotionTypes.AWKWARD, InitItems.bismuth, BLEEDING);

	}
}
