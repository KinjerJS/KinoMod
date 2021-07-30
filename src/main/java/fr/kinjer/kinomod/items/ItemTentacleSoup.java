package fr.kinjer.kinomod.items;

import java.util.Locale;
import java.util.UUID;

import javax.annotation.Nonnull;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.items.base.ItemKino;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.FoodStats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class ItemTentacleSoup extends ItemKino {
	
	public static final String NAME = "tentacle_soup";

	public ItemTentacleSoup() {
		super(NAME);
		this.maxStackSize = 1;
		addPropertyOverride(new ResourceLocation(KinoMod.MODID, "boot"), (stack, worldIn, entityIn) -> ItemTentacleSoup.isBoot(stack) ? 1F : 0F);
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 32;
	}

	@Nonnull
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return isBoot(stack) ? EnumAction.DRINK : EnumAction.EAT;
	}

	@Nonnull
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		if(player.canEat(false)) {
			player.setActiveHand(hand);
			return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
		}
		return ActionResult.newResult(EnumActionResult.PASS, stack);
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase living, int count) {
		super.onUsingTick(stack, living, count);
		if(!(living instanceof EntityPlayer))
			return;
		EntityPlayer player = (EntityPlayer) living;
			if(count % 5 == 0)
				player.getFoodStats().addStats(5, 5F);

			if(count == 5)
				if(player.canEat(false))
					player.getActiveItemStack().getCount();
		
	}

	private static boolean isBoot(ItemStack par1ItemStack) {
		String name = par1ItemStack.getDisplayName().toLowerCase(Locale.ROOT).trim();
		return name.equals("das boot");
	}
}