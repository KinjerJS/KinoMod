package fr.kinjer.kinomod.items;

import java.util.Set;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.init.ItemsMod;
import fr.kinjer.kinomod.utils.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ItemSwordKino extends ItemTool {

	public ItemSwordKino(float attackDamageIn, float attackSpeedIn, ToolMaterial materialIn,
			Set<Block> effectiveBlocksIn) {
		super(74, -2.4F, materialIn, effectiveBlocksIn);
		setCreativeTab(KinoMod.tabKino);
		
		String name = "bismuth_sword";
		ItemsMod.itemtool.add((ItemTool) this.setRegistryName(KinoMod.MODID, name).setUnlocalizedName(KinoMod.MODID + "." + name));
	}
	public float getDestroySpeed(ItemStack stack, IBlockState state) {
		return 1;
	}
	
	@Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (!world.isRemote && player.isSneaking()) {
            RayTraceResult pos = WorldUtil.getNearestPositionWithAir(world, player, 100);
            if (pos != null && (pos.typeOfHit == RayTraceResult.Type.BLOCK || player.rotationPitch >= -5)) {
                int side = pos.sideHit.ordinal();
                if (side != -1) {
                    double x = pos.hitVec.x - (side == 4 ? 0.5 : 0) + (side == 5 ? 0.5 : 0);
                    double y = pos.hitVec.y - (side == 0 ? 2.0 : 0) + (side == 1 ? 0.5 : 0);
                    double z = pos.hitVec.z - (side == 2 ? 0.5 : 0) + (side == 3 ? 0.5 : 0);
                        ((EntityPlayerMP) player).connection.setPlayerLocation(x, y, z, player.rotationYaw, player.rotationPitch);
                        player.dismountRidingEntity();
                        world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.PLAYERS, 1.0F, 1.0F);
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
}
