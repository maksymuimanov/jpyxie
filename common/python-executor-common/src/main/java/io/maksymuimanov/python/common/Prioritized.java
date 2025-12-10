package io.maksymuimanov.python.common;

public interface Prioritized extends Comparable<Prioritized> {
    int MAX_PRIORITY = Integer.MAX_VALUE;
    int HIGH_PRIORITY = 1000;
    int DEFAULT_PRIORITY = 0;
    int LOW_PRIORITY = -1000;
    int MIN_PRIORITY = Integer.MIN_VALUE;

    default int getPriority() {
        return DEFAULT_PRIORITY;
    }

    @Override
    default int compareTo(Prioritized o) {
        return Integer.compare(o.getPriority(), this.getPriority());
    }
}