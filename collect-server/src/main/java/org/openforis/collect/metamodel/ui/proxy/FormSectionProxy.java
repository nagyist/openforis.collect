package org.openforis.collect.metamodel.ui.proxy;

import org.granite.messaging.amf.io.util.externalizer.annotation.ExternalizedProperty;
import org.openforis.collect.metamodel.ui.FormSection;

/**
 * 
 * @author S. Ricci
 *
 */
public class FormSectionProxy extends FormContentContainerProxy<FormSection> implements FormComponentProxy, EntityDefinitionLinkedUIObjectProxy {

	public FormSectionProxy(UIModelObjectProxy<?> parent, FormSection modelObject) {
		super(parent, modelObject);
	}

	@Override
	@ExternalizedProperty
	public int getEntityDefinitionId() {
		return modelObject.getEntityDefinition().getId();
	}
	
	@Override
	@ExternalizedProperty
	public int getNodeDefinitionId() {
		return getEntityDefinitionId();
	}
	
}
