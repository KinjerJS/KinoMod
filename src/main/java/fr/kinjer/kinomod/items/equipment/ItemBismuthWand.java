package fr.kinjer.kinomod.items.equipment;

import java.util.List;

import javax.annotation.Nullable;

import fr.kinjer.kinomod.entity.projectile.EntityBismuthBall;
import fr.kinjer.kinomod.handler.HandlerSounds;
import fr.kinjer.kinomod.items.base.BaseKino;
import fr.kinjer.kinomod.utils.UtilsKeyBoard;
import fr.kinjer.kinomod.utils.UtilsLocalizer;
import fr.kinjer.kinomod.init.InitItems;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBismuthWand extends BaseKino {

	public static final String NAME = "bismuth_wand";

	public ItemBismuthWand() {
		super(NAME);
		this.maxStackSize = 1;
	}

	@Override
	public boolean hasEffect(ItemStack stack) {

		return true;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {

		ItemStack stack = player.getHeldItem(hand);

		if (!world.isRemote) {

			EntityBismuthBall arrow = new EntityBismuthBall(world, player);
			arrow.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 150.0F, 1.0F);
			world.spawnEntity(arrow);
			
			if (!player.capabilities.isCreativeMode) {
				player.getCooldownTracker().setCooldown(this, 15);
			}

		}
		player.swingArm(hand);
		player.playSound(HandlerSounds.SPELL_CAST, 0.4F, 1.2F);
		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}
	
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> l, ITooltipFlag flagIn) {

		if (!UtilsKeyBoard.isShiftKeyDown()) {
			l.add(UtilsLocalizer.shiftDetails());
			return;
		}

		l.add("* §5" + UtilsLocalizer.localize("kinomod.bismuth_wand.toolip"));

	}
}
