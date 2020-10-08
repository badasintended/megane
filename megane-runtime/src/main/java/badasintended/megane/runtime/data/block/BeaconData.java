package badasintended.megane.runtime.data.block;

import badasintended.megane.runtime.data.Appender;
import badasintended.megane.runtime.mixin.BeaconBlockEntityAccessor;
import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

import static badasintended.megane.util.MeganeUtils.config;
import static badasintended.megane.util.MeganeUtils.key;

public class BeaconData extends BlockData {

    public BeaconData() {
        super(() -> config().effect);
        appenders.add(new EffectAppender());
    }

    private static class EffectAppender implements Appender<BlockEntity> {

        @Override
        public boolean append(CompoundTag data, ServerPlayerEntity player, World world, BlockEntity blockEntity) {
            if (blockEntity instanceof BeaconBlockEntity) {
                BeaconBlockEntityAccessor beacon = (BeaconBlockEntityAccessor) blockEntity;
                int primary = StatusEffect.getRawId(beacon.getPrimary());
                int secondary = StatusEffect.getRawId(beacon.getSecondary());
                if (primary == secondary) {
                    data.putInt(key("effectSize"), 1);
                    data.putInt(key("effectId0"), primary);
                    if (config().effect.getLevel()) data.putInt(key("effectLv0"), 2);
                } else {
                    data.putInt(key("effectSize"), 2);
                    data.putInt(key("effectId0"), primary);
                    data.putInt(key("effectId1"), secondary);
                }
                return true;
            }
            return false;
        }

    }

}
