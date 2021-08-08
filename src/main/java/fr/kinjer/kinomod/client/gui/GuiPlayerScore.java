package fr.kinjer.kinomod.client.gui;

import fr.kinjer.kinomod.entity.EntityGhastBossD;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

public class GuiPlayerScore extends Gui {
	
	public static boolean renderPlayerScore = false;
	
	double value = (double) Math.round((100 * (Math.pow((1/Math.sqrt(2.0)), ((EntityGhastBossD.playerScore) / 15)))) * 100) / 100;
	
	String stat = "Score : " + value + " /100";
	 
    public GuiPlayerScore(Minecraft mc)
    {
    	if (renderPlayerScore == true) {
        ScaledResolution scaled = new ScaledResolution(mc);
        int width = scaled.getScaledWidth();
        int height = scaled.getScaledHeight();
 
        drawCenteredString(mc.fontRenderer, stat, width / 2, (height / 2) + 10, Integer.parseInt("FFAA00", 16));
    	}
    }
}
