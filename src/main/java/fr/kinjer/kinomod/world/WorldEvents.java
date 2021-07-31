package fr.kinjer.kinomod.world;

import java.util.UUID;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.init.InitPotion;
import fr.kinjer.kinomod.items.equipment.ItemBaliumCharm;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class WorldEvents {
	
	@SubscribeEvent
	public static void bleedingActive(LivingUpdateEvent event) {

		if (!event.getEntityLiving().isPotionActive(InitPotion.BLEEDING_EFFECT)) {
			return;
		} else {

			if (event.getEntityLiving().isPotionActive(MobEffects.REGENERATION)) {

				event.getEntityLiving().removeActivePotionEffect(MobEffects.REGENERATION);

			}

			if (event.getEntityLiving().isPotionActive(MobEffects.ABSORPTION)) {

				event.getEntityLiving().removeActivePotionEffect(MobEffects.ABSORPTION);

			}

			event.getEntityLiving().attackEntityFrom(KinoMod.Bismuth, 1.0f);
		}
	}

	@SubscribeEvent
	public static void checkIfPlayerIsInWater(LivingUpdateEvent event) {

		if (!event.getEntityLiving().isInsideOfMaterial(Material.WATER)) {
			return;
		} else {

			event.getEntityLiving().getEntityData().setInteger(ItemBaliumCharm.NAME, 300);

		}
	}
}
