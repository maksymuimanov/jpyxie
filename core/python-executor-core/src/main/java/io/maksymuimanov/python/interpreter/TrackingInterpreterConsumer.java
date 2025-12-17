package io.maksymuimanov.python.interpreter;

import lombok.RequiredArgsConstructor;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@RequiredArgsConstructor
public class TrackingInterpreterConsumer<I extends AutoCloseable> implements PythonInterpreterConsumer<I> {
    private final PythonInterpreterFactory<I> interpreterFactory;
    private final Queue<I> interpreterQueue;

    public TrackingInterpreterConsumer(PythonInterpreterFactory<I> interpreterFactory) {
        this(interpreterFactory, new ConcurrentLinkedQueue<>());
    }

    @Override
    public I consume() {
        I i = interpreterFactory.create();
        interpreterQueue.add(i);
        return i;
    }

    @Override
    public void close() throws Exception {
        I current;
        while ((current = this.interpreterQueue.poll()) != null) {
            current.close();
        }
    }
}
