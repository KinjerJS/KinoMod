package fr.kinjer.kinomod.items;

import static net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType.AIR;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import fr.kinjer.kinomod.gui.GuiAirWater;
import fr.kinjer.kinomod.utils.KeyBoard;
import fr.kinjer.kinomod.utils.Localizer;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.command.CommandResultStats;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.GuiScreenEvent.PotionShiftEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDrowningCharm extends ItemCharm {
	
	public static final String NAME = "drown";
	public static final String TAG_NAME = "drown air";
	public static final String TAG_BOOLEAN = "doing drown";
	
	public ItemDrowningCharm() {
		super("charm_of_drowning");
	}
	
	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
		if (itemstack.getItemDamage() == 0 && player.ticksExisted % 39 == 0) {
			player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 40, 1, true, true));
			player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 13 * 20, 0, true, true));
		}
		
		if (!player.getEntityData().hasKey(TAG_NAME)) {
			player.getEntityData().setInteger(TAG_NAME, 300);
		}

		if (player.isInsideOfMaterial(Material.WATER)) {
			GuiIngameForge.renderAir = false;
			GuiAirWater.renderAirWater = false;
			player.setAir(300);
			player.getEntityData().setInteger(TAG_NAME, 300);
		}

		if (!player.isInsideOfMaterial(Material.WATER)) {
			
			GuiIngameForge.renderAir = true;
			GuiAirWater.renderAirWater = true;
			
			int respiration = EnchantmentHelper.getRespirationModifier(player);
			int air = player.getEntityData().getInteger(TAG_NAME);
			air = ((respiration > 0) && (player.getRNG().nextInt(respiration + 1) > 0) ? air : air - 1);
			
			if (air == -20) {
				air = 0;
				player.attackEntityFrom(DamageSource.DROWN, 2.0F);

			}
			player.getEntityData().setInteger(TAG_NAME, air);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> l, ITooltipFlag flagIn) {
		if (!KeyBoard.isShiftKeyDown()) {
			l.add(Localizer.shiftDetails());
			return;
		}

		l.add("* " + Localizer.localize(MobEffects.HASTE.getName()) + " " + Localizer.numberLocalize(2));
		l.add("* " + Localizer.localize(MobEffects.WATER_BREATHING.getName()));
		l.add("* " + Localizer.localize(MobEffects.NIGHT_VISION.getName()));
		l.add("* §4" + Localizer.localize("kinomod.charmdrowning.damagewater"));

	}
}
