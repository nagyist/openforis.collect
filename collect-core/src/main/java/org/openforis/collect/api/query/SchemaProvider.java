package org.openforis.collect.api.query;

import org.openforis.collect.api.event.Event;
import org.openforis.collect.api.schema.EntityDef;

import java.util.List;

public interface SchemaProvider {
    public EntityDef schema(int surveyId);
}
