package lol.bai.megane.api.provider;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.ApiStatus;

public abstract class AbstractProvider<T> {

    private World world;
    private BlockPos pos;
    private HitResult hitResult;
    private PlayerEntity player;
    private T object;

    /**
     * The earliest method to get called by the runtime.
     * Compute additional context here.
     */
    protected void init() {
    }

    public boolean blockOtherProvider() {
        return false;
    }

    protected final World getWorld() {
        return world;
    }

    protected final BlockPos getPos() {
        return pos;
    }

    protected final HitResult getHitResult() {
        return hitResult;
    }

    protected final PlayerEntity getPlayer() {
        return player;
    }

    protected final T getObject() {
        return object;
    }

    @SuppressWarnings("unchecked")
    protected final <C> C getObjectCasted() {
        return (C) object;
    }

    /**
     * @see #init()
     */
    @ApiStatus.Internal
    public final void setContext(World world, BlockPos pos, HitResult hitResult, PlayerEntity player, T t) {
        this.world = world;
        this.pos = pos;
        this.hitResult = hitResult;
        this.player = player;
        this.object = t;

        setContext(world, pos, player, t);
        init();
    }

    /**
     * @deprecated override {@link #init()} instead.
     */
    @Deprecated
    @ApiStatus.ScheduledForRemoval(inVersion = "1.19")
    public void setContext(World world, BlockPos pos, PlayerEntity player, T t) {
    }

}
