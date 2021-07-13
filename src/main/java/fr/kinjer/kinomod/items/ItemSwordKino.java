package fr.kinjer.kinomod.items;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Nullable;

import org.lwjgl.input.Keyboard;

import com.google.common.collect.Multimap;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.init.ItemsMod;
import fr.kinjer.kinomod.utils.KeyBoard;
import fr.kinjer.kinomod.utils.Localizer;
import fr.kinjer.kinomod.utils.WorldUtil;
import fr.kinjer.kinomod.utils.ItemsUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSwordKino extends ItemTool {

	protected static final String ATTACK_BISMUTH_MODIFIER = "AttackBismuthModifier";

	public ItemSwordKino(float attackDamageIn, float attackSpeedIn, ToolMaterial materialIn,
			Set<Block> effectiveBlocksIn) {
		super(72, -2.4000000953674316F, materialIn, effectiveBlocksIn);
		setCreativeTab(KinoMod.tabKino);

		String name = "bismuth_sword";
		ItemsMod.itemtool.add(
				(ItemTool) this.setRegistryName(KinoMod.MODID, name).setUnlocalizedName(KinoMod.MODID + "." + name));
		setHasSubtypes(true);
		setMaxStackSize(1);
		setNoRepair();
	}

	public float getDestroySpeed(ItemStack stack, IBlockState state) {
		Block block = state.getBlock();

		if (block == Blocks.WEB) {
			return 15.0F;
		} else {
			Material material = state.getMaterial();
			return material != Material.PLANTS && material != Material.VINE && material != Material.CORAL
					&& material != Material.LEAVES && material != Material.GOURD ? 1.0F : 1.5F;
		}
	}

	@Override

	public boolean hitEntity(ItemStack itemstack, EntityLivingBase attackedEntity, EntityLivingBase attacker) {
		attackedEntity.attackEntityFrom(KinoMod.Bismuth, 100.0F);
		return super.hitEntity(itemstack, attackedEntity, attacker);
	}

	@SideOnly(Side.CLIENT)
	public boolean isFull3D() {
		return true;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {

		ItemStack stack = player.getHeldItem(hand);
		if (!world.isRemote) {
			RayTraceResult pos = WorldUtil.getNearestPositionWithAir(world, player, 25);
			if (pos != null && (pos.typeOfHit == RayTraceResult.Type.BLOCK || player.rotationPitch >= -5)) {
				int side = pos.sideHit.ordinal();
				if (side != -1) {
					double x = pos.hitVec.x - (side == 4 ? 0.5 : 0) + (side == 5 ? 0.5 : 0);
					double y = pos.hitVec.y - (side == 0 ? 2.0 : 0) + (side == 1 ? 0.5 : 0);
					double z = pos.hitVec.z - (side == 2 ? 0.5 : 0) + (side == 3 ? 0.5 : 0);
					((EntityPlayerMP) player).connection.setPlayerLocation(x, y, z, player.rotationYaw,
							player.rotationPitch);
					player.dismountRidingEntity();
					world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT,
							SoundCategory.PLAYERS, 1.0F, 1.0F);
					if (!player.capabilities.isCreativeMode) {
						player.getCooldownTracker().setCooldown(this, 80);
					}
					return ActionResult.newResult(EnumActionResult.SUCCESS, stack);

				}
			}
		}
		player.swingArm(hand);
		return ActionResult.newResult(EnumActionResult.FAIL, stack);
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> l, ITooltipFlag flagIn) {

		if(!KeyBoard.isShiftKeyDown()) {
			l.add(Localizer.shiftDetails());
			return;
		}
		
		l.add(String.format("info.kinomod.swordkino.b." , getMode(stack) + Localizer.getKeyName(Keyboard.KEY_F4)));
	}



	private String getMode(ItemStack stack) {
		return "idk";
	}

	public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
		Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

		if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
			multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(),
					new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double) this.attackDamage, 0));
			multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(),
					new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4000000953674316D, 0));
			multimap.put("Bismuth", new AttributeModifier(ATTACK_BISMUTH_MODIFIER, 100.0D, 0));
		}

		return multimap;
	}
}
