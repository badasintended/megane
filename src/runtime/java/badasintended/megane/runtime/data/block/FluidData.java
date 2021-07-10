package badasintended.megane.runtime.data.block;

import java.util.List;

import badasintended.megane.api.provider.FluidProvider;
import badasintended.megane.runtime.registry.Registrar;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import static badasintended.megane.runtime.util.Keys.F_HAS;
import static badasintended.megane.runtime.util.Keys.F_ID;
import static badasintended.megane.runtime.util.Keys.F_MAX;
import static badasintended.megane.runtime.util.Keys.F_SIZE;
import static badasintended.megane.runtime.util.Keys.F_STORED;
import static badasintended.megane.util.MeganeUtils.config;

public class FluidData extends BlockData {

    public FluidData() {
        super(Registrar.FLUID, () -> config().fluid);
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    void append(NbtCompound data, ServerPlayerEntity player, World world, BlockEntity blockEntity) {
        List<FluidProvider> providers = Registrar.FLUID.get(blockEntity);
        for (FluidProvider provider : providers) {
            provider.setupContext(world, player);
            if (provider.hasFluid(blockEntity)) {
                data.putBoolean(F_HAS, true);

                int size = provider.getSlotCount(blockEntity);
                int i = 0;

                for (int j = 0; j < size; j++) {
                    Fluid fluid = provider.getFluid(blockEntity, j);
                    if (fluid == null || fluid == Fluids.EMPTY) {
                        continue;
                    }
                    data.putInt(F_ID + i, Registry.FLUID.getRawId(fluid));
                    data.putDouble(F_STORED + i, provider.getStored(blockEntity, j));
                    data.putDouble(F_MAX + i, provider.getMax(blockEntity, j));
                    i++;
                }

                data.putInt(F_SIZE, i);
                return;
            }
        }
    }

}
