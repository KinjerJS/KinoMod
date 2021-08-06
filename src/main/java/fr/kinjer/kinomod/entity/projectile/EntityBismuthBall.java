package fr.kinjer.kinomod.entity.projectile;

import javax.annotation.Nonnull;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.common.CommonProxy;
import fr.kinjer.kinomod.init.InitItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityEvent;

public class EntityBismuthBall extends EntityArrow {

	public EntityBismuthBall(World world) {
		super(world);
		this.setSize(0.05F, 0.05F);
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
	
//	protected void onHit(RayTraceResult raytraceResultIn)
//    {
//        Entity entity = raytraceResultIn.entityHit;
//
//        if (entity != null)
//        {
//            float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
//            int i = MathHelper.ceil((double)f * this.getDamage());
//
//            if (this.getIsCritical())
//            {
//                i += this.rand.nextInt(i / 2 + 2);
//            }
//
//            DamageSource damagesource;
//
//            if (this.shootingEntity == null)
//            {
//                damagesource = DamageSource.causeArrowDamage(this, this);
//            }
//            else
//            {
//                damagesource = DamageSource.causeArrowDamage(this, this.shootingEntity);
//            }
//
//            if (this.isBurning() && !(entity instanceof EntityEnderman))
//            {
//                entity.setFire(5);
//            }
//
//            if (entity.attackEntityFrom(damagesource, (float)i))
//            {
//                if (entity instanceof EntityLivingBase)
//                {
//                    EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
//
//                    if (!this.world.isRemote)
//                    {
//                        entitylivingbase.setArrowCountInEntity(entitylivingbase.getArrowCountInEntity() + 1);
//                    }
//
//                    if (this.knockbackStrength > 0)
//                    {
//                        float f1 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
//
//                        if (f1 > 0.0F)
//                        {
//                            entitylivingbase.addVelocity(this.motionX * (double)this.knockbackStrength * 0.6000000238418579D / (double)f1, 0.1D, this.motionZ * (double)this.knockbackStrength * 0.6000000238418579D / (double)f1);
//                        }
//                    }
//
//                    if (this.shootingEntity instanceof EntityLivingBase)
//                    {
//                        EnchantmentHelper.applyThornEnchantments(entitylivingbase, this.shootingEntity);
//                        EnchantmentHelper.applyArthropodEnchantments((EntityLivingBase)this.shootingEntity, entitylivingbase);
//                    }
//
//                    this.arrowHit(entitylivingbase);
//
//                    if (this.shootingEntity != null && entitylivingbase != this.shootingEntity && entitylivingbase instanceof EntityPlayer && this.shootingEntity instanceof EntityPlayerMP)
//                    {
//                        ((EntityPlayerMP)this.shootingEntity).connection.sendPacket(new SPacketChangeGameState(6, 0.0F));
//                    }
//                }
//
//                this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
//
//                if (!(entity instanceof EntityEnderman))
//                {
//                    this.setDead();
//                }
//            }
//            else
//            {
//                this.motionX *= -0.10000000149011612D;
//                this.motionY *= -0.10000000149011612D;
//                this.motionZ *= -0.10000000149011612D;
//                this.rotationYaw += 180.0F;
//                this.prevRotationYaw += 180.0F;
//                this.ticksInAir = 0;
//
//                if (!this.world.isRemote && this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ < 0.0010000000474974513D)
//                {
//                    if (this.pickupStatus == EntityArrow.PickupStatus.ALLOWED)
//                    {
//                        this.entityDropItem(this.getArrowStack(), 0.1F);
//                    }
//
//                    this.setDead();
//                }
//            }
//        }
//        else
//        {
//            BlockPos blockpos = raytraceResultIn.getBlockPos();
//            this.xTile = blockpos.getX();
//            this.yTile = blockpos.getY();
//            this.zTile = blockpos.getZ();
//            IBlockState iblockstate = this.world.getBlockState(blockpos);
//            this.inTile = iblockstate.getBlock();
//            this.inData = this.inTile.getMetaFromState(iblockstate);
//            this.motionX = (double)((float)(raytraceResultIn.hitVec.x - this.posX));
//            this.motionY = (double)((float)(raytraceResultIn.hitVec.y - this.posY));
//            this.motionZ = (double)((float)(raytraceResultIn.hitVec.z - this.posZ));
//            float f2 = MathHelper.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
//            this.posX -= this.motionX / (double)f2 * 0.05000000074505806D;
//            this.posY -= this.motionY / (double)f2 * 0.05000000074505806D;
//            this.posZ -= this.motionZ / (double)f2 * 0.05000000074505806D;
//            this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
//            this.inGround = true;
//            this.arrowShake = 7;
//            this.setIsCritical(false);
//
//            if (iblockstate.getMaterial() != Material.AIR)
//            {
//                this.inTile.onEntityCollidedWithBlock(this.world, blockpos, iblockstate, this);
//            }
//        }
//    }

}
