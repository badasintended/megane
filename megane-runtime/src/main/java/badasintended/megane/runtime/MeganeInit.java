package badasintended.megane.runtime;

import badasintended.megane.api.MeganeEntrypoint;
import badasintended.megane.util.MeganeUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

import java.util.Arrays;

import static badasintended.megane.util.MeganeUtils.LOGGER;

public class MeganeInit implements ModInitializer {

    @Override
    public void onInitialize() {
        FabricLoader.getInstance().getEntrypointContainers("megane", MeganeEntrypoint.class).forEach(val -> {
            MeganeEntrypoint entry = val.getEntrypoint();
            boolean satisfied = entry.dependencies().length == 0 || Arrays.stream(entry.dependencies()).allMatch(MeganeUtils::hasMod);
            String className = entry.getClass().getName();
            String id = val.getProvider().getMetadata().getId();
            if (satisfied) {
                entry.initialize();
                LOGGER.info("[megane] Loaded {} from {}", className, id);
            } else {
                LOGGER.warn("[megane] {} from {} needs {} to load", className, id, entry.dependencies());
            }
        });
    }

}
