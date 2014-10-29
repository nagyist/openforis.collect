/**
 *
 */
package org.openforis.collect.apiadapter;

import org.openforis.collect.api.command.Command;
import org.openforis.collect.api.command.CommandQueue;
import org.openforis.collect.api.command.UpdateAttributeValueCommand;
import org.openforis.collect.api.event.Event;
import org.openforis.collect.api.event.EventListener;
import org.openforis.collect.model.RecordUpdater;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author D. Wiell
 * @author S. Ricci
 */
public class SynchronousCommandQueue implements CommandQueue {

    @SuppressWarnings("rawtypes")
    private final Map<Class<? extends Command>, CommandHandler> handlers = new HashMap<Class<? extends Command>, CommandHandler>();
    private final EventQueue eventQueue;
    private final Set<EventListener> clientListeners = Collections.newSetFromMap(new ConcurrentHashMap<EventListener, Boolean>());

    public SynchronousCommandQueue(RecordProvider recordProvider) {
        RecordUpdater recordUpdater = new RecordUpdater();
        handlers.put(UpdateAttributeValueCommand.class, new UpdateAttributeValueCommandHandler(recordProvider, recordUpdater));
        eventQueue = new EventQueue(new ClientNotifyingEventListener());
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void submit(Command command) {
        CommandHandler handler = handlers.get(command.getClass());
        List<Event> events = handler.handle(command);
        eventQueue.publish(events);
    }

    public void addListener(EventListener eventListener) {
        clientListeners.add(eventListener);
    }

    public void removeListener(EventListener eventListener) {
        clientListeners.remove(eventListener);
    }

    public void stop() {

    }

    private class ClientNotifyingEventListener implements EventListener {
        public void handle(List<? extends Event> events) {
            for (EventListener clientListener : clientListeners) {
                clientListener.handle(events);
            }
        }
    }
}
