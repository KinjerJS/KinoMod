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

public class ItemFireAdvancedCharm extends ItemCharm
{
	public ItemFireAdvancedCharm()
	{
		super("charm_of_fire_advanced");
	}	
	
	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
		if (itemstack.getItemDamage()==0 && player.ticksExisted%39==0) {
			player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH,40,4,true,true));
		}
	}
	
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> l, ITooltipFlag flagIn)
    {
		if(!KeyBoard.isShiftKeyDown()) {
			l.add(Localizer.shiftDetails());
			return;
		}
		
		l.add(Localizer.localize(MobEffects.STRENGTH.getName()) + " " + Localizer.numberLocalize(5));
		
    }

}
