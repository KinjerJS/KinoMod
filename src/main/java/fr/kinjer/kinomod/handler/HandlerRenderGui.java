package fr.kinjer.kinomod.handler;

import fr.kinjer.kinomod.gui.GuiAirWater;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HandlerRenderGui
{
	@SubscribeEvent
    public void onRenderGui(RenderGameOverlayEvent.Post event)
    {
    	if (event.getType() != ElementType.FOOD) return;
		new GuiAirWater(Minecraft.getMinecraft());
 
    }
}
