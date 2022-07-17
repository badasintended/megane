package lol.bai.megane.module.luggage;

import com.gizmo.luggage.entity.LuggageEntity;
import lol.bai.megane.api.MeganeModule;
import lol.bai.megane.api.registry.CommonRegistrar;
import lol.bai.megane.module.luggage.provider.LuggageItemProvider;

@SuppressWarnings("unused")
public class MeganeLuggage implements MeganeModule {

    @Override
    public void registerCommon(CommonRegistrar registrar) {
        registrar.addItem(LuggageEntity.class, new LuggageItemProvider());
    }

}
