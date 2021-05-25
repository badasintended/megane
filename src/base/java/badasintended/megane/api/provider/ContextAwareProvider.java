package badasintended.megane.api.provider;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public interface ContextAwareProvider {

    default void setupContext(World world, PlayerEntity player) {
    }

}
