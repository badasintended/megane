package badasintended.megane.runtime.registry;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectLists;

public class Registry<T> {

    private final Map<Object, List<T>> objMap = new Object2ObjectOpenHashMap<>();
    private final Map<Class<?>, List<Entry<T>>> map = new Object2ObjectOpenHashMap<>();
    private final Map<Class<?>, List<T>> cache = new Object2ObjectOpenHashMap<>();

    private final List<Entry<?>> sorter = new ObjectArrayList<>();

    private final Set<Class<?>> error = new HashSet<>();

    public void add(Class<?> key, T val, int priority) {
        map.computeIfAbsent(key, k -> new ObjectArrayList<>())
            .add(new Entry<>(val, priority));
    }

    public void add(Object key, T val) {
        objMap.put(key, ObjectLists.singleton(val));
    }

    public void error(Object obj) {
        Class<?> clazz = obj.getClass();
        error.add(clazz);
        cache.remove(clazz);
        map.remove(clazz);
    }

    @SuppressWarnings("unchecked")
    public List<T> get(Object obj) {
        if (objMap.containsKey(obj)) {
            return objMap.get(obj);
        }

        if (obj == null) {
            return ObjectLists.emptyList();
        }

        Class<?> clazz = obj.getClass();
        if (clazz == Object.class || error.contains(clazz)) {
            return ObjectLists.emptyList();
        }

        if (cache.containsKey(clazz)) {
            return cache.get(clazz);
        }

        sorter.clear();
        map.forEach((k, v) -> {
            if (k.isInstance(obj)) {
                sorter.addAll(v);
            }
        });
        sorter.sort(Comparator.comparingInt(e -> e.priority));
        List<T> list = new ObjectArrayList<>();
        for (Entry<?> entry : sorter) {
            list.add((T) entry.value);
        }

        if (list.isEmpty()) {
            // Discard empty list so it'll GC-ed
            list = ObjectLists.emptyList();
        }

        cache.put(clazz, list);
        return list;
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
