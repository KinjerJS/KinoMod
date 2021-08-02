package fr.kinjer.kinomod.helper;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.*;

import java.util.ArrayList;

import fr.kinjer.kinomod.KinoMod;

public class HelperConfig {

    public static IConfigElement getCategoryElement(String name, Configuration cfg) {
        return new FileConfigElement(name, null, ConfigGuiType.CONFIG_CATEGORY, "config." + KinoMod.MODID + ".category." + name, cfg) {
            {
                for (String s : cfg.getCategoryNames()) {
                    if (s.contains(Configuration.CATEGORY_SPLITTER)) continue;
                    this.childElements.add(new ConfigElement(cfg.getCategory(s)));
                }
            }
        }.setConfigEntryClass(SubCategoryClass.class);
    }

    public static class SubCategoryClass extends GuiConfigEntries.CategoryEntry {

        public SubCategoryClass(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement configElement) {
            super(owningScreen, owningEntryList, configElement);
        }

        @Override
        protected GuiScreen buildChildScreen() {
            return new GuiConfig(this.owningScreen,
                    this.configElement.getChildElements(), this.owningScreen.modID, this.configElement.getName(),
                    this.configElement.requiresWorldRestart() || this.owningScreen.allRequireWorldRestart,
                    this.configElement.requiresMcRestart() || this.owningScreen.allRequireMcRestart,
                    GuiConfig.getAbridgedConfigPath(this.configElement.toString()));
        }
    }

    private static class FileConfigElement extends DummyConfigElement {

        private Configuration cfg;

        FileConfigElement(String name, Object defaultValue, ConfigGuiType type, String langKey, Configuration cfg) {
            super(name, defaultValue, type, langKey);
            this.cfg = cfg;
            this.childElements = new ArrayList<>();
        }

        @Override
        public String toString() {
            return cfg.toString();
        }
    }
}
