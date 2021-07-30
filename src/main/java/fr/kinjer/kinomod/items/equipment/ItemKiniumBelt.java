package fr.kinjer.kinomod.items.equipment;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import fr.kinjer.kinomod.enchantment.EnchantmentBelt;
import fr.kinjer.kinomod.helper.HelperBeltEnchant;
import fr.kinjer.kinomod.helper.HelperNBT;
import fr.kinjer.kinomod.items.base.BaseKino;
import fr.kinjer.kinomod.utils.UtilsKeyBoard;
import fr.kinjer.kinomod.utils.UtilsLocalizer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemKiniumBelt extends BaseKino implements IBauble {

    private static Random rand = new Random();

    public ItemKiniumBelt() {
    	super("kinium_belt");
    }
    
	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
		if (itemstack.getItemDamage() == 0 && player.ticksExisted % 39 == 0) {
		}
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {

		if (!UtilsKeyBoard.isShiftKeyDown()) {
			tooltip.add(UtilsLocalizer.shiftDetails());
			return;
		}
		
		List<EnchantmentBelt> enchantments = getBeltEnchantments(stack);
        for (EnchantmentBelt ench : enchantments) {
        	tooltip.add(TextFormatting.BLUE + ench.getDescription());
        }

        tooltip.add("* " + UtilsLocalizer.localize("kinomod.kinium_belt.toolip"));

	}

	@Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if(isInCreativeTab(tab)) {
            ItemStack stack = new ItemStack(this);

            items.add(stack);
        }
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if(!worldIn.isRemote && getBeltEnchantments(stack).isEmpty()) {
        	HelperBeltEnchant.rollBelt(stack);
        }
    }

    public static List<EnchantmentBelt> getBeltEnchantments(ItemStack stack) {
        if(stack.isEmpty() || !(stack.getItem() instanceof ItemKiniumBelt)) {
            return Lists.newArrayList();
        }

        NBTTagCompound tag = HelperNBT.getPersistentData(stack);
        if(!tag.hasKey("belttEnchantments")) {
            return Lists.newArrayList();
        }
        NBTTagList enchants = tag.getTagList("beltEnchantments", Constants.NBT.TAG_COMPOUND);
        List<EnchantmentBelt> enchantments = new ArrayList<>(enchants.tagCount());
        for (int i = 0; i < enchants.tagCount(); i++) {
        	EnchantmentBelt ench = EnchantmentBelt.deserialize(enchants.getCompoundTagAt(i));
            if(ench != null) {
                enchantments.add(ench);
            }
        }
        enchantments.sort(Comparator.comparing(EnchantmentBelt::getType));
        return enchantments;
    }

    public static void setBeltEnchantments(ItemStack stack, List<EnchantmentBelt> enchantments) {
        if(stack.isEmpty() || !(stack.getItem() instanceof ItemKiniumBelt)) {
            return;
        }
        enchantments.sort(Comparator.comparing(EnchantmentBelt::getType));

        NBTTagCompound tag = HelperNBT.getPersistentData(stack);
        NBTTagList enchants = tag.hasKey("beltEnchantments", Constants.NBT.TAG_COMPOUND) ?
                tag.getTagList("beltEnchantments", Constants.NBT.TAG_COMPOUND) : new NBTTagList();
        for (EnchantmentBelt enchant : enchantments) {
            enchants.appendTag(enchant.serialize());
        }
        tag.setTag("beltEnchantments", enchants);
    }

    @Override
    public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
        player.playSound(SoundEvents.BLOCK_GLASS_PLACE, .65F, 6.4f);
    }

    @Override
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
        player.playSound(SoundEvents.BLOCK_GLASS_PLACE, .65F, 6.4f);
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemstack) {
        return BaubleType.BELT;
    }

    @Override
    public boolean willAutoSync(ItemStack itemstack, EntityLivingBase player) {
        return true;
    }
}
