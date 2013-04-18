/**
 * 
 */
package org.openforis.collect.metamodel.ui.proxy;

import org.granite.messaging.amf.io.util.externalizer.annotation.ExternalizedProperty;
import org.openforis.collect.metamodel.ui.Field;
import org.openforis.collect.metamodel.ui.FormSection;

/**
 * @author S. Ricci
 *
 */
public class FieldProxy extends ComponentProxy<Field> implements FormSectionComponentProxy<FormSection> {

	public FieldProxy(UIModelObjectProxy<?> parent, Field modelObject) {
		super(parent, modelObject);
	}

	@ExternalizedProperty
	public int getAttributeId() {
		return modelObject.getAttributeId();
	}

	@ExternalizedProperty
	public String getAutoCompleteGroup() {
		return modelObject.getAutoCompleteGroup();
	}
	
}
