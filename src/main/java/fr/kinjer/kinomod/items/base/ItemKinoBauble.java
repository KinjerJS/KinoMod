package fr.kinjer.kinomod.items.base;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import baubles.api.cap.BaublesCapabilities;
import baubles.api.cap.IBaublesItemHandler;
import baubles.common.Baubles;
import baubles.common.items.ItemRing;
import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.helper.HelperItem;
import fr.kinjer.kinomod.init.InitItems;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = KinoMod.MODID)
public class ItemKinoBauble extends Item implements IBauble {
	@GameRegistry.ObjectHolder(Baubles.MODID + ":ring")
	public static final Item RING = null;

	private static final String TAG_HASHCODE = "playerHashcode";
	private static final String TAG_BAUBLE_UUID_MOST = "baubleUUIDMost";
	private static final String TAG_BAUBLE_UUID_LEAST = "baubleUUIDLeast";

	public ItemKinoBauble(String name) {
		super();
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.setCreativeTab(KinoMod.tabKino);
		InitItems.setItemName(this, name);
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().register((new ItemRing()).setUnlocalizedName("Ring").setRegistryName("ring"));
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
		if (this.isInCreativeTab(tab)) {
			list.add(new ItemStack(this, 1, 0));
		}
	}

	@Override
	public BaubleType getBaubleType(ItemStack itemstack) {
		return BaubleType.BELT;
	}

	@Override
	public boolean hasEffect(ItemStack par1ItemStack) {
		return false;
	}

	@Override
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return EnumRarity.RARE;
	}

	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack) {
		return super.getUnlocalizedName() + "." + par1ItemStack.getItemDamage();
	}

	@Override
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
		player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, .75F, 2f);
	}

	@Override
	public boolean canEquip(ItemStack stack, EntityLivingBase player) {
		return true;
	}

	@Override
	public boolean canUnequip(ItemStack stack, EntityLivingBase player) {
		return true;
	}

	@Override
	public void onWornTick(ItemStack stack, EntityLivingBase player) {
		if(getLastPlayerHashcode(stack) != player.hashCode()) {
			onEquippedOrLoadedIntoWorld(stack, player);
			setLastPlayerHashcode(stack, player.hashCode());
		}
	}

	@Override
	public void onEquipped(ItemStack stack, EntityLivingBase player) {
		player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, .75F, 1.9f);
		onEquippedOrLoadedIntoWorld(stack, player);
		setLastPlayerHashcode(stack, player.hashCode());
	}

	public void onEquippedOrLoadedIntoWorld(ItemStack stack, EntityLivingBase player) {}

	@Override
	public boolean hasContainerItem(ItemStack stack) {
		return !getContainerItem(stack).isEmpty();
	}

	public static UUID getBaubleUUID(ItemStack stack) {
		long most = HelperItem.getLong(stack, TAG_BAUBLE_UUID_MOST, 0);
		if(most == 0) {
			UUID uuid = UUID.randomUUID();
			HelperItem.setLong(stack, TAG_BAUBLE_UUID_MOST, uuid.getMostSignificantBits());
			HelperItem.setLong(stack, TAG_BAUBLE_UUID_LEAST, uuid.getLeastSignificantBits());
			return getBaubleUUID(stack);
		}

		long least = HelperItem.getLong(stack, TAG_BAUBLE_UUID_LEAST, 0);
		return new UUID(most, least);
	}

	public static int getLastPlayerHashcode(ItemStack stack) {
		return HelperItem.getInt(stack, TAG_HASHCODE, 0);
	}

	public static void setLastPlayerHashcode(ItemStack stack, int hash) {
		HelperItem.setInt(stack, TAG_HASHCODE, hash);
	}
	
	@Nonnull
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		ItemStack toEquip = stack.copy();
		toEquip.setCount(1);

		if(canEquip(toEquip, player)) {
			if(world.isRemote)
				return ActionResult.newResult(EnumActionResult.SUCCESS, stack);

			IBaublesItemHandler baubles = BaublesApi.getBaublesHandler(player);
			for(int i = 0; i < baubles.getSlots(); i++) {
				if(baubles.isItemValidForSlot(i, toEquip, player)) {
					ItemStack stackInSlot = baubles.getStackInSlot(i);
					IBauble baubleInSlot = stackInSlot.getCapability(BaublesCapabilities.CAPABILITY_ITEM_BAUBLE, null);
					if(stackInSlot.isEmpty() || baubleInSlot == null || baubleInSlot.canUnequip(stackInSlot, player)) {
						baubles.setStackInSlot(i, ItemStack.EMPTY);

						baubles.setStackInSlot(i, toEquip);
						((IBauble) toEquip.getItem()).onEquipped(toEquip, player);

						stack.shrink(1);

						if(!stackInSlot.isEmpty()) {
							if(baubleInSlot != null) {
								baubleInSlot.onUnequipped(stackInSlot, player);
							}

							if(stack.isEmpty()) {
								return ActionResult.newResult(EnumActionResult.SUCCESS, stackInSlot);
							} else {
								ItemHandlerHelper.giveItemToPlayer(player, stackInSlot);
							}
						}

						return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
					}
				}
			}
		}

		return ActionResult.newResult(EnumActionResult.PASS, stack);
	}
	
	@SubscribeEvent
	public static void onDeath(LivingDeathEvent evt) {
		if(!evt.getEntityLiving().world.isRemote
				&& evt.getEntityLiving() instanceof EntityPlayer
				&& !evt.getEntityLiving().world.getGameRules().getBoolean("keepInventory")
				&& !((EntityPlayer) evt.getEntityLiving()).isSpectator()) {
			IItemHandler inv = BaublesApi.getBaublesHandler((EntityPlayer) evt.getEntityLiving());
			for(int i = 0; i < inv.getSlots(); i++) {
				ItemStack stack = inv.getStackInSlot(i);
				if (!stack.isEmpty() && stack.getItem().getRegistryName().getResourcePath().equals(KinoMod.MODID)) {
					((ItemKinoBauble) stack.getItem()).onUnequipped(stack, evt.getEntityLiving());
				}
			}
		}
	}
}
