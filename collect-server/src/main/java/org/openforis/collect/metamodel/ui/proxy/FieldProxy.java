/**
 * 
 */
package org.openforis.collect.metamodel.ui.proxy;

import java.util.List;

import org.granite.messaging.amf.io.util.externalizer.annotation.ExternalizedProperty;
import org.openforis.collect.metamodel.ui.Field;

/**
 * @author S. Ricci
 *
 */
public class FieldProxy extends UIModelObjectProxy<Field> implements FormComponentProxy, AttributeDefinitionLinkedUIObjectProxy {

	public FieldProxy(UIModelObjectProxy<?> parent, Field modelObject) {
		super(parent, modelObject);
	}

	@Override
	@ExternalizedProperty
	public int getAttributeDefinitionId() {
		return modelObject.getAttributeDefinitionId();
	}

	@Override
	@ExternalizedProperty
	public int getNodeDefinitionId() {
		return getAttributeDefinitionId();
	}
	
	@ExternalizedProperty
	public String getAutoCompleteGroup() {
		return modelObject.getAutoCompleteGroup();
	}
	
	@ExternalizedProperty
	public Enum<?> getFieldsOrder() {
		return modelObject.getFieldsOrder();
	}
	
	@ExternalizedProperty
	public List<String> getVisibleFields() {
		return modelObject.getVisibleFields();
	}

}
