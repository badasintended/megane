package badasintended.megane.api.provider;

import badasintended.megane.api.registry.BaseTooltipRegistry;

public interface EnergyInfoProvider<T> extends BaseTooltipRegistry.Provider<T> {

    static <T> EnergyInfoProvider<T> of(int color, String unit) {
        return new EnergyInfoProvider<T>() {
            @Override
            public int getColor() {
                return color;
            }

            @Override
            public String getUnit() {
                return unit;
            }
        };
    }

    int getColor();

    String getUnit();

}
