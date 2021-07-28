package fr.kinjer.kinomod.gui;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.math.MathHelper;

public class GuiAirWater extends Gui {

	public static boolean renderAirWater = false;

	public GuiAirWater(Minecraft mc) {

		if (renderAirWater == true) {

			ScaledResolution scaled = new ScaledResolution(mc);
			int width = scaled.getScaledWidth();
			int height = scaled.getScaledHeight();

			int j1 = scaled.getScaledHeight() - 39;
			int k2 = j1 - 10;
			int i1 = scaled.getScaledWidth() / 2 + 91;
			int i6 = mc.player.getAir();
			int k6 = MathHelper.ceil((double) (i6 - 2) * 10.0D / 300.0D);
			int i7 = MathHelper.ceil((double) i6 * 10.0D / 300.0D) - k6;

			mc.mcProfiler.endStartSection("water_air");

			for (int k7 = 0; k7 < k6 + i7; ++k7) {
				if (k7 < k6) {
					this.drawTexturedModalRect(i1 - k7 * 8 - 9, k2, 16, 18, 9, 9);
				} else {
					this.drawTexturedModalRect(i1 - k7 * 8 - 9, k2, 25, 18, 9, 9);
				}
			}

			mc.mcProfiler.endSection();
		}
	}
}
