package lol.bai.megane.api.provider;

import javax.annotation.Nullable;

import net.minecraft.text.Text;

public abstract class EnergyInfoProvider<T> extends AbstractProvider<T> {

    public static final Text DEFAULT_NAME = Text.translatable("megane.energy");

    public boolean hasEnergyInfo() {
        return true;
    }

    public Text getName() {
        return DEFAULT_NAME;
    }

    public abstract int getColor();

    @Nullable
    public abstract String getUnit();

}
