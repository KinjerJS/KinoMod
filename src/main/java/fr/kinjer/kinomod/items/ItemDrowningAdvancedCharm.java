package fr.kinjer.kinomod.items;

import java.util.List;

import javax.annotation.Nullable;

import fr.kinjer.kinomod.utils.KeyBoard;
import fr.kinjer.kinomod.utils.Localizer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDrowningAdvancedCharm extends ItemCharm
{
	public ItemDrowningAdvancedCharm()
	{
		super("charm_of_drowning_advanced");
	}	
	
	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
		if (itemstack.getItemDamage()==0 && player.ticksExisted%39==0) {
			player.addPotionEffect(new PotionEffect(MobEffects.HASTE,40,3,true,true));
			player.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING,40,0,true,true));
			player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION,11*20,0,true,true));
		}
	}
	
	@Override
	public boolean hasEffect(ItemStack par1ItemStack) {
		return true;
	}
	
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> l, ITooltipFlag flagIn)
    {
		if(!KeyBoard.isShiftKeyDown()) {
			l.add(Localizer.shiftDetails());
			return;
		}
		
		l.add("* "+Localizer.localize(MobEffects.HASTE.getName()) + " " + Localizer.numberLocalize(4));
		l.add("* "+Localizer.localize(MobEffects.WATER_BREATHING.getName()));
		l.add("* "+Localizer.localize(MobEffects.NIGHT_VISION.getName()));
		
    }


}
