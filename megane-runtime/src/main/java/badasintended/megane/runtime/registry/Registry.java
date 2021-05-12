package badasintended.megane.runtime.registry;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectLists;

public class Registry<T> {

    private final Map<Object, List<T>> objMap = new Object2ObjectOpenHashMap<>();
    private final Map<Class<?>, List<Entry<T>>> map = new Object2ObjectOpenHashMap<>();
    private final Map<Class<?>, List<T>> cache = new Object2ObjectOpenHashMap<>();
    private final List<T> empty = ObjectLists.emptyList();

    private final List<Entry<?>> sorter = new ObjectArrayList<>();

    public void add(Class<?> key, T val, int priority) {
        map.computeIfAbsent(key, k -> new ObjectArrayList<>())
            .add(new Entry<>(val, priority));
    }

    public void add(Object key, T val) {
        objMap.put(key, ObjectLists.singleton(val));
    }

    @SuppressWarnings("unchecked")
    public List<T> get(Class<?> clazz) {
        if (clazz == Object.class) {
            return empty;
        }

        if (cache.containsKey(clazz)) {
            return cache.get(clazz);
        }

        List<T> list;
        if (map.containsKey(clazz)) {
            sorter.clear();
            sorter.addAll(map.get(clazz));
            map.forEach((k, v) -> {
                if (k != clazz && k.isAssignableFrom(clazz)) {
                    sorter.addAll(v);
                }
            });
            sorter.sort(Comparator.comparingInt(e -> e.priority));
            list = new ObjectArrayList<>();
            for (Entry<?> entry : sorter) {
                list.add((T) entry.value);
            }
        } else {
            list = get(clazz.getSuperclass());
        }

        if (list.isEmpty()) {
            list = empty;
        }

        cache.put(clazz, list);
        return list;
    }

    public List<T> get(Object obj) {
        return objMap.containsKey(obj)
            ? objMap.get(obj)
            : obj == null
            ? empty
            : get(obj.getClass());
    }

    static class Entry<T> {

        final int priority;
        final T value;

        Entry(T value, int priority) {
            this.value = value;
            this.priority = priority;
        }

    }

}
