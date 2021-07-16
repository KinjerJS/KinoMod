package fr.kinjer.kinomod.entity;

import java.util.Random;
import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityGhastBossD extends EntityFlying implements IMob {

	private final BossInfoServer bossInfo = (BossInfoServer) (new BossInfoServer(this.getDisplayName(),
			BossInfo.Color.RED, BossInfo.Overlay.PROGRESS)).setDarkenSky(true);

	private static final DataParameter<Boolean> ATTACKING = EntityDataManager.<Boolean>createKey(EntityGhastBossD.class,
			DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> PHASE = EntityDataManager.<Integer>createKey(EntityGhastBossD.class,
			DataSerializers.VARINT);

	private int explosionStrength = 6;

	public EntityGhastBossD(World worldIn) {
		super(worldIn);
		this.setSize(10.0f, 10.0f);
		this.isImmuneToFire = true;
		this.experienceValue = 2500;
		this.moveHelper = new EntityGhastBossD.GhastMoveHelper(this);
	}

	@Override
	protected void initEntityAI() {
		this.tasks.addTask(5, new EntityGhastBossD.AIRandomFly(this));
		this.tasks.addTask(7, new EntityGhastBossD.AILookAround(this));
		this.tasks.addTask(7, new EntityGhastBossD.AIFireballAttack(this));
		this.targetTasks.addTask(1, new EntityAIFindEntityNearestPlayer(this));
	}

	@SideOnly(Side.CLIENT)
	public boolean isAttacking() {
		return ((Boolean) this.dataManager.get(ATTACKING)).booleanValue();
	}

	@SideOnly(Side.CLIENT)
	public boolean isPhase(int i) {
		return ((Integer) this.dataManager.get(PHASE)).intValue() == i;
	}

	public void setAttacking(boolean attacking) {
		this.dataManager.set(ATTACKING, Boolean.valueOf(attacking));
	}

	public void setPhase(int phase) {
		this.dataManager.set(PHASE, Integer.valueOf(phase));
	}

	public int getPhase() {
		return ((Integer) this.dataManager.get(PHASE)).intValue();
	}
	
	public boolean explosionImune() {
		return this.isImmuneToExplosions();
	}

	public int getFireballStrength() {
		return this.explosionStrength;
	}

	public void onLivingUpdate() {
		super.onLivingUpdate();

		if (this.getHealth() <= 500) {
			this.setPhase(2);
		} else if (this.getHealth() <= 1000) {
			this.setPhase(1);
		} else if (this.getHealth() > 1000) {
			this.setPhase(0);
		}

		if (this.posY >= 128.0D) {
			this.posY -= 1.0D;
		}
	}

	/**
	 * Called when the entity is attacked.
	 */
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (this.isEntityInvulnerable(source)) {
			return true;
		}

		else if (source.getImmediateSource() instanceof EntityLargeFireball
				&& source.getTrueSource() instanceof EntityPlayer) {
			super.attackEntityFrom(source, 100.0F);
			return true;
		} else if (source.getImmediateSource() instanceof EntityPlayer
				&& source.getTrueSource() instanceof EntityPlayer) {
			super.attackEntityFrom(source, 0.0F);
			return false;
		} if (this.isArmored())
        {
            Entity entity = source.getImmediateSource();

              if (entity instanceof EntityArrow)
            {
                return false;
            }
        }
		return super.attackEntityFrom(source, amount);
	}

	private boolean isArmored() {
		return true;
	}

	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(PHASE, Integer.valueOf(0));
		this.dataManager.register(ATTACKING, Boolean.valueOf(false));
	}

	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(2000.0D);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(600.0D);
	}

	public SoundCategory getSoundCategory() {
		return SoundCategory.HOSTILE;
	}

	protected SoundEvent getAmbientSound() {
		return SoundEvents.ENTITY_GHAST_AMBIENT;
	}

	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEvents.ENTITY_GHAST_HURT;
	}

	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_GHAST_DEATH;
	}

	@Nullable
	protected ResourceLocation getLootTable() {
		return LootTableList.ENTITIES_GHAST;
	}

	/**
	 * Add the given player to the list of players tracking this entity. For
	 * instance, a player may track a boss in order to view its associated boss bar.
	 */
	public void addTrackingPlayer(EntityPlayerMP player) {
		super.addTrackingPlayer(player);
		this.bossInfo.addPlayer(player);
	}

	/**
	 * Removes the given player from the list of players tracking this entity. See
	 * {@link Entity#addTrackingPlayer} for more information on tracking.
	 */
	public void removeTrackingPlayer(EntityPlayerMP player) {
		super.removeTrackingPlayer(player);
		this.bossInfo.removePlayer(player);
	}

	@Override
	protected void updateAITasks() {
		super.updateAITasks();
		this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
	}

	/**
	 * Returns the volume for the sounds this mob makes.
	 */
	protected float getSoundVolume() {
		return 10.0F;
	}

	public static void registerFixesGhast(DataFixer fixer) {
		EntityLiving.registerFixesMob(fixer, EntityGhast.class);
	}

	@Override
	public void knockBack(Entity entityIn, float strength, double xRatio, double zRatio) {
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setInteger("ExplosionPower", this.explosionStrength);
	}

	/**
	 * Returns false if this Entity is a boss, true otherwise.
	 */
	public boolean isNonBoss() {
		return false;
	}

	/**
	 * Makes the entity despawn if requirements are reached
	 */
	protected void despawnEntity() {
		this.idleTime = 0;
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);

		if (compound.hasKey("ExplosionPower", 99)) {
			this.explosionStrength = compound.getInteger("ExplosionPower");
		}
	}

	public float getEyeHeight() {
		return 2.6F;
	}

	static class AIFireballAttack extends EntityAIBase {
		private final EntityGhastBossD parentEntity;
		public int attackTimer;
		public int shootTimer;

		public AIFireballAttack(EntityGhastBossD entityGhastBossD) {
			this.parentEntity = entityGhastBossD;
		}

		/**
		 * Returns whether the EntityAIBase should begin execution.
		 */
		public boolean shouldExecute() {
			return this.parentEntity.getAttackTarget() != null;
		}

		/**
		 * Execute a one shot task or start executing a continuous task
		 */
		public void startExecuting() {
			this.attackTimer = 0;
			this.shootTimer = 0;
		}

		/**
		 * Reset the task's internal state. Called when this task is interrupted by
		 * another one
		 */
		public void resetTask() {
			this.parentEntity.setAttacking(false);
			this.parentEntity.setPhase(0);
		}

		/**
		 * Keep ticking a continuous task that has already been started
		 */
		public void updateTask() {
			EntityLivingBase entitylivingbase = this.parentEntity.getAttackTarget();
			double d0 = 64.0D;

			if (entitylivingbase.posY >= 128.0D) {
				entitylivingbase.posY -= 1.0D;
			}

			if (entitylivingbase.getDistanceSq(this.parentEntity) < 4096.0D
					&& this.parentEntity.canEntityBeSeen(entitylivingbase)) {
				World world = this.parentEntity.world;
				++this.attackTimer;

				if (this.attackTimer == 5) {
					world.playEvent((EntityPlayer) null, 1015, new BlockPos(this.parentEntity), 0);
				}

				if (this.attackTimer == 10) {
					double d1 = 10.0D;
					Vec3d vec3d = this.parentEntity.getLook(1.0F);
					double d2 = entitylivingbase.posX - (this.parentEntity.posX + vec3d.x * 4.0D);
					double d3 = entitylivingbase.getEntityBoundingBox().minY + (double) (entitylivingbase.height / 2.0F)
							- (0.5D + this.parentEntity.posY + (double) (this.parentEntity.height / 2.0F));
					double d4 = entitylivingbase.posZ - (this.parentEntity.posZ + vec3d.z * 4.0D);
					world.playEvent((EntityPlayer) null, 1016, new BlockPos(this.parentEntity), 0);
					EntityLargeFireball entitylargefireball = new EntityLargeFireball(world, this.parentEntity, d2, d3,
							d4);
					entitylargefireball.explosionPower = this.parentEntity.getFireballStrength();
					entitylargefireball.posX = this.parentEntity.posX + vec3d.x * 4.0D;
					entitylargefireball.posY = this.parentEntity.posY + (double) (this.parentEntity.height / 2.0F)
							+ 0.5D;
					entitylargefireball.posZ = this.parentEntity.posZ + vec3d.z * 4.0D;
					world.spawnEntity(entitylargefireball);
					this.attackTimer = -20;
				}
			} else if (this.attackTimer > 0) {
				--this.attackTimer;
			}
			this.parentEntity.setAttacking(this.attackTimer > 5);
		}
	}

	static class AILookAround extends EntityAIBase {
		private final EntityGhastBossD parentEntity;

		public AILookAround(EntityGhastBossD entityGhastBossD) {
			this.parentEntity = entityGhastBossD;
			this.setMutexBits(2);
		}

		/**
		 * Returns whether the EntityAIBase should begin execution.
		 */
		public boolean shouldExecute() {
			return true;
		}

		/**
		 * Keep ticking a continuous task that has already been started
		 */
		public void updateTask() {
			if (this.parentEntity.getAttackTarget() == null) {
				this.parentEntity.rotationYaw = -((float) MathHelper.atan2(this.parentEntity.motionX,
						this.parentEntity.motionZ)) * (180F / (float) Math.PI);
				this.parentEntity.renderYawOffset = this.parentEntity.rotationYaw;
			} else {
				EntityLivingBase entitylivingbase = this.parentEntity.getAttackTarget();
				double d0 = 64.0D;

				if (entitylivingbase.getDistanceSq(this.parentEntity) < 4096.0D) {
					double d1 = entitylivingbase.posX - this.parentEntity.posX;
					double d2 = entitylivingbase.posZ - this.parentEntity.posZ;
					this.parentEntity.rotationYaw = -((float) MathHelper.atan2(d1, d2)) * (180F / (float) Math.PI);
					this.parentEntity.renderYawOffset = this.parentEntity.rotationYaw;
				}
			}
		}
	}

	static class AIRandomFly extends EntityAIBase {
		private final EntityGhastBossD parentEntity;

		public AIRandomFly(EntityGhastBossD entityGhastBossD) {
			this.parentEntity = entityGhastBossD;
			this.setMutexBits(1);

			if (parentEntity.posY >= 128.0D) {
				parentEntity.posY -= 1.0D;
			}
		}

		/**
		 * Returns whether the EntityAIBase should begin execution.
		 */
		public boolean shouldExecute() {
			EntityMoveHelper entitymovehelper = this.parentEntity.getMoveHelper();

			if (!entitymovehelper.isUpdating()) {
				return true;
			} else {
				double d0 = entitymovehelper.getX() - this.parentEntity.posX;
				double d1 = entitymovehelper.getY() - this.parentEntity.posY;
				double d2 = entitymovehelper.getZ() - this.parentEntity.posZ;
				double d3 = d0 * d0 + d1 * d1 + d2 * d2;
				return d3 < 1.0D || d3 > 3600.0D;
			}
		}

		/**
		 * Returns whether an in-progress EntityAIBase should continue executing
		 */
		public boolean shouldContinueExecuting() {
			return false;
		}

		/**
		 * Execute a one shot task or start executing a continuous task
		 */
		public void startExecuting() {
			Random random = this.parentEntity.getRNG();
			double d0 = this.parentEntity.posX + (double) ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
			double d1 = this.parentEntity.posY + (double) ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
			double d2 = this.parentEntity.posZ + (double) ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
			this.parentEntity.getMoveHelper().setMoveTo(d0, d1, d2, 1.0D);

			if (this.parentEntity.posY > 128) {
				this.parentEntity.getMoveHelper().setMoveTo(d0, d1 - 1.0D, d2, 1.0D);
			}
		}
	}

	static class GhastMoveHelper extends EntityMoveHelper {
		private final EntityGhastBossD parentEntity;
		private int courseChangeCooldown;

		public GhastMoveHelper(EntityGhastBossD entityGhastBossD) {
			super(entityGhastBossD);
			this.parentEntity = entityGhastBossD;
		}

		public void onUpdateMoveHelper() {
			if (this.action == EntityMoveHelper.Action.MOVE_TO) {
				double d0 = this.posX - this.parentEntity.posX;
				double d1 = this.posY - this.parentEntity.posY;
				double d2 = this.posZ - this.parentEntity.posZ;
				double d3 = d0 * d0 + d1 * d1 + d2 * d2;

				if (this.courseChangeCooldown-- <= 0) {
					this.courseChangeCooldown += this.parentEntity.getRNG().nextInt(5) + 2;
					d3 = (double) MathHelper.sqrt(d3);

					if (this.isNotColliding(this.posX, this.posY, this.posZ, d3)) {
						this.parentEntity.motionX += d0 / d3 * 0.1D;
						this.parentEntity.motionY += d1 / d3 * 0.1D;
						this.parentEntity.motionZ += d2 / d3 * 0.1D;
					} else {
						this.action = EntityMoveHelper.Action.WAIT;
					}
				}
			}
		}

		/**
		 * Checks if entity bounding box is not colliding with terrain
		 */
		private boolean isNotColliding(double x, double y, double z, double p_179926_7_) {
			double d0 = (x - this.parentEntity.posX) / p_179926_7_;
			double d1 = (y - this.parentEntity.posY) / p_179926_7_;
			double d2 = (z - this.parentEntity.posZ) / p_179926_7_;
			AxisAlignedBB axisalignedbb = this.parentEntity.getEntityBoundingBox();

			for (int i = 1; (double) i < p_179926_7_; ++i) {
				axisalignedbb = axisalignedbb.offset(d0, d1, d2);

				if (!this.parentEntity.world.getCollisionBoxes(this.parentEntity, axisalignedbb).isEmpty()) {
					return false;
				}
			}

			return true;
		}
	}
}