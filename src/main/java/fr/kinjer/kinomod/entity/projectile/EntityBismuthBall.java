package fr.kinjer.kinomod.entity.projectile;

import javax.annotation.Nonnull;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.common.CommonProxy;
import fr.kinjer.kinomod.init.InitItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityEvent;

public class EntityBismuthBall extends EntityArrow {

	public EntityBismuthBall(World world) {
		super(world);
		this.setSize(0.005F, 0.005F);
		this.pickupStatus = EntityArrow.PickupStatus.DISALLOWED;
	}

	public EntityBismuthBall(World world, EntityLivingBase shooter) {
		super(world, shooter);

		if (shooter instanceof EntityPlayer) {
			this.pickupStatus = EntityArrow.PickupStatus.DISALLOWED;
		}
	}

	public EntityBismuthBall(World world, double x, double y, double z) {
		super(world, x, y, z);
	}

	@Override
	public void setDamage(double damage) {
		super.setDamage(0.0D);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		BlockPos blockpos = new BlockPos(this.posX, this.posY, this.posZ);
		IBlockState iblockstate = this.world.getBlockState(blockpos);

		if (iblockstate.getMaterial() != Material.AIR) {
			AxisAlignedBB axisalignedbb = iblockstate.getCollisionBoundingBox(this.world, blockpos);

			if (axisalignedbb != Block.NULL_AABB && axisalignedbb.offset(blockpos).contains(new Vec3d(this.posX, this.posY, this.posZ))) {
				this.inGround = true;
			}
		}

		if (this.inGround) {
			this.setDead();
		}
	}

	@Override
	@Nonnull
	public ItemStack getArrowStack() {
		return new ItemStack(InitItems.item_bismuth_ball);
	}

	@Override
	public void arrowHit(EntityLivingBase living) {
		super.arrowHit(living);
		if (!world.isRemote) {
			living.attackEntityFrom(CommonProxy.bismuthProjectileDamage, 15.0F);
			world.createExplosion(living, living.posX, living.posY, living.posZ, 0.5F, false);
		}
	}
}
