package lol.bai.megane.module.techreborn.provider;

import reborncore.api.blockentity.InventoryProvider;

public class HardCodedProgressProvider<T extends InventoryProvider> extends AbstractInventoryProgressProvider<T> {

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
            return new HardCodedProgressProvider<T>(inputSlots, outputSlots, progressScaledFunction, scale, inverted);
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

    @Override
    public int getPercentage() {
        int scaled = progressScaledFunction.apply(getObject(), scale);
        return inverted ? (100 - ((scaled == 0) ? 100 : scaled)) : scaled;
    }

    @Override
    protected int[] getInputSlots() {
        return inputSlots;
    }

    @Override
    protected int[] getOutputSlots() {
        return outputSlots;
    }

    public interface ProgressScaledFunction<T> {

        int apply(T t, int scale);

    }

}
