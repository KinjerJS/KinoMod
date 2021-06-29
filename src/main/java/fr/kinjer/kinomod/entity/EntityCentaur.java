package fr.kinjer.kinomod.entity;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityCentaur extends EntityCow{

	public EntityCentaur(World worldIn) {
		super(worldIn);
		this.setSize(1F, 2.4F);
	}
	
	@Override
	public EntityCow createChild(EntityAgeable ageable) {
		return new EntityCentaur(world);
	}
	
	@Override
	protected SoundEvent getAmbientSound() {
		return super.getAmbientSound();
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return super.getHurtSound(damageSourceIn);
	}
	
	@Override
	protected SoundEvent getDeathSound() {
		return super.getDeathSound();
	}

}
