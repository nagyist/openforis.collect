/**
 * 
 */
package org.openforis.collect.metamodel.ui.proxy;

import org.granite.messaging.amf.io.util.externalizer.annotation.ExternalizedProperty;
import org.openforis.collect.metamodel.ui.Column;

/**
 * @author S. Ricci
 *
 */
public class ColumnProxy extends TableHeadingComponentProxy<Column> implements AttributeDefinitionLinkedUIObjectProxy {

	public ColumnProxy(UIModelObjectProxy<?> parent, Column modelObject) {
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
}
