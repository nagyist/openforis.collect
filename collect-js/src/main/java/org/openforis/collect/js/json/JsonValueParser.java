package org.openforis.collect.js.json;

import org.openforis.collect.api.Value;
import org.openforis.idm.model.TextValue;

public class JsonValueParser {
    Value parse(String valueJson) {
        return new TextValue(valueJson);
    }

}
