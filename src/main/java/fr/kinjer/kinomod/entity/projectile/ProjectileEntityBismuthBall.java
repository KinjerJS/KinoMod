package fr.kinjer.kinomod.entity.projectile;

import fr.kinjer.kinomod.KinoMod;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ProjectileEntityBismuthBall extends EntityThrowable {

	public ProjectileEntityBismuthBall(World world) {
		
		super(world);

	}

	public ProjectileEntityBismuthBall(World world, EntityLivingBase thrower) {
		
		super(world, thrower);

	}

	public ProjectileEntityBismuthBall(World world, double x, double y, double z) {
		
		super(world, x, y, z);

	}
	
	@Override
	protected void onImpact(RayTraceResult result) {
		
		if (!this.world.isRemote) {
			
			setDead();
			if (result.entityHit instanceof EntityLivingBase) {
				
				EntityLivingBase entity = (EntityLivingBase) result.entityHit;
				entity.attackEntityFrom(KinoMod.Bismuth, 4.0F);
			}
		}
	}
}
