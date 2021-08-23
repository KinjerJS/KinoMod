package fr.kinjer.kinomod.items;

import java.util.List;
import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.init.InitItems;
import fr.kinjer.kinomod.items.base.BaseKino;
import fr.kinjer.kinomod.utils.UtilsKeyBoard;
import fr.kinjer.kinomod.utils.UtilsLocalizer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionAbsorption;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBismuthSoup extends ItemFood {
	
	public ItemBismuthSoup(String name, int amount, boolean isWolfFood){
        super(amount, isWolfFood);
		this.setUnlocalizedName("kinomod." + name);
		this.setRegistryName(name);
		this.setCreativeTab(KinoMod.tabKino);
		this.maxStackSize = 1;
		
		InitItems.items.add(this);
	}
	
    @Override
    protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player){
        if(!world.isRemote) {
        	int value = 15;
			player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 20 * value, 1));
        	player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 20 * value, 1));
        	player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 20 * value, 1));
        	player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 20 * value, 1));
        	player.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 20 * value, 1));
        	player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 20 * value, 1));
        	player.addPotionEffect(new PotionEffect(MobEffects.HEALTH_BOOST, 20 * value, 1));
        	player.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 20 * value, 1));
        	player.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 20 * value, 1));
        	player.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 20 * value, 1));
        	player.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 20 * value, 1));
        	player.addPotionEffect(new PotionEffect(MobEffects.LUCK, 20 * value, 1));
        	player.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 20 * value, 1));
        	player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 20 * value, 1));
        	player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 20 * value, 1));
        	player.addPotionEffect(new PotionEffect(MobEffects.POISON, 20 * value, 1));
        	player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20 * value, 1));
        	player.addPotionEffect(new PotionEffect(MobEffects.UNLUCK, 20 * value, 1));
        	player.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 20 * value, 1));
        	player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 20 * value, 1));
        	player.addPotionEffect(new PotionEffect(MobEffects.WITHER, 20 * value, 1));
        	player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 20 * value, 1));
        	player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 20 * value, 1));          	
        	player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 20 * value, 1));
        	
        }
    }
    
    @Override
    public boolean hasEffect(ItemStack stack){
    	return true;
    }
    
    @SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> l, ITooltipFlag flagIn) {
		if (!UtilsKeyBoard.isShiftKeyDown()) {
			l.add(UtilsLocalizer.shiftDetails());
			return;
		}

		l.add("* §a" + UtilsLocalizer.localize(MobEffects.HASTE.getName()) + " §a" + UtilsLocalizer.numberLocalize(2));

	}
}