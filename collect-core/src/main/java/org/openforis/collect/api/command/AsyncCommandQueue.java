package org.openforis.collect.api.command;

import org.openforis.collect.api.event.EventListener;

import java.util.concurrent.*;

public class AsyncCommandQueue implements CommandQueue {
    private static final int THREAD_COUNT = 10;

    private final ConcurrentHashMap<Object, BlockingQueue<Boolean>> currentRecords = new ConcurrentHashMap<Object, BlockingQueue<Boolean>>();
    private final ExecutorService executor;
    private final CommandQueue delegate;

    public AsyncCommandQueue(CommandQueue delegate) {
        executor = Executors.newFixedThreadPool(THREAD_COUNT);
        this.delegate = delegate;
    }

    public void submit(final Command command) {
        // Ensure only one command, for a given record, is processed at once.
        // Use a queue per record

        final Object key = queueKey(command);
        BlockingQueue<Boolean> newQueue = new ArrayBlockingQueue<Boolean>(1);
        BlockingQueue<Boolean> currentQueue = currentRecords.putIfAbsent(key, newQueue);
        final BlockingQueue<Boolean> queue = currentQueue == null ? newQueue : currentQueue;

        executor.submit(new Runnable() {
            public void run() {
                try {
                    queue.put(true);
                    delegate.submit(command);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    currentRecords.remove(key);
                    queue.remove();
                }
            }
        });
    }

    public void addListener(EventListener eventListener) {
        delegate.addListener(eventListener);
    }

    public void removeListener(EventListener eventListener) {
        delegate.removeListener(eventListener);
    }

    private Object queueKey(Command command) {
        return command.surveyId + "|" + command.recordId;
    }

    public void stop() {
        executor.shutdownNow();
    }
}
