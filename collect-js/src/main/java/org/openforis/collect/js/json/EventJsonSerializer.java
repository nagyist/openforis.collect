package org.openforis.collect.js.json;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.openforis.collect.api.event.*;

import java.util.List;

public class EventJsonSerializer {
    private final ValueJsonSerializer valueJsonSerializer = new ValueJsonSerializer();

    public String serialize(List<? extends Event> events) {
        JsonArray json = new JsonArray();
        for (Event event : events) {
            json.add(event(event, new JsonObject()));
        }
        return new GsonBuilder().create().toJson(json);
    }

    private JsonObject event(Event event, JsonObject json) {
        if (event instanceof EntityAddedEvent)
            return entityAdded((EntityAddedEvent) event, json);
        if (event instanceof EntityListAddedEvent)
            return entityListAdded((EntityListAddedEvent) event, json);
        if (event instanceof AttributeAddedEvent)
            return attributeAdded((AttributeAddedEvent) event, json);
        if (event instanceof AttributeValueUpdatedEvent)
            return attributeValueUpdated((AttributeValueUpdatedEvent) event, json);
        throw new IllegalStateException("Encountered unknown event type: " + event.getClass().getName());
    }

    private JsonObject entityAdded(EntityAddedEvent event, JsonObject json) {
        nodeAdded("EntityAdded", event, json);
        return json;
    }

    private JsonObject entityListAdded(EntityListAddedEvent event, JsonObject json) {
        nodeAdded("EntityListAdded", event, json);
        return json;
    }

    private JsonObject attributeAdded(AttributeAddedEvent event, JsonObject json) {
        nodeAdded("AttributeAdded", event, json);
        json.addProperty("value", valueJsonSerializer.serialize(event.value));
        return json;
    }

    private JsonObject attributeValueUpdated(AttributeValueUpdatedEvent event, JsonObject json) {
        json.addProperty("type", "AttributeValueUpdated");
        json.addProperty("id", event.id);
        json.addProperty("value", valueJsonSerializer.serialize(event.value));
        return json;
    }


    private void nodeAdded(String type, NodeAddedEvent event, JsonObject json) {
        json.addProperty("type", type);
        json.addProperty("parentId", event.parentId);
        json.addProperty("id", event.id);
        json.addProperty("defId", event.defId);
    }
}
