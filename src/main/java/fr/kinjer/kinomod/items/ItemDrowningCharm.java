package fr.kinjer.kinomod.items;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import fr.kinjer.kinomod.utils.KeyBoard;
import fr.kinjer.kinomod.utils.Localizer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.client.event.GuiScreenEvent.PotionShiftEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDrowningCharm extends ItemCharm
{
	public ItemDrowningCharm()
	{
		super("charm_of_drowning");
	}	
	
	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
		if (itemstack.getItemDamage()==0 && player.ticksExisted%39==0) {
			player.addPotionEffect(new PotionEffect(MobEffects.HASTE,40,1,true,true));
			player.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING,40,0,true,true));
			player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION,13*20,0,true,true));
			if(!player.isInWater())
			player.attackEntityFrom(DamageSource.DROWN, 2.1F);
		}
	}
	
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> l, ITooltipFlag flagIn)
    {
		if(!KeyBoard.isShiftKeyDown()) {
			l.add(Localizer.shiftDetails());
			return;
		}
		
		l.add("* "+Localizer.localize(MobEffects.HASTE.getName()) + " " + Localizer.numberLocalize(2));
		l.add("* "+Localizer.localize(MobEffects.WATER_BREATHING.getName()));
		l.add("* "+Localizer.localize(MobEffects.NIGHT_VISION.getName()));
		l.add("* �4"+Localizer.localize("kinomod.charmdrowning.damagewater"));
		
    }

}
