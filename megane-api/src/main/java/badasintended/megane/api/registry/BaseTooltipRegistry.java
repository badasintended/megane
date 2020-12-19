package badasintended.megane.api.registry;

import java.util.Map;
import java.util.Set;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

@ApiStatus.Internal
@SuppressWarnings("unchecked")
public class BaseTooltipRegistry<T, P extends BaseTooltipRegistry.Provider<? extends T>> {

    protected final Map<T, P> objEntries = new Object2ObjectOpenHashMap<>();
    protected final Map<Class<? extends T>, P> entries = new Object2ObjectOpenHashMap<>();
    public final Set<Class<?>> nulls = new ObjectOpenHashSet<>();

    private final Class<T> tClass;

    BaseTooltipRegistry(Class<T> tClass) {
        this.tClass = tClass;
    }

    @ApiStatus.Internal
    public Map<Class<? extends T>, P> getEntries() {
        return entries;
    }

    public <K extends T> void register(Class<K> clazz, BaseTooltipRegistry.Provider<K> provider) {
        getEntries().put(clazz, (P) provider);
    }

    public <K extends T> void register(K obj, BaseTooltipRegistry.Provider<K> provider) {
        objEntries.put(obj, (P) provider);
    }

    @Nullable
    @ApiStatus.Internal
    public P get(T v) {
        if (objEntries.containsKey(v))
            return objEntries.get(v);

        if (nulls.contains(v.getClass()))
            return null;

        Class<?> clazz = v.getClass();
        boolean containsKey = getEntries().containsKey(clazz);

        if (!containsKey)
            do {
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

    @SuppressWarnings("unused")
    public interface Provider<T> {

    }

}
