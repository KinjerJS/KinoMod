package fr.kinjer.kinomod.entity;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityCentaur extends EntityMob {
	
    private static final DataParameter<Integer> INVULNERABILITY_TIME = EntityDataManager.<Integer>createKey(EntityWither.class, DataSerializers.VARINT);
    private final float[] xRotationHeads = new float[2];
    private final float[] yRotationHeads = new float[2];
    private final float[] xRotOHeads = new float[2];
    private final float[] yRotOHeads = new float[2];
    private final int[] nextHeadUpdate = new int[2];
    private final int[] idleHeadUpdates = new int[2];
    private final BossInfoServer bossInfo = (BossInfoServer)(new BossInfoServer(this.getDisplayName(), BossInfo.Color.RED, BossInfo.Overlay.PROGRESS)).setDarkenSky(true);
    /** Selector used to determine the entities a wither boss should attack. */
    private static final Predicate<Entity> NOT_UNDEAD = new Predicate<Entity>()
    {
        public boolean apply(@Nullable Entity p_apply_1_)
        {
            return p_apply_1_ instanceof EntityLivingBase && ((EntityLivingBase)p_apply_1_).getCreatureAttribute() != EnumCreatureAttribute.UNDEAD && ((EntityLivingBase)p_apply_1_).attackable();
        }
    };

	public EntityCentaur(World worldIn) {
		super(worldIn);
        this.setHealth(this.getMaxHealth());
        this.setSize(1F, 2.4F);
        this.isImmuneToFire = true;
        ((PathNavigateGround)this.getNavigator()).setCanSwim(true);
        this.experienceValue = 50;		
	}
	
	@Override
	public void onDeath(DamageSource cause)
    {
		
    }
	
	@Override
	public float getEyeHeight() {
		return 2.4F;
	}
	
	////////////////////////////////
	

    protected void initEntityAI()
    {
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(3, new EntityAIAttackMelee(this, 1.0F, false));
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
//        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
//        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityLiving.class, 0, false, false, NOT_UNDEAD));
    }

    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(INVULNERABILITY_TIME, Integer.valueOf(0));
    }

    public static void registerFixesWither(DataFixer fixer)
    {
        EntityLiving.registerFixesMob(fixer, EntityCentaur.class);
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("Invul", this.getInvulTime());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.setInvulTime(compound.getInteger("Invul"));

        if (this.hasCustomName())
        {
            this.bossInfo.setName(this.getDisplayName());
        }
    }

    /**
     * Sets the custom name tag for this entity
     */
    public void setCustomNameTag(String name)
    {
        super.setCustomNameTag(name);
        this.bossInfo.setName(this.getDisplayName());
    }

    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.ENTITY_WITHER_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.ENTITY_WITHER_HURT;
    }

    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_WITHER_DEATH;
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        //this.motionY *= 0.6000000238418579D;

