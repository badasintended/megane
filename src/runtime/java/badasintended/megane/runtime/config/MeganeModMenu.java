package badasintended.megane.runtime.config;

import badasintended.megane.runtime.config.screen.MeganeConfigScreen;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class MeganeModMenu implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return MeganeConfigScreen::new;
    }

}
