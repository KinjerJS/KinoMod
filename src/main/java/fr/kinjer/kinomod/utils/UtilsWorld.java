package fr.kinjer.kinomod.utils;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;

public class UtilsWorld {

	public static RayTraceResult getNearestPositionWithAir(World world, EntityPlayer player, int reach) {
		return getMovingObjectPosWithReachDistance(world, player, reach, false, false, true);
	}

	private static RayTraceResult getMovingObjectPosWithReachDistance(World world, EntityPlayer player, double distance,
			boolean p1, boolean p2, boolean p3) {
		float f = player.rotationPitch;
		float f1 = player.rotationYaw;
		double d0 = player.posX;
		double d1 = player.posY + player.getEyeHeight();
		double d2 = player.posZ;
		Vec3d vec3 = new Vec3d(d0, d1, d2);
		float f2 = MathHelper.cos(-f1 * 0.017453292F - (float) Math.PI);
		float f3 = MathHelper.sin(-f1 * 0.017453292F - (float) Math.PI);
		float f4 = -MathHelper.cos(-f * 0.017453292F);
		float f5 = MathHelper.sin(-f * 0.017453292F);
		float f6 = f3 * f4;
		float f7 = f2 * f4;
		Vec3d vec31 = vec3.addVector(f6 * distance, f5 * distance, f7 * distance);
		return world.rayTraceBlocks(vec3, vec31, p1, p2, p3);
	}

	public static void sendIndexedChatMessageToPlayer(EntityPlayer player, ITextComponent message) {

		if (player.world == null || player instanceof FakePlayer) {
			return;
		}

		player.sendMessage(message);
	}
}
