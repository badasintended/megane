package lol.bai.megane.module.luggage;

import com.gizmo.luggage.entity.LuggageEntity;
import lol.bai.megane.module.luggage.provider.LuggageProvider;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;

@SuppressWarnings("unused")
public class MeganeLuggage implements IWailaPlugin {

    @Override
    public void register(IRegistrar registrar) {
        registrar.addEntityData(new LuggageProvider(), LuggageEntity.class);
    }

}
