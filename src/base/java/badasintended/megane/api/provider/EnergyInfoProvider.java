package badasintended.megane.api.provider;

public interface EnergyInfoProvider<T> {

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
