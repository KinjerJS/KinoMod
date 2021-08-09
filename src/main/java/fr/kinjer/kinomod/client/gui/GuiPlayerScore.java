package fr.kinjer.kinomod.client.gui;

import fr.kinjer.kinomod.entity.EntityGhastBossB;
import fr.kinjer.kinomod.entity.EntityGhastBossD;
import fr.kinjer.kinomod.entity.EntityGhastBossK;
import fr.kinjer.kinomod.entity.EntityGhastBossS;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

public class GuiPlayerScore extends Gui {

	public static boolean renderPlayerScoreD = false;
	public static boolean renderPlayerScoreS = false;
	public static boolean renderPlayerScoreB = false;
	public static boolean renderPlayerScoreK = false;

	double valued = (double) Math
			.round((100 * (Math.pow((1 / Math.sqrt(2.0)), ((EntityGhastBossD.playerScoreD) / 15)))) * 100) / 100;
	double values = (double) Math
			.round((100 * (Math.pow((1 / Math.sqrt(2.0)), ((EntityGhastBossS.playerScoreS) / 15)))) * 100) / 100;
	double valueb = (double) Math
			.round((100 * (Math.pow((1 / Math.sqrt(2.0)), ((EntityGhastBossB.playerScoreB) / 15)))) * 100) / 100;
	double valuek = (double) Math
			.round((100 * (Math.pow((1 / Math.sqrt(2.0)), ((EntityGhastBossK.playerScoreK) / 15)))) * 100) / 100;

	String statd = "Score : " + valued + " /100";
	String stats = "Score : " + values + " /100";
	String statb = "Score : " + valueb + " /100";
	String statk = "Score : " + valuek + " /100";

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

		if (renderPlayerScoreB == true) {
			ScaledResolution scaled = new ScaledResolution(mc);
			int width = scaled.getScaledWidth();
			int height = scaled.getScaledHeight();

			drawCenteredString(mc.fontRenderer, statb, width / 2, (height / 2) + 10, Integer.parseInt("FFAA00", 16));
		}

		if (renderPlayerScoreK == true) {
			ScaledResolution scaled = new ScaledResolution(mc);
			int width = scaled.getScaledWidth();
			int height = scaled.getScaledHeight();

			drawCenteredString(mc.fontRenderer, statk, width / 2, (height / 2) + 10, Integer.parseInt("FFAA00", 16));
		}
	}
}
