package lol.bai.megane.runtime.data.block;

import lol.bai.megane.runtime.mixin.AccessorBeaconBlockEntity;
import lol.bai.megane.runtime.util.MeganeUtils;
import mcp.mobius.waila.api.IServerAccessor;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.nbt.NbtCompound;

import static lol.bai.megane.runtime.util.Keys.S_ID;
import static lol.bai.megane.runtime.util.Keys.S_LV;
import static lol.bai.megane.runtime.util.Keys.S_SIZE;

public class BeaconData extends BlockData {

    public BeaconData() {
        super(() -> MeganeUtils.config().effect);
    }

    @Override
    void append(NbtCompound data, IServerAccessor<BlockEntity> accessor) {
        if (accessor.getTarget() instanceof AccessorBeaconBlockEntity beacon) {
            int primary = StatusEffect.getRawId(beacon.getPrimary());
            int secondary = StatusEffect.getRawId(beacon.getSecondary());
            if (primary == secondary) {
                data.putInt(S_SIZE, 1);
                data.putInt(S_ID + 0, primary);
                if (MeganeUtils.config().effect.getLevel())
                    data.putInt(S_LV + 0, 2);
            } else {
                data.putInt(S_SIZE, 2);
                data.putInt(S_ID + 1, primary);
                data.putInt(S_LV + 1, secondary);
            }
        }
    }

}
