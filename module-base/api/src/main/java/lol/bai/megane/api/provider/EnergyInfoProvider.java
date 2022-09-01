package lol.bai.megane.api.provider;

import lol.bai.megane.api.registry.ClientRegistrar;
import lol.bai.megane.api.util.BarFormat;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

/**
 * Base class of dynamic energy information provider.
 * This decides how energy info will be displayed on client.
 * <p>
 * Register implementations with {@link ClientRegistrar#addEnergyInfo}
 *
 * @param <T> type of {@link BlockEntity} this provider for.
 */
public abstract class EnergyInfoProvider<T> extends AbstractProvider<T> {

    public static final Text DEFAULT_NAME = new TranslatableText("megane.energy");

    /**
     * Returns whether this provider decides the look of the info.
     */
    public boolean hasEnergyInfo() {
        return true;
    }

    /**
     * Returns the name of energy.
     *
     * @see #DEFAULT_NAME
     */
    public Text getName() {
        return DEFAULT_NAME;
    }

    /**
     * Returns the format of the number on the bar.
     */
    public BarFormat getFormat() {
        return BarFormat.FRACTION;
    }

    /**
     * Returns the color of the energy bar.
     */
    public abstract int getColor();

    /**
     * Returns the unit of the energy.
     */
    public String getUnit() {
        return "E";
    }

}
