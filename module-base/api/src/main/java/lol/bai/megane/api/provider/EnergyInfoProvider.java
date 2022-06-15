package lol.bai.megane.api.provider;

import javax.annotation.Nullable;

import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public abstract class EnergyInfoProvider<T> extends AbstractProvider<T> {

    public static final Text DEFAULT_NAME = new TranslatableText("megane.energy");

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
