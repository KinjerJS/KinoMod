package fr.kinjer.kinomod.items;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.entity.projectile.EntityBismuthBall;
import fr.kinjer.kinomod.items.base.BaseKino;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBismuthBall extends ItemArrow {
	
	public static final String NAME = "bismuth_ball";
	
	public ItemBismuthBall(String name){
		this.setUnlocalizedName(KinoMod.MODID + name);
		this.setRegistryName(name);
		this.setCreativeTab(KinoMod.tabKino);
	}
	
	@Override
	public EntityArrow createArrow(World world, ItemStack stack, EntityLivingBase shooter) {
		return new EntityBismuthBall(world, shooter);
	}
	
	@Override
	public boolean isInfinite(ItemStack stack, ItemStack bow, EntityPlayer player) {
		return true;
	}
	
}