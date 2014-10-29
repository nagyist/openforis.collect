package org.openforis.collect.js.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.openforis.collect.api.User;
import org.openforis.collect.api.command.UpdateAttributeValueCommand;

public class JsonCommandParser {
    public JsonCommandParser() {
    }

    public UpdateAttributeValueCommand updateAttributeValueCommand(String jsonString, User user) {
        JsonObject json = toJson(jsonString);
        int recordId = asInt("recordId", json);
        int attributeId = asInt("attributeId", json);
        String value = asString("value", json);
        return new UpdateAttributeValueCommand(
                recordId,
                user,
                attributeId,
                new JsonValueParser().parse(value)
        );
    }

    private String asString(String name, JsonObject json) {
        return getMember(name, json).getAsString();
    }

    private String asString(JsonObject json, String name) {
        return asString(name, json);
    }

    private int asInt(String name, JsonObject json) {
        try {
            return getMember(name, json).getAsInt();
        } catch (ClassCastException e) {
            throw new MalformedJson(String.format("Expected member with name %s in %s to be an int", name, json));
        }
    }

    private JsonElement getMember(String name, JsonObject json) {
        JsonElement member = json.get(name);
        if (member == null) throw new MalformedJson(String.format("Expected member with name %s in %s", name, json));
        return member;
    }

    private JsonObject toJson(String jsonString) {
        return new JsonParser().parse(jsonString).getAsJsonObject();
    }
}
