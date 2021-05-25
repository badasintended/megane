package badasintended.megane.runtime.data.block;

import java.util.List;

import badasintended.megane.api.provider.EnergyProvider;
import badasintended.megane.runtime.registry.Registrar;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

import static badasintended.megane.runtime.util.Keys.E_HAS;
import static badasintended.megane.runtime.util.Keys.E_MAX;
import static badasintended.megane.runtime.util.Keys.E_STORED;
import static badasintended.megane.util.MeganeUtils.config;

public class EnergyData extends BlockData {

    public EnergyData() {
        super(() -> config().energy);
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    void append(CompoundTag data, ServerPlayerEntity player, World world, BlockEntity blockEntity) {
        List<EnergyProvider> providers = Registrar.ENERGY.get(blockEntity);
        for (EnergyProvider provider : providers) {
            provider.setupContext(world, player);
            if (provider.hasEnergy(blockEntity)) {
                data.putBoolean(E_HAS, true);
                data.putDouble(E_STORED, provider.getStored(blockEntity));
                data.putDouble(E_MAX, provider.getMax(blockEntity));
                return;
            }
        }
    }

}
