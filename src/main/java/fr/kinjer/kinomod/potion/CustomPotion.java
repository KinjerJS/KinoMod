package fr.kinjer.kinomod.potion;

import fr.kinjer.kinomod.KinoMod;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

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
