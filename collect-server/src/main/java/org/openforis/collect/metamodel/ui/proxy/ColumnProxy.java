/**
 * 
 */
package org.openforis.collect.metamodel.ui.proxy;

import org.granite.messaging.amf.io.util.externalizer.annotation.ExternalizedProperty;
import org.openforis.collect.metamodel.proxy.AttributeDefinitionProxy;
import org.openforis.collect.metamodel.ui.Column;

/**
 * @author S. Ricci
 *
 */
public class ColumnProxy extends TableHeadingComponentProxy<Column> implements AttributeModelObjectProxy {

	public ColumnProxy(UIModelObjectProxy<?> parent, Column modelObject) {
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
	
}
