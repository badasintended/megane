package lol.bai.megane.runtime.data.wrapper;

import lol.bai.megane.api.provider.EnergyProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.EnergyData;
import net.minecraft.block.entity.BlockEntity;

@SuppressWarnings({"deprecation", "rawtypes"})
public class EnergyWrapper extends DataWrapper<EnergyProvider, BlockEntity> {

    public EnergyWrapper(EnergyProvider megane) {
        super(megane);
    }

    @Override
    public void appendData(IDataWriter data, IServerAccessor<BlockEntity> accessor, IPluginConfig config) {
        data.add(EnergyData.class, res -> {
            setContext(accessor, accessor.getTarget().getPos());
            if (megane.hasEnergy()) res.add(EnergyData.of(megane.getStored(), megane.getMax()));
            if (megane.blockOtherProvider()) res.block();
        });
    }

}
