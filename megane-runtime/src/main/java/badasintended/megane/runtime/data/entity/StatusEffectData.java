package badasintended.megane.runtime.data.entity;

import java.util.List;
import java.util.stream.Collectors;

import badasintended.megane.runtime.data.Appender;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

import static badasintended.megane.util.MeganeUtils.config;
import static badasintended.megane.util.MeganeUtils.key;

public class StatusEffectData extends EntityData {

    public StatusEffectData() {
        super(() -> config().effect);
        appenders.add(new EffectAppender());
    }

    private static class EffectAppender implements Appender<LivingEntity> {

        @Override
        public boolean append(CompoundTag data, ServerPlayerEntity player, World world, LivingEntity entity) {
            List<StatusEffectInstance> effects = entity
                .getStatusEffects()
                .stream()
                .filter(t -> t.shouldShowParticles() || config().effect.getHidden())
                .collect(Collectors.toList());

            data.putInt(key("effectSize"), effects.size());

            for (int i = 0; i < effects.size(); i++) {
                StatusEffectInstance effect = effects.get(i);
                data.putInt(key("effectId" + i), StatusEffect.getRawId(effect.getEffectType()));
                data.putInt(key("effectLv" + i), config().effect.getLevel() ? effect.getAmplifier() : 0);
            }

            return true;
        }

    }

}
