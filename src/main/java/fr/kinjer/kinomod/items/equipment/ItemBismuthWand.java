package fr.kinjer.kinomod.items.equipment;

import fr.kinjer.kinomod.entity.projectile.EntityBismuthBall;
import fr.kinjer.kinomod.items.base.BaseKino;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

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
		Vec3d aim = player.getLookVec();

		if (!world.isRemote) {

			EntityBismuthBall arrow = new EntityBismuthBall(world, player);
			arrow.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 15.5F, 1.0F);
			world.spawnEntity(arrow);
		}
		player.swingArm(hand);
		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}
}
