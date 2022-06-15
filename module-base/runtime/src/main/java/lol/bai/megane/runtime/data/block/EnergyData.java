package lol.bai.megane.runtime.data.block;

import java.util.List;

import lol.bai.megane.api.provider.EnergyProvider;
import lol.bai.megane.runtime.registry.Registrar;
import lol.bai.megane.runtime.util.MeganeUtils;
import mcp.mobius.waila.api.IServerAccessor;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;

import static lol.bai.megane.runtime.util.Keys.E_HAS;
import static lol.bai.megane.runtime.util.Keys.E_MAX;
import static lol.bai.megane.runtime.util.Keys.E_STORED;

public class EnergyData extends BlockData {

    public EnergyData() {
        super(Registrar.ENERGY, () -> MeganeUtils.config().energy);
    }

    @Override
    @SuppressWarnings("rawtypes")
    void append(NbtCompound data, IServerAccessor<BlockEntity> accessor) {
        List<EnergyProvider> providers = Registrar.ENERGY.get(accessor.getTarget());
        for (EnergyProvider provider : providers) {
            setContext(provider, accessor);
            if (provider.hasEnergy()) {
                data.putBoolean(E_HAS, true);
                data.putDouble(E_STORED, provider.getStored());
                data.putDouble(E_MAX, provider.getMax());
                return;
            }
            if (provider.blockOtherProvider()) {
                return;
            }
        }
    }

}
