package fr.kinjer.kinomod.client.gui;

import fr.kinjer.kinomod.entity.EntityGhastBossD;
import fr.kinjer.kinomod.entity.EntityGhastBossS;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

public class GuiPlayerScore extends Gui {

	public static boolean renderPlayerScoreD = false;
	public static boolean renderPlayerScoreS = false;

	double valued = (double) Math
			.round((100 * (Math.pow((1 / Math.sqrt(2.0)), ((EntityGhastBossD.playerScore) / 15)))) * 100) / 100;
	double values = (double) Math
			.round((100 * (Math.pow((1 / Math.sqrt(2.0)), ((EntityGhastBossS.playerScore) / 15)))) * 100) / 100;

	String statd = "Score : " + valued + " /100";
	String stats = "Score : " + values + " /100";

	public GuiPlayerScore(Minecraft mc) {
		if (renderPlayerScoreD == true) {
			ScaledResolution scaled = new ScaledResolution(mc);
			int width = scaled.getScaledWidth();
			int height = scaled.getScaledHeight();

			drawCenteredString(mc.fontRenderer, statd, width / 2, (height / 2) + 10, Integer.parseInt("FFAA00", 16));
		}

		if (renderPlayerScoreS == true) {
			ScaledResolution scaled = new ScaledResolution(mc);
			int width = scaled.getScaledWidth();
			int height = scaled.getScaledHeight();

			drawCenteredString(mc.fontRenderer, stats, width / 2, (height / 2) + 10, Integer.parseInt("FFAA00", 16));
		}
	}
}
