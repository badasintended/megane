package lol.bai.megane.runtime.data.entity;

import java.util.List;

import lol.bai.megane.runtime.util.Keys;
import lol.bai.megane.runtime.util.MeganeUtils;
import mcp.mobius.waila.api.IServerAccessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.nbt.NbtCompound;

public class StatusEffectData extends EntityData {

    public StatusEffectData() {
        super(() -> MeganeUtils.config().effect);
    }

    @Override
    void append(NbtCompound data, IServerAccessor<Entity> accessor) {
        if (accessor.getTarget() instanceof LivingEntity livingEntity) {
            List<StatusEffectInstance> effects = livingEntity
                .getStatusEffects()
                .stream()
                .filter(t -> t.shouldShowParticles() || MeganeUtils.config().effect.getHidden())
                .toList();

            data.putInt(Keys.S_SIZE, effects.size());

            for (int i = 0; i < effects.size(); i++) {
                StatusEffectInstance effect = effects.get(i);
                data.putInt(Keys.S_ID + i, StatusEffect.getRawId(effect.getEffectType()));
                data.putInt(Keys.S_LV + i, MeganeUtils.config().effect.getLevel() ? effect.getAmplifier() : 0);
            }
        }
    }

}
