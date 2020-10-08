package badasintended.megane.api.registry;

import java.util.*;

@SuppressWarnings("unchecked")
public abstract class BaseTooltipRegistry<T, P extends BaseTooltipRegistry.Provider<? extends T>> {

    protected final Map<Class<? extends T>, P> entries = new HashMap<>();
    protected final Set<Class<?>> nulls = new HashSet<>();

    private final Class<T> tClass;

    public BaseTooltipRegistry(Class<T> tClass) {
        this.tClass = tClass;
    }

    public Map<Class<? extends T>, P> getEntries() {
        return entries;
    }

    public <K extends T> void register(Class<K> clazz, Provider<K> provider) {
        getEntries().put(clazz, (P) provider);
    }

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
