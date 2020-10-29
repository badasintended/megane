package badasintended.megane.api.registry;

import badasintended.megane.api.provider.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.*;

@SuppressWarnings("unchecked")
public class TooltipRegistry<T, P extends TooltipRegistry.Provider<? extends T>> {

    public static final TooltipRegistry<BlockEntity, EnergyProvider<? extends BlockEntity>> ENERGY = new TooltipRegistry<>(BlockEntity.class);
    public static final TooltipRegistry<BlockEntity, FluidProvider<? extends BlockEntity>> FLUID = new TooltipRegistry<>(BlockEntity.class);
    public static final TooltipRegistry<BlockEntity, InventoryProvider<? extends BlockEntity>> BLOCK_INVENTORY = new TooltipRegistry<>(BlockEntity.class);
    public static final TooltipRegistry<LivingEntity, InventoryProvider<? extends LivingEntity>> ENTITY_INVENTORY = new TooltipRegistry<>(LivingEntity.class);
    public static final TooltipRegistry<BlockEntity, ProgressProvider<? extends BlockEntity>> PROGRESS = new TooltipRegistry<>(BlockEntity.class);

    protected final Map<Class<? extends T>, P> entries = new HashMap<>();
    public final Set<Class<?>> nulls = new HashSet<>();

    private final Class<T> tClass;

    private TooltipRegistry(Class<T> tClass) {
        this.tClass = tClass;
    }

    @ApiStatus.Internal
    public Map<Class<? extends T>, P> getEntries() {
        return entries;
    }

    public <K extends T> void register(Class<K> clazz, Provider<K> provider) {
        getEntries().put(clazz, (P) provider);
    }

    @Nullable
    @ApiStatus.Internal
    public P get(T v) {
        if (nulls.contains(v.getClass())) return null;

        Class<?> clazz = v.getClass();
        boolean containsKey = getEntries().containsKey(clazz);

        if (!containsKey) do {
            clazz = clazz.getSuperclass();
            containsKey = getEntries().containsKey(clazz);
        } while (!containsKey && clazz != tClass);

        if (containsKey) {
            P provider = getEntries().get(clazz);
            getEntries().putIfAbsent((Class<? extends T>) v.getClass(), provider);
            return provider;
        }

        nulls.add(v.getClass());
        return null;
    }

    public interface Provider<T> {

    }

}
