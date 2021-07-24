package fr.kinjer.kinomod.entity;

import java.util.Calendar;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import fr.kinjer.kinomod.handler.ModSounds;
import fr.kinjer.kinomod.init.PotionInit;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIAttackRanged;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIBreakDoor;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIZombieAttack;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityCentaur extends EntityMob {

    private static final DataParameter<Boolean> SWINGING_ARMS = EntityDataManager.<Boolean>createKey(AbstractSkeleton.class, DataSerializers.BOOLEAN);
	private final BossInfoServer bossInfo = (BossInfoServer) (new BossInfoServer(this.getDisplayName(),
			BossInfo.Color.RED, BossInfo.Overlay.PROGRESS)).setDarkenSky(true);
    private final EntityAIAttackMelee aiAttackOnCollide = new EntityAIAttackMelee(this, 0.6D, false);
    private final float[] xRotationHeads = new float[2];
	private final float[] yRotationHeads = new float[2];
	private final int[] nextHeadUpdate = new int[2];
	private final int[] idleHeadUpdates = new int[2];
	private int attackTimer;
	private int blockBreakCounter;
	
	// Boss Health:
    public float damageTakenThisSec = 0;
    public float healthLastTick = -1;
    public long updateTick = 0;
    
    private static final double MAX_HEALTH = 1024.0D;
	private static final double MOVEMENT_SPEED = 0.6D;
	private static final double ATTACK_DAMAGE = 30.0D;
	private static final double ARMOR = 25.0D;
	
	private static final Predicate<Entity> NOT_UNDEAD = new Predicate<Entity>()
    {
        public boolean apply(@Nullable Entity p_apply_1_)
        {
            return p_apply_1_ instanceof EntityLivingBase && ((EntityLivingBase)p_apply_1_).getCreatureAttribute() != EnumCreatureAttribute.UNDEAD && ((EntityLivingBase)p_apply_1_).attackable();
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
		this.tasks.addTask(1, new EntityAIAttackMelee(this, 0.6D, true));
		this.tasks.addTask(2, new EntityAIMoveTowardsTarget(this, 0.6D, 32.0F));
		this.tasks.addTask(3, new EntityAIMoveTowardsRestriction(this, 0.6D));
		this.tasks.addTask(5, new EntityAISwimming(this));
		this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 1.0D));
		this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 80.0F));
		this.tasks.addTask(8, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityLiving.class, 0, false, false, NOT_UNDEAD));
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

	protected void entityInit() {
		super.entityInit();
		this.getDataManager().register(SWINGING_ARMS, Boolean.valueOf(false));
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
	
	private double getHeadX(int p_82214_1_) {
		if (p_82214_1_ <= 0) {
			return this.posX;
		} else {
			float f = (this.renderYawOffset + (float) (180 * (p_82214_1_ - 1))) * 0.017453292F;
			float f1 = MathHelper.cos(f);
			return this.posX + (double) f1 * 1.3D;
		}
	}

	private double getHeadY(int p_82208_1_) {
		return p_82208_1_ <= 0 ? this.posY + 3.0D : this.posY + 2.2D;
	}

	private double getHeadZ(int p_82213_1_) {
		if (p_82213_1_ <= 0) {
			return this.posZ;
		} else {
			float f = (this.renderYawOffset + (float) (180 * (p_82213_1_ - 1))) * 0.017453292F;
			float f1 = MathHelper.sin(f);
			return this.posZ + (double) f1 * 1.3D;
		}
	}

	private float rotlerp(float p_82204_1_, float p_82204_2_, float p_82204_3_) {
		float f = MathHelper.wrapDegrees(p_82204_2_ - p_82204_1_);

		if (f > p_82204_3_) {
			f = p_82204_3_;
		}

		if (f < -p_82204_3_) {
			f = -p_82204_3_;
		}

		return p_82204_1_ + f;
	}

	public void setArmsRaised(boolean armsRaised) {
		this.getDataManager().set(SWINGING_ARMS, Boolean.valueOf(armsRaised));
	}

	@SideOnly(Side.CLIENT)
	public boolean isArmsRaised() {
		return ((Boolean) this.getDataManager().get(SWINGING_ARMS)).booleanValue();
	}
	
	/**
     * sets this entity's combat AI.
     */
    public void setCombatTask()
    {
        if (this.world != null && !this.world.isRemote)
        {
            this.tasks.removeTask(this.aiAttackOnCollide);
            ItemStack itemstack = this.getHeldItemMainhand();
            this.tasks.addTask(4, this.aiAttackOnCollide);
            
        }
    }

	/**
	 * Called frequently so the entity can update its state every tick as required.
	 * For example, zombies and skeletons use this to react to sunlight and start to
	 * burn.
	 */
	public void onLivingUpdate() {
		
		if (this.healthLastTick < 0)
            this.healthLastTick = this.getHealth();
        if (this.healthLastTick - this.getHealth() > 50)
            this.setHealth(this.healthLastTick);
        this.healthLastTick = this.getHealth();
        if (!this.getEntityWorld().isRemote && this.updateTick % 20 == 0) {
            this.damageTakenThisSec = 0;
        }
		
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

	/**
	 * Called when the entity is attacked.
	 */
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (this.isEntityInvulnerable(source)) {
			return false;
		} else {
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

				return super.attackEntityFrom(source, amount);
			}
		}
	}

	public boolean attackEntityAsMob(Entity entityIn) {
		this.attackTimer = 10;
		this.world.setEntityState(this, (byte) 4);
		boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), 8.0f);

		if (flag) {
			entityIn.motionY += 0.4000000059604645D;
			this.applyEnchantments(this, entityIn);
			((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(MobEffects.WITHER, 200));
		}

		this.playSound(SoundEvents.ENTITY_IRONGOLEM_ATTACK, 1.0F, 1.0F);
		return flag;
	}

	/**
	 * Sets the custom name tag for this entity
	 */
	public void setCustomNameTag(String name) {
		super.setCustomNameTag(name);
		this.bossInfo.setName(this.getDisplayName());
	}

	protected SoundEvent getAmbientSound() {
		return SoundEvents.ENTITY_ZOMBIE_AMBIENT;
	}

	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEvents.ENTITY_ZOMBIE_HURT;
	}

	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_ZOMBIE_DEATH;
	}

	protected SoundEvent getStepSound() {
		return SoundEvents.ENTITY_ZOMBIE_STEP;
	}

	protected void playStepSound(BlockPos pos, Block blockIn) {
		this.playSound(this.getStepSound(), 0.15F, 1.0F);
	}

	@Nullable
	protected ResourceLocation getLootTable() {
		return LootTableList.ENTITIES_ZOMBIE;
	}

	public static boolean canDestroyBlock(Block blockIn) {
		return blockIn != Blocks.BEDROCK && blockIn != Blocks.END_PORTAL && blockIn != Blocks.END_PORTAL_FRAME
				&& blockIn != Blocks.COMMAND_BLOCK && blockIn != Blocks.REPEATING_COMMAND_BLOCK
				&& blockIn != Blocks.CHAIN_COMMAND_BLOCK && blockIn != Blocks.BARRIER
				&& blockIn != Blocks.STRUCTURE_BLOCK && blockIn != Blocks.STRUCTURE_VOID
				&& blockIn != Blocks.PISTON_EXTENSION && blockIn != Blocks.END_GATEWAY;
	}

	/**
	 * Returns whether the wither is armored with its boss armor or not by checking
	 * whether its health is below half of its maximum.
	 */
	public boolean isArmored() {
		return this.getHealth() <= this.getMaxHealth();
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

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);

		if (this.hasCustomName()) {
			this.bossInfo.setName(this.getDisplayName());
		}
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

	/**
	 * Returns false if this Entity is a boss, true otherwise.
	 */
	public boolean isNonBoss() {
		return false;
	}

	/**
	 * Called only once on an entity when first time spawned, via egg, mob spawner,
	 * natural spawning etc, but not called when entity is reloaded from nbt. Mainly
	 * used for initializing attributes and inventory
	 */
	@Nullable
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
		IEntityLivingData ientitylivingdata = super.onInitialSpawn(difficulty, livingdata);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8.0D);
		return ientitylivingdata;
	}

	/**
	 * Called when the mob's health reaches 0.
	 */
	public void onDeath(DamageSource cause) {
		super.onDeath(cause);
	}
}