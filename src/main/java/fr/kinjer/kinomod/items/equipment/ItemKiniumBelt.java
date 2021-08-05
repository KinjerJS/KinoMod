package fr.kinjer.kinomod.items.equipment;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import fr.kinjer.kinomod.items.base.BaseKinoBaubleBelt;
import fr.kinjer.kinomod.utils.UtilsKeyBoard;
import fr.kinjer.kinomod.utils.UtilsLocalizer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemKiniumBelt extends BaseKinoBaubleBelt {

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

		tooltip.add("* §a" + UtilsLocalizer.localize("kinomod.kinium_belt.toolip"));
	}
}
