package io.maksymuimanov.python.common;

import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;

public interface SetSpec<T> extends Iterable<T> {
    default boolean isEmpty() {
        return this.toSet().isEmpty();
    }

    @Override
    default Iterator<T> iterator() {
        return this.toSet().iterator();
    }

    @Override
    default void forEach(Consumer<? super T> action) {
        Iterable.super.forEach(action);
    }

    @Override
    default Spliterator<T> spliterator() {
        return Iterable.super.spliterator();
    }

    Set<T> toSet();
}
