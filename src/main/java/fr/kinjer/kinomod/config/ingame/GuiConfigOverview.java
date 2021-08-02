package fr.kinjer.kinomod.config.ingame;

import com.google.common.collect.Lists;
import fr.kinjer.kinomod.KinoMod;
import fr.kinjer.kinomod.config.Config;
import fr.kinjer.kinomod.helper.HelperConfig;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.List;

public class GuiConfigOverview extends GuiConfig {

    public GuiConfigOverview(GuiScreen parentScreen) {
        super(parentScreen, buildConfigList(), KinoMod.MODID, false, false, I18n.format("kinomod.config.title.overview"));
    }

    private static List<IConfigElement> buildConfigList() {
        List<IConfigElement> out = Lists.newLinkedList();
        Config.getAvailableConfigurations().forEach((key, value) -> out.add(HelperConfig.getCategoryElement(key, value)));
        return out;
    }
}
