package fr.kinjer.kinomod.items.equipment.tool;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Nullable;

import org.lwjgl.input.Keyboard;

import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;

import cofh.api.item.IMultiModeItem;
import cofh.core.util.CoreUtils;
import cofh.core.util.helpers.BaublesHelper;
import cofh.core.util.helpers.ChatHelper;
import cofh.core.util.helpers.EnergyHelper;
import cofh.core.util.helpers.ServerHelper;
import cofh.redstoneflux.api.IEnergyContainerItem;
import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.common.CommonProxy;
import fr.kinjer.kinomod.config.Config;
import fr.kinjer.kinomod.entity.EntityGhastBossD;
import fr.kinjer.kinomod.init.InitItems;
import fr.kinjer.kinomod.utils.UtilsKeyBoard;
import fr.kinjer.kinomod.utils.UtilsLocalizer;
import fr.kinjer.kinomod.utils.UtilsWorld;
import fr.kinjer.kinomod.utils.UtilsItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
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
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.util.registry.RegistrySimple;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ToolSwordKino extends ItemTool implements IMultiModeItem {
	
    private final IRegistry<ResourceLocation, IItemPropertyGetter> properties = new RegistrySimple<ResourceLocation, IItemPropertyGetter>();
	private double attackBismuth;
	private double attackBismuthBoost;
    protected boolean bFull3D;
	
	protected static final UUID ATTACK_BISMUTH_MODIFIER = UUID.randomUUID();
	protected static final UUID SWORD_UUID = UUID.randomUUID();
	
	public static final int DAMAGE = 0;
	public static final int SPEED = 1;
	public static final int RANGE = 2;
	
	private static double BISMUTH_DAMAGE = Config.swordBismuthDamage;
	private static final double ATTACK_DAMAGE = Config.swordAttackDamage - 1.0D;
	private static final double ATTACK_SPEED = -2.4000000953674316D;
	
	public ToolSwordKino(float attackDamageIn, float attackSpeedIn, float attackBismuthIn, Item.ToolMaterial materialIn, Set<Block> effectiveBlocksIn) {
		super(materialIn, effectiveBlocksIn);
        this.toolMaterial = materialIn;
        this.maxStackSize = 1;
        this.attackDamage = (float) ATTACK_DAMAGE;
        this.attackSpeed = (float) ATTACK_SPEED;
        this.attackBismuth = (float) BISMUTH_DAMAGE;
		this.setCreativeTab(KinoMod.tabKino);

		InitItems.setItemToolName(this, "bismuth_sword");
		setHasSubtypes(true);
		setMaxStackSize(1);
		setNoRepair();
	}
	
	@Override
	public boolean hasEffect(ItemStack stack) {

		return true;
	}

	@Override
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
		attackedEntity.attackEntityFrom(CommonProxy.bismuthDamage, (float) BISMUTH_DAMAGE);
		return super.hitEntity(itemstack, attackedEntity, attacker);
	}

	@SideOnly(Side.CLIENT)
	public boolean isFull3D() {
		return true;
	}
	
	public boolean isActive(ItemStack stack) {
		return stack.getTagCompound() != null;
	}

	@Override
	public void readNBTShareTag(ItemStack stack, NBTTagCompound nbt) {
		nbt.setBoolean("Mode", setMode(stack, 1));
		
		super.readNBTShareTag(stack, nbt);
	}
	
	@Override
	public boolean updateItemStackNBT(NBTTagCompound nbt) {
		nbt.getBoolean("Mode");
		return super.updateItemStackNBT(nbt);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {

		ItemStack stack = player.getHeldItem(hand);
		if (!world.isRemote) {
			RayTraceResult pos = UtilsWorld.getNearestPositionWithAir(world, player, 25);
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
						player.getCooldownTracker().setCooldown(this, Config.swordCooldownTeleport * 20);
					}
					return ActionResult.newResult(EnumActionResult.SUCCESS, stack);

				}
			}
		}
		player.swingArm(hand);
		return ActionResult.newResult(EnumActionResult.FAIL, stack);
	}
	
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> l, ITooltipFlag flagIn) {
		
		String SWORD_INFO = UtilsLocalizer.localize("info.kinomod.sword.d." + getMode(stack));

		if (!UtilsKeyBoard.isShiftKeyDown()) {
			l.add(UtilsLocalizer.shiftDetails());
			return;
		}

		l.add(UtilsLocalizer.localize("�a" + SWORD_INFO));

	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isSelected) {

		if (ServerHelper.isClientWorld(world) || CoreUtils.isFakePlayer(entity) || !isActive(stack)) {
			return;
		}
		EntityPlayer player = (EntityPlayer) entity;
		switch (getMode(stack)) {
			case DAMAGE:
				super.attackDamage = (float) (ATTACK_DAMAGE + 5);
				break;
			case SPEED:
				super.attackDamage = (float) ATTACK_DAMAGE;
				super.attackSpeed = (float) (ATTACK_SPEED + 2.0D);
				break;
			case RANGE:
				break;
			default:
				
		}
	}
	
	@Override
	public int getNumModes(ItemStack stack) {
		return 3;
	}

	@Override
	public void onModeChange(EntityPlayer player, ItemStack stack) {

		player.world.playSound(null, player.getPosition(), SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.PLAYERS, 0.4F, (isActive(stack) ? 0.7F : 0.5F) + 0.1F * getMode(stack));
		ChatHelper.sendIndexedChatMessageToPlayer(player, new TextComponentTranslation("info.kinomod.sword.d." + getMode(stack)));
	}
	
	@Override
	public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
		Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

		if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
			multimap.put("Bismuth", new AttributeModifier(ATTACK_BISMUTH_MODIFIER, "Tool modifier", BISMUTH_DAMAGE, 0));
		}

		return multimap;
	}
}
