package org.openforis.collect.apiadapter;

import org.openforis.collect.api.query.SchemaProvider;
import org.openforis.collect.api.schema.AttributeDef;
import org.openforis.collect.api.schema.EntityDef;
import org.openforis.collect.api.schema.EntityListDef;
import org.openforis.collect.api.schema.ValueType;

import static java.util.Arrays.asList;

public class DefaultSchemaProvider implements SchemaProvider {
    private final EntityDef dummySchema = dummySchema();

    public EntityDef schema(int surveyId) {
        return dummySchema;
    }

    private EntityDef dummySchema() {
        return new EntityDef("plot", "Plot", asList(
                new AttributeDef("plot_number", "Plot Number", ValueType.Number),
                new EntityListDef("trees", "Trees",
                        new EntityDef("tree", "Tree", asList(
                                new AttributeDef("tree_number", "Tree Number", ValueType.Number)
                        ))
                ))
        );
    }

}
