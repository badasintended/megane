package badasintended.megane.runtime.data.block;

import badasintended.megane.runtime.mixin.AccessorBeaconBlockEntity;
import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

import static badasintended.megane.runtime.util.Keys.S_ID;
import static badasintended.megane.runtime.util.Keys.S_LV;
import static badasintended.megane.runtime.util.Keys.S_SIZE;
import static badasintended.megane.util.MeganeUtils.config;

public class BeaconData extends BlockData {

    public BeaconData() {
        super(() -> config().effect);
    }

    @Override
    void append(NbtCompound data, ServerPlayerEntity player, World world, BlockEntity blockEntity) {
        if (blockEntity instanceof BeaconBlockEntity) {
            AccessorBeaconBlockEntity beacon = (AccessorBeaconBlockEntity) blockEntity;
            int primary = StatusEffect.getRawId(beacon.getPrimary());
            int secondary = StatusEffect.getRawId(beacon.getSecondary());
            if (primary == secondary) {
                data.putInt(S_SIZE, 1);
                data.putInt(S_ID + 0, primary);
                if (config().effect.getLevel())
                    data.putInt(S_LV + 0, 2);
            } else {
                data.putInt(S_SIZE, 2);
                data.putInt(S_ID + 1, primary);
                data.putInt(S_LV + 1, secondary);
            }
        }
    }

}
