package fr.kinjer.kinomod.network;

import baubles.api.BaublesApi;
import fr.kinjer.kinomod.init.InitItems;
import fr.kinjer.kinomod.items.equipment.ItemBaliumBelt;
import fr.kinjer.kinomod.handler.HandlerSounds;
import fr.kinjer.kinomod.helper.HelperItem;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.items.IItemHandler;

public class PacketDash implements IMessage {

	@Override
	public void fromBytes(ByteBuf buf) {}

	@Override
	public void toBytes(ByteBuf buf) {}

	public static class Handler implements IMessageHandler<PacketDash, IMessage> {

		@Override
		public IMessage onMessage(PacketDash message, MessageContext ctx) {
			EntityPlayerMP player = ctx.getServerHandler().player;
			player.mcServer.addScheduledTask(() -> {
				player.world.playSound(null, player.posX, player.posY, player.posZ, HandlerSounds.DASH, SoundCategory.PLAYERS, 1F, 1F);
				IItemHandler baublesInv = BaublesApi.getBaublesHandler(player);
				int slot = BaublesApi.isBaubleEquipped(player, InitItems.balium_belt);
				if(slot < 0) {
					ctx.getServerHandler().disconnect(new TextComponentTranslation("botaniamisc.invalidDodge"));
					return;
				}
				ItemStack ringStack = baublesInv.getStackInSlot(slot);
				player.addExhaustion(0.3F);
				HelperItem.setInt(ringStack, ItemBaliumBelt.TAG_DODGE_COOLDOWN, ItemBaliumBelt.MAX_CD);
			});
			return null;
		}
	}
}
