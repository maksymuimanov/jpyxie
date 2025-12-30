package io.maksymuimanov.python.common;

import java.util.Iterator;
import java.util.Map;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface MapSpec<K, V> extends Iterable<Map.Entry<K, V>> {
    default boolean isEmpty() {
        return this.toMap().isEmpty();
    }

    @Override
    default Iterator<Map.Entry<K, V>> iterator() {
        return this.toMap().entrySet().iterator();
    }

    default void forEach(BiConsumer<? super K, ? super V> action) {
        this.toMap().forEach(action);
    }

    @Override
    default void forEach(Consumer<? super Map.Entry<K, V>> action) {
        Iterable.super.forEach(action);
    }

    @Override
    default Spliterator<Map.Entry<K, V>> spliterator() {
        return Iterable.super.spliterator();
    }

    Map<K, V> toMap();
}
