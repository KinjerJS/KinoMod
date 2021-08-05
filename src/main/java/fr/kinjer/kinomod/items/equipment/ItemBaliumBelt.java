package fr.kinjer.kinomod.items.equipment;

import java.util.List;

import javax.annotation.Nullable;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.handler.HandlerTick;
import fr.kinjer.kinomod.handler.HandlerPacket;
import fr.kinjer.kinomod.handler.HandlerSounds;
import fr.kinjer.kinomod.helper.HelperVector;
import fr.kinjer.kinomod.helper.HelperItem;
import fr.kinjer.kinomod.init.InitItems;
import fr.kinjer.kinomod.items.base.BaseKino;
import fr.kinjer.kinomod.items.base.BaseKinoBaubleBelt;
import fr.kinjer.kinomod.network.PacketDash;
import fr.kinjer.kinomod.utils.UtilsKeyBoard;
import fr.kinjer.kinomod.utils.UtilsLocalizer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = KinoMod.MODID)
public class ItemBaliumBelt extends BaseKinoBaubleBelt {

	public static final String TAG_DODGE_COOLDOWN = "dodgeCooldown";
	public static final int MAX_CD = 10;

	private static boolean oldLeftDown, oldRightDown;
	private static int leftDown, rightDown;

	public ItemBaliumBelt() {
		super("balium_belt");
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> l, ITooltipFlag flagIn) {

		if (!UtilsKeyBoard.isShiftKeyDown()) {
			l.add(UtilsLocalizer.shiftDetails());
			return;
		}

		l.add("* §a" + UtilsLocalizer.localize("kinomod.balium_belt.toolip"));
		l.add("* §4" + UtilsLocalizer.localize(MobEffects.WEAKNESS.getName()) + " §4" + UtilsLocalizer.numberLocalize(2));
		l.add("* §4" + UtilsLocalizer.localize(MobEffects.MINING_FATIGUE.getName()));

	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void onKeyDown(KeyInputEvent event) {
		Minecraft mc = Minecraft.getMinecraft();

		IItemHandler baublesInv = BaublesApi.getBaublesHandler(mc.player);
		int slot = BaublesApi.isBaubleEquipped(mc.player, InitItems.balium_belt);
		if (slot < 0) {
			return;
		}
		ItemStack ringStack = baublesInv.getStackInSlot(slot);
		if (HelperItem.getInt(ringStack, TAG_DODGE_COOLDOWN, 0) > 0)
			return;

		int threshold = 4;
		if (mc.gameSettings.keyBindLeft.isKeyDown() && !oldLeftDown) {
			int oldLeft = leftDown;
			leftDown = HandlerTick.ticksInGame;

			if (leftDown - oldLeft < threshold) {
				dodge(mc.player, true);
				mc.player.playSound(HandlerSounds.DASH, 1.0F, 1.0F);
			}
		} else if (mc.gameSettings.keyBindRight.isKeyDown() && !oldRightDown) {
			int oldRight = rightDown;
			rightDown = HandlerTick.ticksInGame;

			if (rightDown - oldRight < threshold) {
				dodge(mc.player, false);
				mc.player.playSound(HandlerSounds.DASH, 1.0F, 1.0F);
			}
		}

		oldLeftDown = mc.gameSettings.keyBindLeft.isKeyDown();
		oldRightDown = mc.gameSettings.keyBindRight.isKeyDown();
	}

	@Override
	public void onWornTick(ItemStack stack, EntityLivingBase player) {
		
		player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 5, 1, true, false));
		player.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 5, 0, true, false));
		
		int cd = HelperItem.getInt(stack, TAG_DODGE_COOLDOWN, 0);
		if (cd > 0)
			HelperItem.setInt(stack, TAG_DODGE_COOLDOWN, cd - 1);
	}

	private static void dodge(EntityPlayer player, boolean left) {
		float yaw = player.rotationYaw;
		float x = MathHelper.sin(-yaw * 0.017453292F - (float) Math.PI);
		float z = MathHelper.cos(-yaw * 0.017453292F - (float) Math.PI);
		HelperVector lookVec = new HelperVector(x, 0, z);
		HelperVector sideVec = lookVec.crossProduct(new HelperVector(0, left ? 1 : -1, 0)).multiply(1.25);

		player.motionX = sideVec.x;
		player.motionY = sideVec.y;
		player.motionZ = sideVec.z;

		HandlerPacket.sendToServer(new PacketDash());
	}

	@SideOnly(Side.CLIENT)
	public static void renderHUD(ScaledResolution resolution, EntityPlayer player, ItemStack stack, float pticks) {
		int xo = resolution.getScaledWidth() / 2 - 20;
		int y = resolution.getScaledHeight() / 2 + 20;

		if (!player.capabilities.isFlying) {
			int cd = HelperItem.getInt(stack, TAG_DODGE_COOLDOWN, 0);
			int width = Math.min((int) ((cd - pticks) * 2), 40);
			GlStateManager.color(1F, 1F, 1F, 1F);
			if (width > 0) {
				Gui.drawRect(xo, y - 2, xo + 40, y - 1, 0x88000000);
				Gui.drawRect(xo, y - 2, xo + width, y - 1, 0xFFFFFFFF);
			}
		}

		GlStateManager.enableAlpha();
		GlStateManager.color(1F, 1F, 1F, 1F);
	}
}
