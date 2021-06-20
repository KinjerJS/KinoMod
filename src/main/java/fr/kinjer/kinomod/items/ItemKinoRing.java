package fr.kinjer.kinomod.items;

import baubles.common.items.ItemRing;
import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.init.ItemsMod;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

public class ItemKinoRing extends ItemRing {

	public String name;
	
	public ItemKinoRing(String name) {
		this.name = name;
		ItemsMod.setItemName(this, name);
		setCreativeTab(KinoMod.tabKino);
	}
	
	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
		if(this == ItemsMod.bismuth_ring) {
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
