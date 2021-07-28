package fr.kinjer.kinomod.potion;

import fr.kinjer.kinomod.KinoMod;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CustomPotion extends Potion {
	
    private int statusIconIndex = -1;
	
	public CustomPotion(String name, boolean isBadPotion, int colour, int iconIndexX, int iconIndexY) {
		super(isBadPotion, colour);
		this.setPotionName("effect." + name);
		setIconIndex(iconIndexX, iconIndexY);
		setRegistryName(new ResourceLocation(KinoMod.MODID + ":" + name));
	}
    
	@SideOnly(Side.CLIENT)
    public boolean hasStatusIcon()
    {
    	Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(KinoMod.MODID + ":" + "textures/gui/potion_effects.png"));
		return true;
    }
}