package lol.bai.megane.api;

import lol.bai.megane.api.registry.ClientRegistrar;
import lol.bai.megane.api.registry.CommonRegistrar;

/**
 * @deprecated use WTHIT API
 */
@Deprecated
public interface MeganeModule {

    /**
     * Register common-sided providers here.
     */
    default void registerCommon(CommonRegistrar registrar) {
    }

    /**
     * Register client-sided providers here.
     */
    default void registerClient(ClientRegistrar registrar) {
    }

}
