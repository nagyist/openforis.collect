package org.openforis.collect.apiadapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.openforis.collect.api.query.SchemaProvider;
import org.openforis.collect.api.schema.AttributeDef;
import org.openforis.collect.api.schema.EntityDef;
import org.openforis.collect.api.schema.NodeDef;
import org.openforis.collect.api.schema.ValueType;
import org.openforis.idm.metamodel.EntityDefinition;
import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.metamodel.NodeLabel.Type;
import org.openforis.idm.metamodel.Survey;

public class DefaultSchemaProvider implements SchemaProvider {
    
	private SurveyProvider surveyProvider;

    public DefaultSchemaProvider(SurveyProvider surveyProvider) {
		this.surveyProvider = surveyProvider;
	}
    
    public EntityDef schema(int surveyId, Locale locale) {
    	Survey survey = surveyProvider.provideSurvey(surveyId);
    	
    	EntityDefinition rootEntityDef = survey.getSchema().getRootEntityDefinitions().get(0);
    	EntityDef entityDef = entityDef(rootEntityDef, locale);
        return entityDef;
    }

	private EntityDef entityDef(EntityDefinition entityDef, Locale locale) {
		return new EntityDef(String.valueOf(entityDef.getId()), extractLabel(entityDef, locale), memberDefs(entityDef, locale));
	}

    private List<? extends NodeDef> memberDefs(EntityDefinition entityDef, Locale locale) {
    	List<NodeDef> result = new ArrayList<NodeDef>();
    	List<NodeDefinition> children = entityDef.getChildDefinitions();
    	for (NodeDefinition child : children) {
			if (child instanceof EntityDefinition) {
				result.add(entityDef((EntityDefinition) child, locale));
			} else {
				result.add(new AttributeDef(String.valueOf(child.getId()), extractLabel(child, locale), ValueType.Text));
			}
		}
		return result;
	}

	private String extractLabel(NodeDefinition node, Locale locale) {
		String label = node.getLabel(Type.INSTANCE, locale.getLanguage());
		if (label == null) {
			label = node.getLabel(Type.INSTANCE);
			if (label == null) {
				label = node.getName();
			}
		}
		return label;
	}

}
