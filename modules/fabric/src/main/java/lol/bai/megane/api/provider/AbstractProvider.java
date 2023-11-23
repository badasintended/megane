package lol.bai.megane.api.provider;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.ApiStatus;

/**
 * Base class to all providers.
 *
 * @deprecated use WTHIT API
 */
@Deprecated
public abstract class AbstractProvider<T> {

    private Level world;
    private BlockPos pos;
    private HitResult hitResult;
    private Player player;
    private T object;

    /**
     * The earliest method to get called by the runtime.
     * Compute additional context here.
     */
    protected void init() {
    }

    /**
     * Returns whether this provider will block any other provider.
     */
    public boolean blockOtherProvider() {
        return false;
    }

    /**
     * Returns the world context.
     */
    protected final Level getWorld() {
        return world;
    }

    /**
     * Returns the position context.
     */
    protected final BlockPos getPos() {
        return pos;
    }

    /**
     * Returns the hit context.
     */
    protected final HitResult getHitResult() {
        return hitResult;
    }

    /**
     * Returns the player context.
     */
    protected final Player getPlayer() {
        return player;
    }

    /**
     * Returns the object context.
     */
    protected final T getObject() {
        return object;
    }

    /**
     * Returns the object context, casted to arbitrary type.
     */
    @SuppressWarnings("unchecked")
    protected final <C> C getObjectCasted() {
        return (C) object;
    }

    /**
     * @see #init()
     */
    @ApiStatus.Internal
    public final void setContext(Level world, BlockPos pos, HitResult hitResult, Player player, T t) {
        this.world = world;
        this.pos = pos;
        this.hitResult = hitResult;
        this.player = player;
        this.object = t;

        init();
    }

}
