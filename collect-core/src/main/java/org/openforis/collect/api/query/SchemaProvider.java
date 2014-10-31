package org.openforis.collect.api.query;

import java.util.Locale;

import org.openforis.collect.api.schema.EntityDef;

public interface SchemaProvider {
    
	public EntityDef schema(int surveyId, Locale locale);
	
}
