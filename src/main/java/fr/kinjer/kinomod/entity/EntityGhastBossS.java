package fr.kinjer.kinomod.entity;

import java.util.Collection;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Nullable;

import fr.kinjer.kinomod.client.gui.GuiPlayerScore;
import fr.kinjer.kinomod.entity.projectile.EntityGhastBossSFireball;
import fr.kinjer.kinomod.handler.HandlerLootTable;
import fr.kinjer.kinomod.handler.HandlerSounds;
import fr.kinjer.kinomod.init.InitItems;
import fr.kinjer.kinomod.utils.UtilsWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.end.DragonFightManager;
import net.minecraft.world.gen.feature.WorldGenEndPodium;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityGhastBossS extends EntityFlying implements IMob {

	private final BossInfoServer bossInfo = (BossInfoServer) (new BossInfoServer(this.getDisplayName(),
			BossInfo.Color.RED, BossInfo.Overlay.PROGRESS)).setDarkenSky(true);

	private UUID ghastbossdUniqueId;
	private boolean ghastbossdKilled;
	private boolean previouslyKilled;
	public int deathTicks;
	public static int playerScore;
	public static int playerMaxScore = 15;

	private static final double MAX_HEALTH = 750.0D;
	private static final double MOVEMENT_SPEED = 0.6D;
	private static final double FOLLOW_RANGE = 100.0D;
	private static final double XP_VALUE = 700.0D;

	private static final DataParameter<Boolean> ATTACKING = EntityDataManager.<Boolean>createKey(EntityGhastBossS.class,
			DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> PHASE = EntityDataManager.<Integer>createKey(EntityGhastBossS.class,
			DataSerializers.VARINT);

	private int explosionStrength = 6;

	public EntityGhastBossS(World worldIn) {
		super(worldIn);
		this.setSize(10.0f, 10.0f);
		this.isImmuneToFire = true;
		this.experienceValue = (int) XP_VALUE;
		this.moveHelper = new EntityGhastBossS.GhastMoveHelper(this);
	}

	@Override
	protected void initEntityAI() {
		this.tasks.addTask(5, new EntityGhastBossS.AIRandomFly(this));
		this.tasks.addTask(7, new EntityGhastBossS.AILookAround(this));
		this.tasks.addTask(7, new EntityGhastBossS.AIFireballAttack(this));
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

		if (this.getHealth() <= 250) {
			this.setPhase(2);
		} else if (this.getHealth() <= 500) {
			this.setPhase(1);
		} else if (this.getHealth() > 500) {
			this.setPhase(0);
		}
	}

	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (this.isEntityInvulnerable(source)) {
			return true;
		}

		else if (source.getImmediateSource() instanceof EntityGhastBossSFireball
				&& source.getTrueSource() instanceof EntityPlayer) {
			super.attackEntityFrom(source, 50.0F);
			return true;
		} else if (source.getImmediateSource() instanceof EntityPlayer
				&& source.getTrueSource() instanceof EntityPlayer) {
			super.attackEntityFrom(source, 0.0F);
			return false;
		}
		if (this.isArmored()) {
			Entity entity = source.getImmediateSource();

			if (entity instanceof EntityArrow) {
				return false;
			}
		}
		return super.attackEntityFrom(source, 50.0F);
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
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(MAX_HEALTH);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(FOLLOW_RANGE);
	}

	public SoundCategory getSoundCategory() {
		return SoundCategory.HOSTILE;
	}

	protected SoundEvent getAmbientSound() {
		return HandlerSounds.ENTITY_GHAST_BOSS_AMBIENT;
	}

//	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
//		return HandlerSounds.ENTITY_GHAST_BOSS_HURT;
//	}

	protected SoundEvent getDeathSound() {
		return HandlerSounds.ENTITY_GHAST_BOSS_DEATH;
	}
	
	protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier)
    {
        EntityItem entityitem = this.dropItem(InitItems.ghast_boss_tentacle_s, 1);

        if (entityitem != null)
        {
            entityitem.setNoDespawn();
        }
        
        EntityItem entityitem2 = this.dropItem(InitItems.seminium, 3);
        
        if (entityitem2 != null)
        {
        	entityitem2.setNoDespawn();
        }
    }

	protected void collideWithEntity(Entity entityIn) {
	}

	public void addTrackingPlayer(EntityPlayerMP player) {
		super.addTrackingPlayer(player);
		this.bossInfo.addPlayer(player);
	}

	public void removeTrackingPlayer(EntityPlayerMP player) {
		super.removeTrackingPlayer(player);
		this.bossInfo.removePlayer(player);
		this.playerScore -= this.playerScore;
		this.heal(750.0F);
	}

	@Override
	protected void updateAITasks() {
		super.updateAITasks();
		this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
	}

	protected float getSoundVolume() {
		return 30.0F;
	}

	public static void registerFixesGhast(DataFixer fixer) {
		EntityLiving.registerFixesMob(fixer, EntityGhast.class);
	}

	@Override
	public void knockBack(Entity entityIn, float strength, double xRatio, double zRatio) {
	}

	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setInteger("ExplosionPower", this.explosionStrength);
	}

	public boolean isNonBoss() {
		return false;
	}

	protected void despawnEntity() {
		this.idleTime = 0;
	}

	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);

		if (compound.hasKey("ExplosionPower", 99)) {
			this.explosionStrength = compound.getInteger("ExplosionPower");
		}
	}

	public void processGhastDeath(EntityGhastBossS ghastbosss) {
		if (ghastbosss.getUniqueID().equals(this.ghastbossdUniqueId)) {
			this.bossInfo.setPercent(0.0F);
			this.bossInfo.setVisible(false);

			this.previouslyKilled = true;
			this.ghastbossdKilled = true;
		}
	}

	public float getEyeHeight() {
		return 2.6F;
	}

	protected void onDeathUpdate() {
		this.bossInfo.setPercent(0.0F);
		this.bossInfo.setVisible(false);
		++this.deathTicks;

		if (this.deathTicks == 20) {
			GuiPlayerScore.renderPlayerScoreS = true;
		}

		if (this.deathTicks >= 70 && this.deathTicks <= 200)
        {
            float f = (this.rand.nextFloat() - 0.5F) * 8.0F;
            float f1 = (this.rand.nextFloat() - 0.5F) * 4.0F;
            float f2 = (this.rand.nextFloat() - 0.5F) * 8.0F;
            this.world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.posX + (double)f, this.posY + 2.0D + (double)f1, this.posZ + (double)f2, 0.0D, 0.0D, 0.0D);
        }

		boolean flag = this.world.getGameRules().getBoolean("doMobLoot");
		int i = 500;

		if (!this.world.isRemote) {
			if (this.deathTicks > 150 && this.deathTicks % 5 == 0 && flag) {
				this.dropExperience(MathHelper.floor((float) i * 0.08F));
			}

			if (this.deathTicks == 1) {
				this.world.playSound(this.posX, this.posY, this.posZ, HandlerSounds.ENTITY_GHAST_BOSS_DEATH, SoundCategory.HOSTILE, 50.0F, 0.2F, false);
			}
		}

		this.rotationYaw += 5.0F;

		if (this.deathTicks == 200 && !this.world.isRemote) {
			if (flag) {
				this.dropExperience(MathHelper.floor((float) i * 0.2F));
			}
			GuiPlayerScore.renderPlayerScoreS = false;
			this.playerScore -= this.playerScore;
			this.dropFewItems(false, 1);
			this.setDead();
		}
	}

	private void dropExperience(int xp) {
		xp = (int) XP_VALUE;
		while (xp > 0) {
			int i = EntityXPOrb.getXPSplit(xp);
			xp -= i;
			this.world.spawnEntity(new EntityXPOrb(this.world, this.posX, this.posY, this.posZ, i));
		}
	}

	static class AIFireballAttack extends EntityAIBase {
		private final EntityGhastBossS parentEntity;
		public int attackTimer;
		public int shootTimer;

		public AIFireballAttack(EntityGhastBossS entityGhastBossS) {
			this.parentEntity = entityGhastBossS;
		}

		public boolean shouldExecute() {
			return this.parentEntity.getAttackTarget() != null;
		}

		public void startExecuting() {
			this.attackTimer = 0;
			this.shootTimer = 0;
		}

		public void resetTask() {
			this.parentEntity.setAttacking(false);
			this.parentEntity.setPhase(0);
		}

		public void updateTask() {
			EntityLivingBase entitylivingbase = this.parentEntity.getAttackTarget();

			if (entitylivingbase.getDistanceSq(this.parentEntity) < 4096.0D) {
				World world = this.parentEntity.world;
				++this.attackTimer;

				if (this.attackTimer == 5) {
					parentEntity.playSound(HandlerSounds.ENTITY_GHAST_BOSS_HURT, 50.0F, 1.0F);
				}

				if (this.attackTimer == 10) {
					double d1 = 10.0D;
					Vec3d vec3d = this.parentEntity.getLook(1.0F);
					double d2 = entitylivingbase.posX - (this.parentEntity.posX + vec3d.x * 4.0D);
					double d3 = entitylivingbase.getEntityBoundingBox().minY + (double) (entitylivingbase.height / 2.0F)
							- (0.5D + this.parentEntity.posY + (double) (this.parentEntity.height / 2.0F));
					double d4 = entitylivingbase.posZ - (this.parentEntity.posZ + vec3d.z * 4.0D);
					world.playEvent((EntityPlayer) null, 1016, new BlockPos(this.parentEntity), 0);
					EntityGhastBossSFireball entitylargefireball = new EntityGhastBossSFireball(world, this.parentEntity, d2, d3,
							d4);
					entitylargefireball.explosionPower = this.parentEntity.getFireballStrength();
					entitylargefireball.posX = this.parentEntity.posX + vec3d.x * 4.0D;
					entitylargefireball.posY = this.parentEntity.posY + (double) (this.parentEntity.height / 2.0F) + 0.5D;
					entitylargefireball.posZ = this.parentEntity.posZ + vec3d.z * 4.0D;
					world.spawnEntity(entitylargefireball);
					EntityGhastBossS.playerScore += 1;
					this.attackTimer = -20;
				}
			} else if (this.attackTimer > 0) {
				--this.attackTimer;
			}
			this.parentEntity.setAttacking(this.attackTimer > 5);
		}
	}

	static class AILookAround extends EntityAIBase {
		private final EntityGhastBossS parentEntity;

		public AILookAround(EntityGhastBossS entityGhastBossS) {
			this.parentEntity = entityGhastBossS;
			this.setMutexBits(2);
		}

		public boolean shouldExecute() {
			return true;
		}

		public void updateTask() {
			if (this.parentEntity.getAttackTarget() == null) {
				this.parentEntity.rotationYaw = -((float) MathHelper.atan2(this.parentEntity.motionX,
						this.parentEntity.motionZ)) * (180F / (float) Math.PI);
				this.parentEntity.renderYawOffset = this.parentEntity.rotationYaw;
			} else {
				EntityLivingBase entitylivingbase = this.parentEntity.getAttackTarget();

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
		private final EntityGhastBossS parentEntity;

		public AIRandomFly(EntityGhastBossS entityGhastBossS) {
			this.parentEntity = entityGhastBossS;
			this.setMutexBits(1);
		}

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

		public boolean shouldContinueExecuting() {
			return false;
		}

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
		private final EntityGhastBossS parentEntity;
		private int courseChangeCooldown;

		public GhastMoveHelper(EntityGhastBossS entityGhastBossS) {
			super(entityGhastBossS);
			this.parentEntity = entityGhastBossS;
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
