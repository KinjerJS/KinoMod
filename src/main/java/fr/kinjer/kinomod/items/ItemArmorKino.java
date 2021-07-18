package fr.kinjer.kinomod.items;

import java.util.List;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nullable;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.init.ItemsMod;
import fr.kinjer.kinomod.utils.Localizer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.FoodStats;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemArmorKino extends ItemArmor {

	public static final String DURABILITY = Localizer.localize("§7" + "Durability" + " : ");
	public static final String INFINITE = Localizer.localize("§4" + "Infinite");

	private static final Set<String> damageNegations = new HashSet<>();

	public ItemArmorKino(String name, ArmorMaterial material, int renderIndex, EntityEquipmentSlot equipmentSlot) {
		super(material, renderIndex, equipmentSlot);
		setCreativeTab(KinoMod.tabKino);

		damageNegations.add(DamageSource.DROWN.damageType);
		damageNegations.add(DamageSource.FALL.damageType);
		damageNegations.add(DamageSource.LAVA.damageType);
		damageNegations.add(DamageSource.IN_WALL.damageType);
		damageNegations.add(DamageSource.STARVE.damageType);
		damageNegations.add(DamageSource.IN_FIRE.damageType);
		damageNegations.add(DamageSource.ON_FIRE.damageType);
		damageNegations.add(DamageSource.HOT_FLOOR.damageType);
		damageNegations.add(DamageSource.FLY_INTO_WALL.damageType);

		ItemsMod.itemsarmor.add(
				(ItemArmor) this.setRegistryName(KinoMod.MODID, name).setUnlocalizedName(KinoMod.MODID + "." + name));
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> l, ITooltipFlag flagIn) {
		int damage = stack.getMaxDamage() - stack.getItemDamage();
		l.add(DURABILITY + INFINITE);
	}

	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		return repair.getItem() == ItemsMod.bismuth;
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {

		if (isFullArmor(player)) {
			if (player.isBurning())
				player.extinguish();
			FoodStats foodStats = player.getFoodStats();
			if (player.inventory.hasItemStack(new ItemStack(ItemsMod.tentacle_soup)))
				if (player.ticksExisted % 100 == 0 && foodStats.needFood())
					foodStats.addStats(5, 5F);
			player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(80);
			player.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
			player.stepHeight = 1.0625F;
			player.fallDistance = 0.0f;
			if(world.isRemote) {
				player.capabilities.allowFlying = true;
				player.capabilities.setFlySpeed(0.15F);
			}
			player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 20 * 13, 0, false, false));
			if (player.isInWater()) {
				player.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 5, 0, true, false));
			}
		} else {
			if(world.isRemote) {
				player.capabilities.setFlySpeed(0.05F);
				if (!player.isCreative()) {
					player.capabilities.allowFlying = false;
					player.capabilities.isFlying = false;
				}
			}
			player.stepHeight = 0.5F;
			player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20);
			player.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.0D);
		}
	}

	public static void onPlayerAttacked(LivingDamageEvent event) {
		if (event.getEntity() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntity();
			if (damageNegations.contains(event.getSource().damageType) && isFullArmor(player)) {
				event.setCanceled(true);
			}
		}
	}

	public static boolean isFullArmor(EntityPlayer player) {
		Item head = player.inventory.armorInventory.get(3).getItem();
		Item chestplate = player.inventory.armorInventory.get(2).getItem();
		Item leggings = player.inventory.armorInventory.get(1).getItem();
		Item boots = player.inventory.armorInventory.get(0).getItem();
		return head != null && head == ItemsMod.helmet_item && chestplate != null
				&& chestplate == ItemsMod.chestplate_item && leggings != null && leggings == ItemsMod.leggings_item
				&& boots != null && boots == ItemsMod.boots_item;
	}

}
