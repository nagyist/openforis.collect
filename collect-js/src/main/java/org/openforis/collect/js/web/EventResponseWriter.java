package org.openforis.collect.js.web;

import org.openforis.collect.api.command.CommandQueue;
import org.openforis.collect.api.event.Event;
import org.openforis.collect.api.event.EventListener;
import org.openforis.collect.api.query.RecordEventsProvider;
import org.openforis.collect.js.json.EventJsonSerializer;
import org.openforis.collect.js.web.util.StreamingRequestHandler;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class EventResponseWriter implements EventListener {
    private final int surveyId;
    private final int recordId;
    private final AsyncContext ctx;
    private final ReentrantReadWriteLock initializationLock = new ReentrantReadWriteLock();
    private long initialVersion;
    private CommandQueue commandQueue;

    public EventResponseWriter(int surveyId, int recordId, AsyncContext ctx) {
        this.surveyId = surveyId;
        this.recordId = recordId;
        this.ctx = ctx;
    }

    public void handle(List<? extends Event> events) {
        try {
            initializationLock.readLock().tryLock(10, TimeUnit.SECONDS);
            // TODO: Filter out events from correct survey/record somehow
            // TODO: Drop events with version <= initialVersion, to get rid of duplicates
            writeEvents(events);
        } catch (InterruptedException e) {
            throw new IllegalStateException("Unable to handle events", e);
        }
    }

    private void writeEvents(List<? extends Event> events) {
        String json = new EventJsonSerializer().serialize(events);
        writeEvent("recordEvent", json, ctx);
    }

    public void initialize(RecordEventsProvider recordEventsProvider, final CommandQueue commandQueue) {
        this.commandQueue = commandQueue;
        initializationLock.writeLock().lock();
        try {
            ctx.addListener(asyncListener(commandQueue));
            commandQueue.addListener(this);
            List<? extends Event> initialEvents = recordEventsProvider.eventsForRecord(surveyId, recordId);
            writeEvents(initialEvents);
            Event lastEvent = initialEvents.get(initialEvents.size() - 1);
            initialVersion = lastEvent.version;
        } finally {
            initializationLock.writeLock().unlock();
        }
    }

    private StreamingRequestHandler.DefaultAsyncListener asyncListener(final CommandQueue commandQueue) {
        return new StreamingRequestHandler.DefaultAsyncListener() {
            public void onError(AsyncEvent event) throws IOException {
                commandQueue.removeListener(EventResponseWriter.this);
            }

            public void onStartAsync(AsyncEvent event) throws IOException {
                super.onStartAsync(event);
            }

            public void onComplete(AsyncEvent event) throws IOException {
                commandQueue.removeListener(EventResponseWriter.this);
            }

            public void onTimeout(AsyncEvent event) throws IOException {
                super.onTimeout(event);
            }
        };
    }

    private void writeEvent(String eventType, String data, AsyncContext ctx) {
        try {

            String message = "event: " + eventType + "\n"
                    + "data: " + data
                    + "\n\n";
            PrintWriter writer = ctx.getResponse().getWriter();
            writer.write(message);
            writer.flush();
            if (writer.checkError()) {
                commandQueue.removeListener(EventResponseWriter.this);
            }
        } catch (IOException e) {
            commandQueue.removeListener(EventResponseWriter.this);
        }
    }
}
