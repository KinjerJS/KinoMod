package fr.kinjer.kinomod.items;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
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
			//player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE,40,1,true,true));
		}
	}
	
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
		//Drawning (impossible de récupérer des bulles d'oxygène à la surface) + Aqua Infinity, Grâce du dauphin et Récupération de l'oxygène sous l'eau
    }

}
