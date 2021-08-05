package fr.kinjer.kinomod.entity;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.common.CommonProxy;
import fr.kinjer.kinomod.handler.HandlerLootTable;
import fr.kinjer.kinomod.handler.HandlerSounds;
import fr.kinjer.kinomod.init.InitItems;
import fr.kinjer.kinomod.init.InitPotion;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityCentaur extends EntityMob {

	private static final DataParameter<Boolean> SWINGING_ARMS = EntityDataManager
			.<Boolean>createKey(AbstractSkeleton.class, DataSerializers.BOOLEAN);
	private final BossInfoServer bossInfo = (BossInfoServer) (new BossInfoServer(this.getDisplayName(),
			BossInfo.Color.RED, BossInfo.Overlay.PROGRESS)).setDarkenSky(true);
	private final EntityAIAttackMelee aiAttackOnCollide = new EntityAIAttackMelee(this, MOVEMENT_SPEED, false);
	private final float[] xRotationHeads = new float[2];
	private final float[] yRotationHeads = new float[2];
	private final int[] nextHeadUpdate = new int[2];
	private final int[] idleHeadUpdates = new int[2];
	private int attackTimer;
	private int blockBreakCounter;

	private static final double MAX_HEALTH = 500.0D;
	private static final double MOVEMENT_SPEED = 0.6D;
	private static final double ATTACK_DAMAGE = 8.0D;
	private static final double ARMOR = 20.0D;
	private static final double FOLLOW_RANGE = 50.0D;

	private static final Predicate<Entity> NOT_UNDEAD = new Predicate<Entity>() {
		public boolean apply(@Nullable Entity p_apply_1_) {
			return p_apply_1_ instanceof EntityLivingBase
					&& ((EntityLivingBase) p_apply_1_).getCreatureAttribute() != EnumCreatureAttribute.UNDEAD
					&& ((EntityLivingBase) p_apply_1_).attackable();
		}
	};

	public EntityCentaur(World worldIn) {
		super(worldIn);
		this.setHealth(getHealth());
		this.setSize(1.5F, 3.6F);
		this.isImmuneToExplosions();
		this.isImmuneToFire = true;
		((PathNavigateGround) this.getNavigator()).setCanSwim(true);
		this.experienceValue = 500;
	}

	protected void initEntityAI() {
		this.tasks.addTask(1, new EntityAIAttackMelee(this, MOVEMENT_SPEED, true));
		this.tasks.addTask(2, new EntityAIMoveTowardsTarget(this, MOVEMENT_SPEED, (float) FOLLOW_RANGE));
		this.tasks.addTask(3, new EntityAIMoveTowardsRestriction(this, MOVEMENT_SPEED));
		this.tasks.addTask(5, new EntityAISwimming(this));
		this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 1.0D));
		this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, (float) FOLLOW_RANGE));
		this.tasks.addTask(8, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
		this.targetTasks.addTask(2,
				new EntityAINearestAttackableTarget(this, EntityLiving.class, 0, false, false, NOT_UNDEAD));
	}

	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(MAX_HEALTH);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(MOVEMENT_SPEED);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(ATTACK_DAMAGE);
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(ARMOR);
	}

	public static void registerFixesCentaur(DataFixer fixer) {
		EntityLiving.registerFixesMob(fixer, EntityCentaur.class);
	}

	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
	}

	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);

		if (this.hasCustomName()) {
			this.bossInfo.setName(this.getDisplayName());
		}
	}

	public void setCustomNameTag(String name) {
		super.setCustomNameTag(name);
		this.bossInfo.setName(this.getDisplayName());
	}

	@Override
	protected ResourceLocation getLootTable() {
		return HandlerLootTable.CENTAUR;
	}

	public boolean isImmuneToExplosions() {
		return true;
	}

	protected void updateAITasks() {
		if (this.ticksExisted % 10 == 0) {
			this.heal(10.0F);
		} else {
			super.updateAITasks();

			for (int i = 1; i < 3; ++i) {
				if (this.ticksExisted >= this.nextHeadUpdate[i - 1]) {
					this.nextHeadUpdate[i - 1] = this.ticksExisted + 10 + this.rand.nextInt(10);
				}
			}

			if (this.ticksExisted % 20 == 0) {
				this.heal(1.0F);
			}

			this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
		}
	}

	public void setArmsRaised(boolean armsRaised) {
		this.getDataManager().set(SWINGING_ARMS, Boolean.valueOf(armsRaised));
	}

	@SideOnly(Side.CLIENT)
	public boolean isArmsRaised() {
		return ((Boolean) this.getDataManager().get(SWINGING_ARMS)).booleanValue();
	}
	
	public void setCombatTask() {
		if (this.world != null && !this.world.isRemote) {
			this.tasks.removeTask(this.aiAttackOnCollide);
			ItemStack itemstack = this.getHeldItemMainhand();
			this.tasks.addTask(4, this.aiAttackOnCollide);

		}
	}

	public void onLivingUpdate() {

		super.onLivingUpdate();

		if (this.attackTimer > 0) {
			--this.attackTimer;
		}

		if (this.motionX * this.motionX + this.motionZ * this.motionZ > 2.500000277905201E-7D
				&& this.rand.nextInt(5) == 0) {
			int i = MathHelper.floor(this.posX);
			int j = MathHelper.floor(this.posY - 0.20000000298023224D);
			int k = MathHelper.floor(this.posZ);
			IBlockState iblockstate = this.world.getBlockState(new BlockPos(i, j, k));

			if (iblockstate.getMaterial() != Material.AIR) {
				this.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK,
						this.posX + ((double) this.rand.nextFloat() - 0.5D) * (double) this.width,
						this.getEntityBoundingBox().minY + 0.1D,
						this.posZ + ((double) this.rand.nextFloat() - 0.5D) * (double) this.width,
						4.0D * ((double) this.rand.nextFloat() - 0.5D), 0.5D,
						((double) this.rand.nextFloat() - 0.5D) * 4.0D, Block.getStateId(iblockstate));
			}
		}

		if (this.blockBreakCounter > 0) {
			--this.blockBreakCounter;

			if (this.blockBreakCounter == 0
					&& net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this)) {
				int i1 = MathHelper.floor(this.posY);
				int l1 = MathHelper.floor(this.posX);
				int i2 = MathHelper.floor(this.posZ);
				boolean flag = false;

				for (int k2 = -1; k2 <= 1; ++k2) {
					for (int l2 = -1; l2 <= 1; ++l2) {
						for (int j = 0; j <= 3; ++j) {
							int i3 = l1 + k2;
							int k = i1 + j;
							int l = i2 + l2;
							BlockPos blockpos = new BlockPos(i3, k, l);
							IBlockState iblockstate = this.world.getBlockState(blockpos);
							Block block = iblockstate.getBlock();

							if (!block.isAir(iblockstate, this.world, blockpos)
									&& block.canEntityDestroy(iblockstate, world, blockpos, this)
									&& net.minecraftforge.event.ForgeEventFactory.onEntityDestroyBlock(this, blockpos,
											iblockstate)) {
								flag = this.world.destroyBlock(blockpos, true) || flag;
							}
						}
					}
				}

				if (flag) {
					this.world.playEvent((EntityPlayer) null, 1022, new BlockPos(this), 0);
				}
			}
		}

		if (this.ticksExisted % 20 == 0) {
			this.heal(1.0F);
		}

		this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
	}

	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (this.isEntityInvulnerable(source)) {
			return false;
		} else if (source != DamageSource.DROWN && !(source.getTrueSource() instanceof EntityCentaur)) {
			if (this.isArmored()) {
				Entity entity = source.getImmediateSource();

				if (entity instanceof EntityArrow) {
					return false;
				}
			}

			Entity entity1 = source.getTrueSource();

			if (entity1 != null && !(entity1 instanceof EntityPlayer) && entity1 instanceof EntityLivingBase
					&& ((EntityLivingBase) entity1).getCreatureAttribute() == this.getCreatureAttribute()) {
				return false;
			} else {
				if (this.blockBreakCounter <= 0) {
					this.blockBreakCounter = 20;
				}

				for (int i = 0; i < this.idleHeadUpdates.length; ++i) {
					this.idleHeadUpdates[i] += 3;
				}

				return super.attackEntityFrom(source, amount / 2);
			}
		} else {
			return false;
		}
	}

	public boolean attackEntityAsMob(Entity entityIn) {
		this.attackTimer = 10;
		this.world.setEntityState(this, (byte) 4);
		boolean flag = entityIn.attackEntityFrom(CommonProxy.bismuthDamage, (float) ATTACK_DAMAGE);

		if (flag) {
			entityIn.motionY += 0.4000000059604645D;
			this.applyEnchantments(this, entityIn);
			((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(InitPotion.BLEEDING_EFFECT, 70));
		}

		return flag;
	}

	protected SoundEvent getAmbientSound() {
		return HandlerSounds.ENTITY_CENTAUR_AMBIENT;
	}

	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return HandlerSounds.ENTITY_CENTAUR_HURT;
	}

	protected SoundEvent getDeathSound() {
		return HandlerSounds.ENTITY_CENTAUR_DEATH;
	}

	public static boolean canDestroyBlock(Block blockIn) {
		return blockIn != Blocks.BEDROCK && blockIn != Blocks.END_PORTAL && blockIn != Blocks.END_PORTAL_FRAME
				&& blockIn != Blocks.COMMAND_BLOCK && blockIn != Blocks.REPEATING_COMMAND_BLOCK
				&& blockIn != Blocks.CHAIN_COMMAND_BLOCK && blockIn != Blocks.BARRIER
				&& blockIn != Blocks.STRUCTURE_BLOCK && blockIn != Blocks.STRUCTURE_VOID
				&& blockIn != Blocks.PISTON_EXTENSION && blockIn != Blocks.END_GATEWAY;
	}

	public boolean isArmored() {
		return this.getHealth() <= this.getMaxHealth();
	}

	public void addTrackingPlayer(EntityPlayerMP player) {
		super.addTrackingPlayer(player);
		this.bossInfo.addPlayer(player);
	}

	public void removeTrackingPlayer(EntityPlayerMP player) {
		super.removeTrackingPlayer(player);
		this.bossInfo.removePlayer(player);
	}

	@Override
	public float getEyeHeight() {
		return 2.4F;
	}

	@SideOnly(Side.CLIENT)
	public float getHeadYRotation(int p_82207_1_) {
		return this.yRotationHeads[p_82207_1_];
	}

	@SideOnly(Side.CLIENT)
	public float getHeadXRotation(int p_82210_1_) {
		return this.xRotationHeads[p_82210_1_];
	}

	public boolean isNonBoss() {
		return false;
	}

	@Nullable
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
		IEntityLivingData ientitylivingdata = super.onInitialSpawn(difficulty, livingdata);
		return ientitylivingdata;
	}

	public void onDeath(DamageSource cause) {
		super.onDeath(cause);
	}
}
