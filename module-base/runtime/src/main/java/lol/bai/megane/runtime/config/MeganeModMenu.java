package lol.bai.megane.runtime.config;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import lol.bai.megane.runtime.config.screen.MeganeConfigScreen;
import lol.bai.megane.runtime.util.MeganeUtils;

public class MeganeModMenu implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return MeganeConfigScreen::new;
    }

    @Override
    public Map<String, ConfigScreenFactory<?>> getProvidedConfigScreenFactories() {
        return ImmutableMap.of(MeganeUtils.MODID, MeganeConfigScreen::new);
    }

}
