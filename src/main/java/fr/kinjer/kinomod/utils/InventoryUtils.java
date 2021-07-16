package fr.kinjer.kinomod.utils;

import fr.kinjer.kinomod.config.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class InventoryUtils {
	
	public static void givePlayerStack(EntityPlayer player, ItemStack stack) {
        if (player.world.isRemote) {
            return;
        }
        player.inventory.addItemStackToInventory(stack);
        if (stack.getCount() > 0) {
            dropItemNoDelay(stack, player.world, VectorFromEntity.fromEntity(player));
        }
    }
	
	 public static void dropItemNoDelay(ItemStack stack, World world, VectorFromEntity dropLocation) {
	        EntityItem item = new EntityItem(world, dropLocation.x, dropLocation.y, dropLocation.z, stack);
	        item.motionX = world.rand.nextGaussian() * 0.05;
	        item.motionY = world.rand.nextGaussian() * 0.05 + 0.2F;
	        item.motionZ = world.rand.nextGaussian() * 0.05;
	        world.spawnEntity(item);
	        item.setPickupDelay(0);
	    }
}
