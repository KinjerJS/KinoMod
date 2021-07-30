package fr.kinjer.kinomod.items.base;

import baubles.common.items.ItemRing;
import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.init.InitItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

public class BaseKinoRing extends ItemRing {

	public String name;
	
	public BaseKinoRing(String name) {
		this.name = name;
		InitItems.setItemName(this, name);
		setCreativeTab(KinoMod.tabKino);
	}
	
	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
		if(this == InitItems.bismuth_ring) {
			if (itemstack.getItemDamage()==0 && player.ticksExisted%39==0) {
				player.addPotionEffect(new PotionEffect(MobEffects.HASTE,40,0,true,true));
			}
		}
	}

	@Override
	public boolean hasEffect(ItemStack par1ItemStack) {
		return false;
	}

}
