package lol.bai.megane.runtime.data.wrapper;

import lol.bai.megane.api.provider.ProgressProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.ProgressData;
import net.minecraft.block.entity.BlockEntity;

@SuppressWarnings({"deprecation", "rawtypes"})
public class ProgressWrapper extends DataWrapper<ProgressProvider, BlockEntity> {

    public ProgressWrapper(ProgressProvider megane) {
        super(megane);
    }

    @Override
    public void appendData(IDataWriter data, IServerAccessor<BlockEntity> accessor, IPluginConfig config) {
        data.add(ProgressData.class, res -> {
            setContext(accessor, accessor.getTarget().getPos());

            if (megane.hasProgress()) {
                ProgressData progressData = ProgressData.ratio(megane.getPercentage() / 100f);
                int inputSize = megane.getInputSlotCount();

                for (int i = 0; i < inputSize; i++) {
                    progressData.input(megane.getInputStack(i));
                }

                int outputSize = megane.getOutputSlotCount();

                for (int i = 0; i < outputSize; i++) {
                    progressData.output(megane.getOutputStack(i));
                }

                res.add(progressData);
            }

            if (megane.blockOtherProvider()) res.block();
        });
    }

}
