package fr.kinjer.kinomod.handler;

import com.google.common.collect.ImmutableList;

import fr.kinjer.kinomod.KinoMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = KinoMod.MODID)
public final class HandlerTick {

	private HandlerTick() {}

	public static int ticksWithLexicaOpen = 0;
	public static int pageFlipTicks = 0;
	public static int ticksInGame = 0;
	public static float partialTicks = 0;
	public static float delta = 0;
	public static float total = 0;
	private static int ticks, count;

	private static void calcDelta() {
		float oldTotal = total;
		total = ticksInGame + partialTicks;
		delta = total - oldTotal;
	}

	@SubscribeEvent
	public static void renderTick(RenderTickEvent event) {
		if(event.phase == Phase.START)
			partialTicks = event.renderTickTime;
		else {
			calcDelta();
		}
	}
	
	public static void tick() {
		EntityPlayer player = Minecraft.getMinecraft().player;
	}
	
	@SideOnly(Side.CLIENT)
	public static void tickClient() {
		if(ticks > 0)
			--ticks;
	}

	@SubscribeEvent
	public static void clientTickEnd(ClientTickEvent event) {
		if(event.phase == Phase.END) {
			tick();
			tickClient();

			GuiScreen gui = Minecraft.getMinecraft().currentScreen;
			if(gui == null || !gui.doesGuiPauseGame()) {
				ticksInGame++;
				partialTicks = 0;
			}

			calcDelta();
		}
	}

	public static void notifyPageChange() {
		if(pageFlipTicks == 0)
			pageFlipTicks = 5;
	}
}
