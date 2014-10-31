package org.openforis.collect.api.query;

import org.openforis.collect.api.event.Event;

import java.util.List;

public interface RecordEventsProvider {
    public List<? extends Event> eventsForRecord(int recordId);
}
