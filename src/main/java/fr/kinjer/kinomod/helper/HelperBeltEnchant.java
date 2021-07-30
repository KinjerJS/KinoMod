package fr.kinjer.kinomod.helper;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;

import javax.annotation.Nullable;

import fr.kinjer.kinomod.common.enchantment.EnchantmentBeltRegistry;
import fr.kinjer.kinomod.enchantment.EnchantmentBelt;
import fr.kinjer.kinomod.items.equipment.ItemKiniumBelt;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class HelperBeltEnchant {

    private static final Random rand = new Random();

    private static float chance2nd = 0.0F;
    private static float chance3rd = 0.0F;

    private static float chance2Level = 0.0F;

    private static float chanceToAll = 1.0F;
    private static float chanceToNonExisting = 0.0F;

    public static void rollBelt(ItemStack stack) {
        if(stack.isEmpty() || !(stack.getItem() instanceof ItemKiniumBelt)) {
            return;
        }

        List<EnchantmentBelt> ench = new ArrayList<>();
        while (mayGetAdditionalRoll(ench)) {
        	EnchantmentBelt.Type type = getRollType(ench);
            if(type != null) {
                int lvl = getRollLevel();
                if(type.hasEnchantmentTag()) {
                    Enchantment e = EnchantmentBeltRegistry.getRandomEnchant();
                    if(e != null) {
                        ench.add(new EnchantmentBelt(type, e, lvl));
                    }
                } else {
                    ench.add(new EnchantmentBelt(type, lvl));
                }
            }
        }
        ItemKiniumBelt.setBeltEnchantments(stack, collapseEnchantments(ench));
    }

    @Nullable
    private static EnchantmentBelt.Type getRollType(List<EnchantmentBelt> existing) {
        int exAll = getAdditionAll(existing);
        switch (existing.size()) {
            case 0:
            case 1:
                if(rand.nextFloat() < chanceToAll) {
                    return EnchantmentBelt.Type.ADD_TO_EXISTING_ALL;
                }
                if(rand.nextFloat() < chanceToNonExisting) {
                    return EnchantmentBelt.Type.ADD_TO_SPECIFIC;
                }
                return EnchantmentBelt.Type.ADD_TO_EXISTING_SPECIFIC;
            case 2:
                if(exAll > 1) {
                    return null;
                } else if(exAll == 1) {
                    if(rand.nextFloat() < chanceToNonExisting) {
                        return EnchantmentBelt.Type.ADD_TO_SPECIFIC;
                    }
                    return EnchantmentBelt.Type.ADD_TO_EXISTING_SPECIFIC;
                } else {
                    if(rand.nextFloat() < chanceToAll) {
                        return EnchantmentBelt.Type.ADD_TO_EXISTING_ALL;
                    }
                    if(rand.nextFloat() < chanceToNonExisting) {
                        return EnchantmentBelt.Type.ADD_TO_SPECIFIC;
                    }
                    return EnchantmentBelt.Type.ADD_TO_EXISTING_SPECIFIC;
                }
            default:
                break;
        }
        return null;
    }

    private static int getRollLevel() {
        if(rand.nextFloat() < chance2Level) {
            return 2;
        }
        return 1;
    }

    private static boolean mayGetAdditionalRoll(List<EnchantmentBelt> existing) {
        if(existing.isEmpty()) return true;
        switch (existing.size()) {
            case 1:
                return rand.nextFloat() < chance2nd;
            case 2:
                return getAdditionAll(existing) < 2 && rand.nextFloat() < chance3rd;
            default:
                break;
        }
        return false;
    }

    private static int getAdditionAll(List<EnchantmentBelt> ench) {
        int i = 0;
        for (EnchantmentBelt e : ench) {
            if(e.getType().equals(EnchantmentBelt.Type.ADD_TO_EXISTING_ALL)) {
                i++;
            }
        }
        return i;
    }

    private static List<EnchantmentBelt> collapseEnchantments(List<EnchantmentBelt> ench) {
        List<EnchantmentBelt> enchantments = new LinkedList<>();
        for (EnchantmentBelt e : ench) {
            boolean found = false;
            for (EnchantmentBelt ex : enchantments) {
                if(ex.canMerge(e)) {
                    ex.merge(e);
                    found = true;
                    break;
                }
            }
            if(!found) {
                enchantments.add(e);
            }
        }
        return enchantments;
    }
}
