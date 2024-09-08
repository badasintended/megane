package lol.bai.megane.module.techreborn.provider;

import mcp.mobius.waila.api.IDataProvider;
import mcp.mobius.waila.api.IDataWriter;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerAccessor;
import mcp.mobius.waila.api.data.ProgressData;
import net.minecraft.world.level.block.entity.BlockEntity;
import reborncore.api.blockentity.InventoryProvider;

public class HardCodedProgressProvider<T extends InventoryProvider> implements IDataProvider<BlockEntity> {

    public static <T extends InventoryProvider> Builder<T> builder(ProgressScaledFunction<T> progressScaledFunction) {
        return new Builder<>(progressScaledFunction);
    }

    public static class Builder<T extends InventoryProvider> {

        private final ProgressScaledFunction<T> progressScaledFunction;

        private Builder(ProgressScaledFunction<T> progressScaledFunction) {
            this.progressScaledFunction = progressScaledFunction;
        }

        private int[] inputSlots = new int[0];
        private int[] outputSlots = new int[0];
        private int scale = 100;
        private boolean inverted = false;

        public Builder<T> input(int... inputSlots) {
            this.inputSlots = inputSlots;
            return this;
        }

        public Builder<T> output(int... outputSlots) {
            this.outputSlots = outputSlots;
            return this;
        }

        public Builder<T> inverted() {
            this.inverted = true;
            return this;
        }

        public Builder<T> scale(int scale) {
            this.scale = scale;
            return this;
        }

        public HardCodedProgressProvider<T> build() {
            return new HardCodedProgressProvider<>(inputSlots, outputSlots, progressScaledFunction, scale, inverted);
        }

    }

    private final int[] inputSlots;
    private final int[] outputSlots;
    private final ProgressScaledFunction<T> progressScaledFunction;
    private final int scale;
    private final boolean inverted;

    private HardCodedProgressProvider(int[] inputSlots, int[] outputSlots, ProgressScaledFunction<T> progressScaledFunction, int scale, boolean inverted) {
        this.inputSlots = inputSlots;
        this.outputSlots = outputSlots;
        this.progressScaledFunction = progressScaledFunction;
        this.scale = scale;
        this.inverted = inverted;
    }

    public interface ProgressScaledFunction<T> {

        int apply(T t, int scale);

    }

    @Override
    @SuppressWarnings("unchecked")
    public void appendData(IDataWriter data, IServerAccessor<BlockEntity> accessor, IPluginConfig config) {
        data.add(ProgressData.class, res -> {
            var target = (T) accessor.getTarget();

            var scaled = progressScaledFunction.apply(target, scale);
            var ratio = (inverted ? (100 - ((scaled == 0) ? 100 : scaled)) : scaled) / 100f;

            res.add(ProgressData
                .ratio(ratio)
                .itemGetter(target.getInventory()::getItem)
                .input(inputSlots)
                .output(outputSlots));
        });
    }

}
