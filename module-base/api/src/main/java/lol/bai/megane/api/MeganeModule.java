package lol.bai.megane.api;

import lol.bai.megane.api.registry.ClientRegistrar;
import lol.bai.megane.api.registry.CommonRegistrar;

public interface MeganeModule {

    default void registerCommon(CommonRegistrar registrar) {
    }

    default void registerClient(ClientRegistrar registrar) {
    }

}
