package fr.kinjer.kinomod.entity.projectile;

import javax.annotation.Nonnull;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.init.InitItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class EntityBismuthBall extends EntityArrow {

    public EntityBismuthBall(World world){
        super(world);
        this.pickupStatus = EntityArrow.PickupStatus.DISALLOWED;
    }

    public EntityBismuthBall(World world, EntityLivingBase shooter){
        super(world, shooter);
        
        if (shooter instanceof EntityPlayer)
        {
            this.pickupStatus = EntityArrow.PickupStatus.DISALLOWED;
        }
    }

    public EntityBismuthBall(World world, double x, double y, double z){
        super(world, x, y, z);
    }

    @Override
    public void setDamage(double damage){
        super.setDamage(1.0D);
    }

    @Override
    @Nonnull
    public ItemStack getArrowStack(){
        return new ItemStack(InitItems.item_bismuth_ball);
    }


    @Override
    public void arrowHit(EntityLivingBase living){
        super.arrowHit(living);
        living.attackEntityFrom(KinoMod.Bismuth, 5.0F);
    }
}
