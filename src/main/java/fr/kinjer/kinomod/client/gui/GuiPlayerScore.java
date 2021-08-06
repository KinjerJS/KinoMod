package fr.kinjer.kinomod.client.gui;

import fr.kinjer.kinomod.entity.EntityGhastBossD;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

public class GuiPlayerScore extends Gui{
	
	public static boolean renderPlayerScore = false;
	
	String miss = "Fireball missed : " + (EntityGhastBossD.playerScore - 15);
	String touch = "Fireball touched : " + 15;
	 
    public GuiPlayerScore(Minecraft mc)
    {
    	if (renderPlayerScore == true) {
        ScaledResolution scaled = new ScaledResolution(mc);
        int width = scaled.getScaledWidth();
        int height = scaled.getScaledHeight();
 
        drawCenteredString(mc.fontRenderer, miss, width / 2, (height / 2) + 10, Integer.parseInt("FFAA00", 16));
        drawCenteredString(mc.fontRenderer, touch, width / 2, (height / 2) + 20, Integer.parseInt("FFAA00", 16));
    	}
    }
}