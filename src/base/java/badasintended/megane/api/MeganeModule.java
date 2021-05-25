package badasintended.megane.api;

import badasintended.megane.api.registry.MeganeClientRegistrar;
import badasintended.megane.api.registry.MeganeRegistrar;

public interface MeganeModule {

    /**
     * Register your tooltips here plz.
     */
    default void register(MeganeRegistrar registrar) {
    }

    default void registerClient(MeganeClientRegistrar registrar) {
    }

}
