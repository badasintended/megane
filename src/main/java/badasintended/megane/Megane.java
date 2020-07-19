package badasintended.megane;

import badasintended.megane.api.MeganeApi;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class Megane implements ModInitializer {

    @Override
    public void onInitialize() {
        FabricLoader.getInstance().getEntrypointContainers("megane", MeganeApi.class).forEach(val -> {
            val.getEntrypoint().onMeganeInitialize();
        });
    }

}
