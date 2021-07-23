package fr.kinjer.kinomod.potion;

import java.util.List;

import javax.annotation.Nullable;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.init.PotionInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.init.Bootstrap;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CustomPotion extends Potion {
	
	public CustomPotion(String name, boolean isBadPotion, int colour, int iconIndexX, int iconIndexY) {
		super(isBadPotion, colour);
		this.setPotionName("effect." + name);
		setIconIndex(iconIndexX, iconIndexY);
		setRegistryName(new ResourceLocation(KinoMod.MODID + ":" + name));
	}

	@Override
	public boolean hasStatusIcon() {

		Minecraft.getMinecraft().getTextureManager()
				.bindTexture(new ResourceLocation(KinoMod.MODID + "textures/gui/potion_effects.png"));
		return true;

	}
}
