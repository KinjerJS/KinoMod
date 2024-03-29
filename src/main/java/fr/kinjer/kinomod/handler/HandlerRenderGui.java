package fr.kinjer.kinomod.handler;

import fr.kinjer.kinomod.client.gui.GuiAirWater;
import fr.kinjer.kinomod.client.gui.GuiPlayerScore;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HandlerRenderGui
{
	@SubscribeEvent
    public void onRenderGui(RenderGameOverlayEvent.Post event)
    {
    	if (event.getType() != ElementType.FOOD) return;
		new GuiAirWater(Minecraft.getMinecraft());
 
    }
	
	@SubscribeEvent
    public void onRenderGuiScore(RenderGameOverlayEvent.Post event)
    {
    	if (event.getType() != ElementType.EXPERIENCE) return;
    	new GuiPlayerScore(Minecraft.getMinecraft());
    }
}
