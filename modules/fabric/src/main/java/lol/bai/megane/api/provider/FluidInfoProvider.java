package lol.bai.megane.api.provider;

import lol.bai.megane.api.registry.ClientRegistrar;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

/**
 * Base class of dynamic fluid information provider.
 * This decides how fluid info will be displayed on client.
 * <p>
 * Register implementations with {@link ClientRegistrar#addFluidInfo}
 *
 * @param <T> type of {@link Fluid} this provider for.
 *
 * @deprecated use WTHIT API
 */
@Deprecated
public abstract class FluidInfoProvider<T> extends AbstractProvider<T> {

    @Nullable
    private CompoundTag nbt;

    /**
     * Returns whether this provider decides the look of the info.
     */
    public boolean hasFluidInfo() {
        return true;
    }

    /**
     * Returns the color of the fluid
     */
    public abstract int getColor();

    /**
     * Returns the name of the fluid.
     */
    public abstract Component getName();

    /**
     * Returns the nbt of the fluid, if any.
     */
    @Nullable
    protected final CompoundTag getNbt() {
        return nbt;
    }

    @ApiStatus.Internal
    public final void setFluidInfoContext(@Nullable CompoundTag nbt) {
        this.nbt = nbt;
    }

}
