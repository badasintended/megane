package badasintended.megane.runtime.config;

import badasintended.megane.runtime.config.screen.MeganeConfigScreen;
import io.github.prospector.modmenu.api.ConfigScreenFactory;
import io.github.prospector.modmenu.api.ModMenuApi;

public class MeganeModMenu implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return MeganeConfigScreen::new;
    }

}
