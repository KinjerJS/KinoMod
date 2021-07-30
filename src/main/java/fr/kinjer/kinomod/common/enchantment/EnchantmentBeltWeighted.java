package fr.kinjer.kinomod.common.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedRandom;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import fr.kinjer.kinomod.KinoMod;

public class EnchantmentBeltWeighted extends WeightedRandom.Item {

    private final Enchantment enchantment;

    public EnchantmentBeltWeighted(Enchantment ench, int weight) {
        super(weight);
        this.enchantment = ench;
    }

    public Enchantment getEnchantment() {
        return enchantment;
    }
    
    @Nonnull
    public String serialize() {
        return this.enchantment.getRegistryName().toString() + ":" + itemWeight;
    }

    @Nullable
    public static EnchantmentBeltWeighted deserialize(String str) {
        String[] spl = str.split(":");
        if(spl.length < 3) {
            return null;
        }
        String domain = spl[0];
        String weight = spl[spl.length - 1];
        StringBuilder path = new StringBuilder();
        for (int i = 1; i < spl.length - 1; i++) {
            if(path.length() > 0) {
                path.append(":"); //Cause that vanishes when splitting...
            }
            path.append(spl[i]);
        }
        //TODO find a better solution than hardcoding (duh)
        String registryName = domain + ":" + path.toString();
        if (registryName.equalsIgnoreCase("cofhcore:holding")) {
            KinoMod.log.info("Ignoring belt enchantment 'cofhcore:holding' as it's prone to cause issues.");
            return null;
        }
        //see #1302
        if (domain.equalsIgnoreCase("dungeontactics")) {
        	KinoMod.log.info("Ignoring belt enchantments for '" + registryName + "' as dungeontactic's enchantments generated through the prism are prone to cause issues.");
            return null;
        }
        Enchantment ench = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(registryName));
        if(ench == null) {
        	KinoMod.log.info("Ignoring whitelist entry " + str + " for belt enchantments - Enchantment does not exist!");
            return null;
        }
        int w;
        try {
            w = Integer.parseInt(weight);
        } catch (NumberFormatException exc) {
        	KinoMod.log.info("Ignoring whitelist entry " + str + " for belt enchantments - last :-separated argument is not a number!");
            return null;
        }
        return new EnchantmentBeltWeighted(ench, w);
    }
    
    public static interface DataSet {

        @Nonnull
        public String serialize();

        public static class StringElement implements DataSet {

            private final String str;

            public StringElement(@Nonnull String str) {
                this.str = str;
            }

            @Nonnull
            @Override
            public String serialize() {
                return str;
            }
        }
    }
}
