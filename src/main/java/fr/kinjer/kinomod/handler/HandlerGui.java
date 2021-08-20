package fr.kinjer.kinomod.handler;

import cofh.core.block.TileCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class HandlerGui implements IGuiHandler
{
	public static final int TILE_ID = 0;
	@Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == TILE_ID) {
			TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
			if (tile instanceof TileCore) {
				return ((TileCore) tile).getGuiServer(player.inventory);
			}
		}
        return null;
    }
 
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
    	if(ID == TILE_ID) {
    		TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
    		if (tile instanceof TileCore) {
    			return ((TileCore) tile).getGuiClient(player.inventory);
    		}
    	}
        return null;
    }
}
