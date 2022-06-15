package lol.bai.megane.api.provider;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

public abstract class FluidInfoProvider<T> extends AbstractProvider<T> {

    @Nullable
    private NbtCompound nbt;

    public boolean hasFluidInfo() {
        return true;
    }

    public abstract int getColor();

    public abstract Text getName();

    @Nullable
    protected final NbtCompound getNbt() {
        return nbt;
    }

    @ApiStatus.Internal
    public final void setFluidInfoContext(@Nullable NbtCompound nbt) {
        this.nbt = nbt;
    }

}
