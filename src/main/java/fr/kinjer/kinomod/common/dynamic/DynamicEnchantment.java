package fr.kinjer.kinomod.common.dynamic;

import net.minecraft.enchantment.Enchantment;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class DynamicEnchantment {

    protected final Type type;
    @Nullable
    protected final Enchantment enchantment;
    protected int levelAddition;

    public DynamicEnchantment(Type type, @Nonnull Enchantment enchantment, int levelAddition) {
        if(!type.hasEnchantmentTag()) {
            throw new IllegalArgumentException("Tried to create belt enchantment that doesn't requires a std. enchantment together with an enchantment!");
        }
        this.type = type;
        this.enchantment = enchantment;
        this.levelAddition = levelAddition;
    }

    public DynamicEnchantment(Type type, int levelAddition) {
        if(type.hasEnchantmentTag()) {
            throw new IllegalArgumentException("Tried to create belt enchantment that requires a std. enchantment without an enchantment!");
        }
        this.type = type;
        this.enchantment = null;
        this.levelAddition = levelAddition;
    }

    public Type getType() {
        return type;
    }

    @Nullable
    public Enchantment getEnchantment() {
        return enchantment;
    }

    public int getLevelAddition() {
        return levelAddition;
    }

    public void setLevelAddition(int levelAddition) {
        this.levelAddition = levelAddition;
    }

    @Nonnull
    public DynamicEnchantment copy() {
        return this.copy(this.getLevelAddition());
    }

    @Nonnull
    public DynamicEnchantment copy(int level) {
        if (this.getType().hasEnchantmentTag()) {
            return new DynamicEnchantment(this.getType(), this.getEnchantment(), level);
        } else {
            return new DynamicEnchantment(this.type, level);
        }
    }

    public enum Type {

        ADD_TO_SPECIFIC,
        ADD_TO_EXISTING_SPECIFIC,
        ADD_TO_EXISTING_ALL(false);

        private final boolean hasEnchantmentTag;

        Type() {
            this(true);
        }

        Type(boolean hasEnchantmentTag) {
            this.hasEnchantmentTag = hasEnchantmentTag;
        }

        public boolean hasEnchantmentTag() {
            return hasEnchantmentTag;
        }
    }
}
