package fr.kinjer.kinomod.enchantment;

import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import fr.kinjer.kinomod.common.dynamic.DynamicEnchantment;

public class EnchantmentBelt extends DynamicEnchantment {

    public EnchantmentBelt(Type type, @Nonnull Enchantment enchantment, int levelAddition) {
        super(type, enchantment, levelAddition);
    }

    public EnchantmentBelt(Type type, int levelAddition) {
        super(type, levelAddition);
    }

    @SideOnly(Side.CLIENT)
    public String getDescription() {
        String unlocKey = "enchantment.belt." + this.type.name().toLowerCase() + ".name";
        String locLevels = I18n.format("enchantment.belt.level." + (this.levelAddition == 1 ? "one" : "more"));
        if (this.type.hasEnchantmentTag()) {
            String locEnch = I18n.format(this.enchantment.getName());
            return I18n.format(unlocKey, String.valueOf(this.levelAddition), locLevels, locEnch);
        } else {
            return I18n.format(unlocKey, String.valueOf(this.levelAddition), locLevels);
        }
    }

    public boolean canMerge(EnchantmentBelt other) {
        return this.type.equals(other.type) && (!this.type.hasEnchantmentTag() || this.enchantment.equals(other.enchantment));
    }

    public void merge(EnchantmentBelt src) {
        if(canMerge(src)) {
            this.levelAddition += src.levelAddition;
        }
    }

    public NBTTagCompound serialize() {
        NBTTagCompound cmp = new NBTTagCompound();
        cmp.setInteger("type", this.type.ordinal());
        cmp.setInteger("level", this.levelAddition);
        if(this.type.hasEnchantmentTag()) {
            cmp.setString("ench", this.enchantment.getRegistryName().toString());
        }
        return cmp;
    }

    @Nullable
    public static EnchantmentBelt deserialize(NBTTagCompound cmp) {
        int typeId = cmp.getInteger("type");
        Type type = Type.values()[MathHelper.clamp(typeId, 0, Type.values().length - 1)];
        int level = Math.max(0, cmp.getInteger("level"));
        if(type.hasEnchantmentTag()) {
            ResourceLocation res = new ResourceLocation(cmp.getString("ench"));
            //Disallow dungeontactics enchantments on the prism; see #1302
            if (res.getResourceDomain().equals("dungeontactics")) {
                return null;
            }

            Enchantment e = ForgeRegistries.ENCHANTMENTS.getValue(res);
            if(e != null) {
                return new EnchantmentBelt(type, e, level);
            }
        } else {
            return new EnchantmentBelt(type, level);
        }
        return null;
    }
}
