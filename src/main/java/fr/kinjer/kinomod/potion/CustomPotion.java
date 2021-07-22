package fr.kinjer.kinomod.potion;

import java.util.List;

import javax.annotation.Nullable;

import fr.kinjer.kinomod.KinoMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
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
	
	/**
     * allows items to add custom lines of information to the mouseover description
     */
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        PotionUtils.addPotionTooltip(stack, tooltip, 1.0F);
    }
}
