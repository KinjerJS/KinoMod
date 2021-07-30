package fr.kinjer.kinomod.handler;

import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.network.PacketDash;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public final class HandlerPacket {

	private static final SimpleNetworkWrapper HANDLER = new SimpleNetworkWrapper(KinoMod.MODID);

	public static void init() {
		int id = 0;
		HANDLER.registerMessage(PacketDash.Handler.class, PacketDash.class, id++, Side.SERVER);
	}

	public static void sendToNearby(World world, BlockPos pos, IMessage toSend) {
		if(world instanceof WorldServer) {
			WorldServer ws = (WorldServer) world;

			for (EntityPlayer player : ws.playerEntities) {
				EntityPlayerMP playerMP = (EntityPlayerMP) player;

				if (playerMP.getDistanceSq(pos) < 64 * 64
						&& ws.getPlayerChunkMap().isPlayerWatchingChunk(playerMP, pos.getX() >> 4, pos.getZ() >> 4)) {
					HANDLER.sendTo(toSend, playerMP);
				}
			}

		}
	}

	public static void sendToNearby(World world, Entity e, IMessage toSend) {
		sendToNearby(world, new BlockPos(e), toSend);
	}

	public static void sendTo(EntityPlayerMP playerMP, IMessage toSend) {
		HANDLER.sendTo(toSend, playerMP);
	}

	public static void sendToServer(IMessage msg) {
		HANDLER.sendToServer(msg);
	}

	private HandlerPacket() {}

}
