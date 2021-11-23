package badasintended.megane.api.provider;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public interface EnergyInfoProvider {

    static EnergyInfoProvider of(int color, String unit, Text name) {
        return new EnergyInfoProvider() {
            @Override
            public int getColor() {
                return color;
            }

            @Override
            public String getUnit() {
                return unit;
            }

            @Override
            public Text getName() {
                return name;
            }
        };
    }

    int getColor();

    String getUnit();

    Text getName();

}
