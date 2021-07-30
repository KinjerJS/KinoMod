package fr.kinjer.kinomod.common.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.WeightedRandom;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import javax.annotation.Nullable;

import fr.kinjer.kinomod.common.data.ConfigDataAdapter;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class EnchantmentBeltRegistry implements ConfigDataAdapter {

    private static final Random rand = new Random();
    public static final EnchantmentBeltRegistry INSTANCE = new EnchantmentBeltRegistry();

    private static List<EnchantmentBeltWeighted> possibleEnchants = new LinkedList<>();

    private EnchantmentBeltRegistry() {}

    @Override
    public Iterable<EnchantmentBeltWeighted> getDefaultDataSets() {
        List<EnchantmentBeltWeighted> enchantments = new LinkedList<>();
        for (Enchantment e : ForgeRegistries.ENCHANTMENTS.getValues()) {
            if(!e.isCurse()) { //Cause fck curses on this.
                Enchantment.Rarity rarity = e.getRarity();
                enchantments.add(new EnchantmentBeltWeighted(e, rarity == null ? 5 : rarity.getWeight()));
            }
        }
        return enchantments;
    }

    @Nullable
    public static Enchantment getRandomEnchant() {
        if(possibleEnchants.isEmpty()) {
            return null;
        }
        return WeightedRandom.getRandomItem(rand, possibleEnchants).getEnchantment();
    }

    public static boolean canBeInfluenced(Enchantment ench) {
        for (EnchantmentBeltWeighted e : possibleEnchants) {
            if(e.getEnchantment().equals(ench)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public LoadPhase getLoadPhase() {
        return LoadPhase.INIT;
    }

    @Override
    public String getDataFileName() {
        return "belt_enchantments";
    }

    @Override
    public String getDescription() {
        return "Defines a whitelist of which enchantments can be rolled and buffed by the enchantment-belt. The higher the weight, the more likely that roll is selected." +
                "Format: <enchantment-registry-name>:<weight>";
    }

    @Nullable
    @Override
    public Optional<EnchantmentBeltWeighted> appendDataSet(String str) {
    	EnchantmentBeltWeighted ench = EnchantmentBeltWeighted.deserialize(str);
        if(ench == null) {
            return Optional.empty();
        }
        possibleEnchants.add(ench);
        return Optional.of(ench);
    }

    @Override
    public void resetRegistry() {
        possibleEnchants.clear();
    }
}
