/**
 *
 */
package org.openforis.collect.api.event;

import org.openforis.collect.api.command.Command;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author D. Wiell
 * @author S. Ricci
 */
public abstract class Event {

    private static final AtomicLong LAST_EVENT_VERSION = new AtomicLong();

    public final long version;
    public final Date timestamp;
    public final Command trigger;

    public Event(Command trigger) {
        this.version = LAST_EVENT_VERSION.incrementAndGet();
        this.trigger = trigger;
        this.timestamp = new Date();
    }
}
