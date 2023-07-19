package lol.bai.megane.runtime.data.wrapper;

import lol.bai.megane.api.provider.FluidProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.FluidData;
import net.minecraft.block.entity.BlockEntity;

@SuppressWarnings({"deprecation", "rawtypes"})
public class FluidWrapper extends DataWrapper<FluidProvider, BlockEntity> {

    public FluidWrapper(FluidProvider megane) {
        super(megane);
    }

    @Override
    public void appendData(IDataWriter data, IServerAccessor<BlockEntity> accessor, IPluginConfig config) {
        data.add(FluidData.class, res -> {
            setContext(accessor, accessor.getTarget().getPos());

            if (megane.hasFluids()) {
                int size = megane.getSlotCount();
                FluidData fluidData = FluidData.of(size);

                for (int i = 0; i < size; i++) {
                    fluidData.add(megane.getFluid(i), megane.getNbt(i), megane.getStored(i), megane.getMax(i));
                }

                res.add(fluidData);
            }

            if (megane.blockOtherProvider()) res.block();
        });
    }

}
