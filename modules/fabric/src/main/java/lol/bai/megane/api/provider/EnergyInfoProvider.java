package lol.bai.megane.api.provider;

import lol.bai.megane.api.registry.ClientRegistrar;
import lol.bai.megane.api.util.BarFormat;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntity;

/**
 * Base class of dynamic energy information provider.
 * This decides how energy info will be displayed on client.
 * <p>
 * Register implementations with {@link ClientRegistrar#addEnergyInfo}
 *
 * @param <T> type of {@link BlockEntity} this provider for.
 *
 * @deprecated use WTHIT API
 */
@Deprecated
public abstract class EnergyInfoProvider<T> extends AbstractProvider<T> {

    public static final Component DEFAULT_NAME = Component.translatable("megane.energy");

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
    public Component getName() {
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
