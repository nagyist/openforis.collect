package org.openforis.collect.apiadapter;

import org.openforis.collect.api.event.AttributeAddedEvent;
import org.openforis.collect.api.event.EntityAddedEvent;
import org.openforis.collect.api.event.EntityListAddedEvent;
import org.openforis.collect.api.event.Event;
import org.openforis.collect.api.query.RecordEventsProvider;
import org.openforis.idm.model.TextValue;

import java.util.List;

import static java.util.Arrays.asList;

public class DefaultRecordEventsProvider implements RecordEventsProvider {
    private final List<? extends Event> dummyRecordEvents = dummyRecordEvents();

    public List<? extends Event> eventsForRecord(int surveyId, int recordId) {
        return dummyRecordEvents;
    }

    private List<? extends Event> dummyRecordEvents() {
        return asList(
                new EntityAddedEvent(null, null, "1", "plot"),
                new AttributeAddedEvent(null, "1", "2", "plot_number", new TextValue("")),
                new EntityListAddedEvent(null, "1", "1-3", "trees"),
                new EntityAddedEvent(null, "1-3", "4", "tree"),
                new AttributeAddedEvent(null, "4", "5", "tree_number", new TextValue("Test value")),
                new EntityAddedEvent(null, "1-3", "6", "tree"),
                new AttributeAddedEvent(null, "6", "7", "tree_number", new TextValue(""))
        );
    }

}