//        if (!this.world.isRemote && this.getWatchedTargetId(0) > 0)
//        {
//            Entity entity = this.world.getEntityByID(this.getWatchedTargetId(0));
//
//            if (entity != null)
//            {
//                if (this.posY < entity.posY || !this.isArmored() && this.posY < entity.posY + 5.0D)
//                {
//                    if (this.motionY < 0.0D)
//                    {
//                        this.motionY = 0.0D;
//                    }
//
//                   // this.motionY += (0.5D - this.motionY) * 0.6000000238418579D;
//                }
//
//                double d0 = entity.posX - this.posX;
//                double d1 = entity.posZ - this.posZ;
//                double d3 = d0 * d0 + d1 * d1;
//
//                if (d3 > 9.0D)
//                {
//                    double d5 = (double)MathHelper.sqrt(d3);
//                    this.motionX += (d0 / d5 * 0.5D - this.motionX) * 0.6000000238418579D;
//                    this.motionZ += (d1 / d5 * 0.5D - this.motionZ) * 0.6000000238418579D;
//                }
//            }
//        }

        if (this.motionX * this.motionX + this.motionZ * this.motionZ > 0.05000000074505806D)
        {
            this.rotationYaw = (float)MathHelper.atan2(this.motionZ, this.motionX) * (180F / (float)Math.PI) - 90.0F;
        }

        super.onLivingUpdate();

        for (int i = 0; i < 2; ++i)
        {
            this.yRotOHeads[i] = this.yRotationHeads[i];
            this.xRotOHeads[i] = this.xRotationHeads[i];
        }

        for (int j = 0; j < 2; ++j)
        {
            //int k = this.getWatchedTargetId(j + 1);
            //Entity entity1 = null;

//            if (k > 0)
//            {
//                entity1 = this.world.getEntityByID(k);
//            }

//            if (entity1 != null)
//            {
//                double d11 = this.getHeadX(j + 1);
//                double d12 = this.getHeadY(j + 1);
//                double d13 = this.getHeadZ(j + 1);
//                double d6 = entity1.posX - d11;
//                double d7 = entity1.posY + (double)entity1.getEyeHeight() - d12;
//                double d8 = entity1.posZ - d13;
//                double d9 = (double)MathHelper.sqrt(d6 * d6 + d8 * d8);
//                float f = (float)(MathHelper.atan2(d8, d6) * (180D / Math.PI)) - 90.0F;
//                float f1 = (float)(-(MathHelper.atan2(d7, d9) * (180D / Math.PI)));
//                this.xRotationHeads[j] = this.rotlerp(this.xRotationHeads[j], f1, 40.0F);
//                this.yRotationHeads[j] = this.rotlerp(this.yRotationHeads[j], f, 10.0F);
//            }
//            else
//            {
//                this.yRotationHeads[j] = this.rotlerp(this.yRotationHeads[j], this.renderYawOffset, 10.0F);
//            }
            this.yRotationHeads[j] = this.rotlerp(this.yRotationHeads[j], this.renderYawOffset, 10.0F);
        }
    }

    protected void updateAITasks()
    {
        if (this.getInvulTime() > 0)
        {
            int j1 = this.getInvulTime() - 1;

            this.setInvulTime(j1);

            if (this.ticksExisted % 10 == 0)
            {
                this.heal(10.0F);
            }
        }
        else
        {
            super.updateAITasks();

            for (int i = 1; i < 3; ++i)
            {
                if (this.ticksExisted >= this.nextHeadUpdate[i - 1])
                {
                    this.nextHeadUpdate[i - 1] = this.ticksExisted + 10 + this.rand.nextInt(10);

//                    if (this.world.getDifficulty() == EnumDifficulty.NORMAL || this.world.getDifficulty() == EnumDifficulty.HARD)
//                    {
//                        int j3 = i - 1;
//                        int k3 = this.idleHeadUpdates[i - 1];
//                        this.idleHeadUpdates[j3] = this.idleHeadUpdates[i - 1] + 1;
//
//                        if (k3 > 15)
//                        {
//                            float f = 10.0F;
//                            float f1 = 5.0F;
//                            double d0 = MathHelper.nextDouble(this.rand, this.posX - 10.0D, this.posX + 10.0D);
//                            double d1 = MathHelper.nextDouble(this.rand, this.posY - 5.0D, this.posY + 5.0D);
//                            double d2 = MathHelper.nextDouble(this.rand, this.posZ - 10.0D, this.posZ + 10.0D);
//                            this.launchWitherSkullToCoords(i + 1, d0, d1, d2, true);
//                            this.idleHeadUpdates[i - 1] = 0;
//                        }
//                    }

//                    int k1 = this.getWatchedTargetId(i);
//
//                    if (k1 > 0)
//                    {
//                        Entity entity = this.world.getEntityByID(k1);
//
//                        if (entity != null && entity.isEntityAlive() && this.getDistanceSq(entity) <= 900.0D && this.canEntityBeSeen(entity))
//                        {
//                            if (entity instanceof EntityPlayer && ((EntityPlayer)entity).capabilities.disableDamage)
//                            {
//                                this.updateWatchedTargetId(i, 0);
//                            }
//                            else
//                            {
//                                this.nextHeadUpdate[i - 1] = this.ticksExisted + 40 + this.rand.nextInt(20);
//                                this.idleHeadUpdates[i - 1] = 0;
//                            }
//                        }
//                        else
//                        {
//                            this.updateWatchedTargetId(i, 0);
//                        }
//                    }
//                    else
//                    {
//                        List<EntityLivingBase> list = this.world.<EntityLivingBase>getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(20.0D, 8.0D, 20.0D), Predicates.and(NOT_UNDEAD, EntitySelectors.NOT_SPECTATING));
//
//                        for (int j2 = 0; j2 < 10 && !list.isEmpty(); ++j2)
//                        {
//                            EntityLivingBase entitylivingbase = list.get(this.rand.nextInt(list.size()));
//
//
//                            list.remove(entitylivingbase);
//                        }
//                    }
                }
            }

            if (this.ticksExisted % 20 == 0)
            {
                this.heal(1.0F);
            }

            this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
        }
    }

    /**
     * Initializes this Wither's explosion sequence and makes it invulnerable. Called immediately after spawning.
     */
    public void ignite()
    {
        this.setInvulTime(220);
        this.setHealth(this.getMaxHealth() / 10.0F);
    }

    /**
     * Sets the Entity inside a web block.
     */
    public void setInWeb()
    {
    }

    /**
     * Add the given player to the list of players tracking this entity. For instance, a player may track a boss in
     * order to view its associated boss bar.
     */
    public void addTrackingPlayer(EntityPlayerMP player)
    {
        super.addTrackingPlayer(player);
        this.bossInfo.addPlayer(player);
    }

    /**
     * Removes the given player from the list of players tracking this entity. See {@link Entity#addTrackingPlayer} for
     * more information on tracking.
     */
    public void removeTrackingPlayer(EntityPlayerMP player)
    {
        super.removeTrackingPlayer(player);
        this.bossInfo.removePlayer(player);
    }

    private double getHeadX(int p_82214_1_)
    {
        if (p_82214_1_ <= 0)
        {
            return this.posX;
        }
        else
        {
            float f = (this.renderYawOffset + (float)(180 * (p_82214_1_ - 1))) * 0.017453292F;
            float f1 = MathHelper.cos(f);
            return this.posX + (double)f1 * 1.3D;
        }
    }

    private double getHeadY(int p_82208_1_)
    {
        return p_82208_1_ <= 0 ? this.posY + 3.0D : this.posY + 2.2D;
    }

    private double getHeadZ(int p_82213_1_)
    {
        if (p_82213_1_ <= 0)
        {
            return this.posZ;
        }
        else
        {
            float f = (this.renderYawOffset + (float)(180 * (p_82213_1_ - 1))) * 0.017453292F;
            float f1 = MathHelper.sin(f);
            return this.posZ + (double)f1 * 1.3D;
        }
    }

    private float rotlerp(float p_82204_1_, float p_82204_2_, float p_82204_3_)
    {
        float f = MathHelper.wrapDegrees(p_82204_2_ - p_82204_1_);

        if (f > p_82204_3_)
        {
            f = p_82204_3_;
        }

        if (f < -p_82204_3_)
        {
            f = -p_82204_3_;
        }

        return p_82204_1_ + f;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (this.isEntityInvulnerable(source))
        {
            return false;
        }
        else if (source != DamageSource.DROWN && !(source.getTrueSource() instanceof EntityWither))
        {
            if (this.getInvulTime() > 0 && source != DamageSource.OUT_OF_WORLD)
            {
                return false;
            }
            else
            {
//                if (this.isArmored())
//                {
//                    Entity entity = source.getImmediateSource();
//
//                    if (entity instanceof EntityArrow)
//                    {
//                        return false;
//                    }
//                }

                Entity entity1 = source.getTrueSource();

                if (entity1 != null && !(entity1 instanceof EntityPlayer) && entity1 instanceof EntityLivingBase && ((EntityLivingBase)entity1).getCreatureAttribute() == this.getCreatureAttribute())
                {
                    return false;
                }
                else
                {
//                    if (this.blockBreakCounter <= 0)
//                    {
//                        this.blockBreakCounter = 20;
//                    }

                    for (int i = 0; i < this.idleHeadUpdates.length; ++i)
                    {
                        this.idleHeadUpdates[i] += 3;
                    }

                    return super.attackEntityFrom(source, amount);
                }
            }
        }
        else
        {
            return false;
        }
    }

    /**
     * Drop 0-2 items of this living's type
     */
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier)
    {
//        EntityItem entityitem = this.dropItem(Items.NETHER_STAR, 1);
//
//        if (entityitem != null)
//        {
//            entityitem.setNoDespawn();
//        }
    }

    /**
     * Makes the entity despawn if requirements are reached
     */
    protected void despawnEntity()
    {
        this.idleTime = 0;
    }

    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender()
    {
        return 15728880;
    }

    public void fall(float distance, float damageMultiplier)
    {
    }

    /**
     * adds a PotionEffect to the entity
     */
    public void addPotionEffect(PotionEffect potioneffectIn)
    {
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(2000.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.50D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
    }
    
    @SideOnly(Side.CLIENT)
    public float getHeadYRotation(int p_82207_1_)
    {
        return this.yRotationHeads[p_82207_1_];
    }

    @SideOnly(Side.CLIENT)
    public float getHeadXRotation(int p_82210_1_)
    {
        return this.xRotationHeads[p_82210_1_];
    }

    public int getInvulTime()
    {
        return ((Integer)this.dataManager.get(INVULNERABILITY_TIME)).intValue();
    }

    public void setInvulTime(int time)
    {
        this.dataManager.set(INVULNERABILITY_TIME, Integer.valueOf(time));
    }

    /**
     * Returns whether the wither is armored with its boss armor or not by checking whether its health is below half of
     * its maximum.
     */
    public boolean isArmored()
    {
        return this.getHealth() <= this.getMaxHealth() / 2.0F;
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEAD;
    }

    protected boolean canBeRidden(Entity entityIn)
    {
        return false;
    }

    /**
     * Returns false if this Entity is a boss, true otherwise.
     */
    public boolean isNonBoss()
    {
        return false;
    }
	
	@Override
	public boolean attackEntityAsMob(Entity entityIn)
    {
        if (!super.attackEntityAsMob(entityIn))
        {
            return false;
        }
        else
        {
            return true;
        }
    }
	
	@Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        IEntityLivingData ientitylivingdata = super.onInitialSpawn(difficulty, livingdata);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
        this.setCombatTask();
        return ientitylivingdata;
    }
	
	public void setCombatTask()
    {
        if (this.world != null && !this.world.isRemote)
        {
//            this.tasks.removeTask(this.aiAttackOnCollide);
//            this.tasks.removeTask(this.aiArrowAttack);
            ItemStack itemstack = this.getHeldItemMainhand();

            if (itemstack.getItem() instanceof net.minecraft.item.ItemBow)
            {
                int i = 20;

                if (this.world.getDifficulty() != EnumDifficulty.HARD)
                {
                    i = 40;
                }

//                this.aiArrowAttack.setAttackCooldown(i);
//                this.tasks.addTask(4, this.aiArrowAttack);
            }
            else
            {
//                this.tasks.addTask(4, this.aiAttackOnCollide);
            }
        }
    }

}
