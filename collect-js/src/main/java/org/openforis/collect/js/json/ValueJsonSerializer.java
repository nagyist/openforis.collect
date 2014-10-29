package org.openforis.collect.js.json;

import org.openforis.collect.api.Value;
import org.openforis.idm.model.TextValue;

public class ValueJsonSerializer {
    String serialize(Value value) {
        if (value instanceof TextValue)
            return value.toString();
        throw new IllegalStateException("Encountered unknown value type: " + value.getClass().getName());
    }

}
