package fr.kinjer.kinomod.handler;

import fr.kinjer.kinomod.client.gui.GuiExtractor;
import fr.kinjer.kinomod.containers.ContainerExtractor;
import fr.kinjer.kinomod.tileentities.TileExtractor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class HandlerGui implements IGuiHandler
{
	@Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
        if(te instanceof TileExtractor) {
            return new ContainerExtractor((TileExtractor)te, player.inventory);
        }
        return null;
    }
 
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
        if(te instanceof TileExtractor) {
            return new GuiExtractor((TileExtractor)te, player.inventory);
        }
        return null;
    }
}
