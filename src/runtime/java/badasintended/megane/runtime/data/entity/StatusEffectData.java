package badasintended.megane.runtime.data.entity;

import java.util.List;
import java.util.stream.Collectors;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

import static badasintended.megane.runtime.util.Keys.S_ID;
import static badasintended.megane.runtime.util.Keys.S_LV;
import static badasintended.megane.runtime.util.Keys.S_SIZE;
import static badasintended.megane.util.MeganeUtils.config;

public class StatusEffectData extends EntityData {

    public StatusEffectData() {
        super(() -> config().effect);
    }

    @Override
    void append(CompoundTag data, ServerPlayerEntity player, World world, LivingEntity entity) {
        List<StatusEffectInstance> effects = entity
            .getStatusEffects()
            .stream()
            .filter(t -> t.shouldShowParticles() || config().effect.getHidden())
            .collect(Collectors.toList());

        data.putInt(S_SIZE, effects.size());

        for (int i = 0; i < effects.size(); i++) {
            StatusEffectInstance effect = effects.get(i);
            data.putInt(S_ID + i, StatusEffect.getRawId(effect.getEffectType()));
            data.putInt(S_LV + i, config().effect.getLevel() ? effect.getAmplifier() : 0);
        }
    }

}
