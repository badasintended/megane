package lol.bai.megane.runtime.data.block;

import java.util.List;

import lol.bai.megane.api.provider.FluidProvider;
import lol.bai.megane.runtime.registry.Registrar;
import lol.bai.megane.runtime.util.MeganeUtils;
import mcp.mobius.waila.api.IServerAccessor;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.registry.Registry;

import static lol.bai.megane.runtime.util.Keys.F_HAS;
import static lol.bai.megane.runtime.util.Keys.F_ID;
import static lol.bai.megane.runtime.util.Keys.F_MAX;
import static lol.bai.megane.runtime.util.Keys.F_NBT;
import static lol.bai.megane.runtime.util.Keys.F_SIZE;
import static lol.bai.megane.runtime.util.Keys.F_STORED;

public class FluidData extends BlockData {

    public FluidData() {
        super(Registrar.FLUID, () -> MeganeUtils.config().fluid);
    }

    @Override
    @SuppressWarnings("rawtypes")
    void append(NbtCompound data, IServerAccessor<BlockEntity> accessor) {
        List<FluidProvider> providers = Registrar.FLUID.get(accessor.getTarget());
        for (FluidProvider provider : providers) {
            setContext(provider, accessor);
            if (provider.hasFluids()) {
                data.putBoolean(F_HAS, true);

                int size = provider.getSlotCount();
                int i = 0;

                for (int j = 0; j < size; j++) {
                    Fluid fluid = provider.getFluid(j);
                    if (fluid == null || fluid == Fluids.EMPTY) {
                        continue;
                    }
                    data.putInt(F_ID + i, Registry.FLUID.getRawId(fluid));
                    data.putDouble(F_STORED + i, provider.getStored(j));
                    data.putDouble(F_MAX + i, provider.getMax(j));

                    NbtCompound nbt = provider.getNbt(j);
                    if (nbt != null) {
                        data.put(F_NBT, nbt);
                    }
                    i++;
                }

                data.putInt(F_SIZE, i);
                return;
            }
            if (provider.blockOtherProvider()) {
                return;
            }
        }
    }

}
