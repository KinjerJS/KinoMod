package fr.kinjer.kinomod.items;

import baubles.common.items.ItemRing;
import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.init.InitItems;
import fr.kinjer.kinomod.items.base.BaseKinoBaubleRing;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

public class ItemBismuthRing extends BaseKinoBaubleRing {

public static final String NAME = "bismuth_ring";
	
	public ItemBismuthRing() {
		super(NAME);
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
		return true;
	}
}
