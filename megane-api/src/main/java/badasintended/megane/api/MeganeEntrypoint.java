package badasintended.megane.api;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public interface MeganeEntrypoint {

    default String[] dependencies() {
        return new String[0];
    }

    /**
     * Register your tooltips here plz.
     */
    default void initialize() {
    }

    @Environment(EnvType.CLIENT)
    default void initializeClient() {
    }

}
