/**
 * 
 */
package org.openforis.collect.metamodel.ui.proxy;

import org.granite.messaging.amf.io.util.externalizer.annotation.ExternalizedProperty;
import org.openforis.collect.metamodel.proxy.AttributeDefinitionProxy;
import org.openforis.collect.metamodel.ui.Field;
import org.openforis.collect.metamodel.ui.FormSection;

/**
 * @author S. Ricci
 *
 */
public class FieldProxy extends ComponentProxy<Field> implements FormSectionComponentProxy<FormSection>, AttributeModelObjectProxy {

	public FieldProxy(UIModelObjectProxy<?> parent, Field modelObject) {
		super(parent, modelObject);
	}

	@Override
	@ExternalizedProperty
	public int getAttributeId() {
		return modelObject.getAttributeId();
	}

	@Override
	@ExternalizedProperty
	public AttributeDefinitionProxy getAttributeDefinition() {
		return null;
	}
	
	@ExternalizedProperty
	public String getAutoCompleteGroup() {
		return modelObject.getAutoCompleteGroup();
	}
	
	@ExternalizedProperty
	public Enum<?> getFieldsOrder() {
		return modelObject.getFieldsOrder();
	}
	
}
