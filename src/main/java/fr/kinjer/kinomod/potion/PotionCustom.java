package fr.kinjer.kinomod.potion;

import fr.kinjer.kinomod.KinoMod;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionCustom extends Potion {
	
    private int statusIconIndex = -1;
	
	public PotionCustom(String name, boolean isBadPotion, int colour, int iconIndexX, int iconIndexY) {
		super(isBadPotion, colour);
		this.setPotionName("effect." + name);
		setIconIndex(iconIndexX, iconIndexY);
		setRegistryName(new ResourceLocation(KinoMod.MODID + ":" + name));
	}
	
	public PotionCustom(String name, boolean isBadPotion, int colour) {
		super(isBadPotion, colour);
		this.setPotionName("effect." + name);
		setRegistryName(new ResourceLocation(KinoMod.MODID + ":" + name));
    }
	
	/**
     * checks if Potion effect is ready to be applied this tick.
     */
	@Override
    public boolean isReady(int duration, int amplifier)
    {
    	return true;
    }
	
	/**
	 * Returns true if this potion has an affect that changes per level (like swiftness, and not like night vision)
	 */
	public boolean canAmplify() {
		return true;
	}
	
	public void affectEntity(Entity thrownPotion, Entity thrower, EntityLivingBase entity, int amplifier, double potency) {
		this.performEffect(entity, amplifier);
	}

	@SideOnly(Side.CLIENT)
    public boolean hasStatusIcon()
    {
    	Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(KinoMod.MODID + ":" + "textures/gui/potion_effects.png"));
		return true;
    }

	public void performEffect(LivingUpdateEvent event, int amplifier)
    {
    }
}
