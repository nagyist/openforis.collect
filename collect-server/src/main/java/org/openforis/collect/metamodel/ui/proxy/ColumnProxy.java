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
public class ColumnProxy extends TableHeadingComponentProxy<Column> {

	public ColumnProxy(UIModelObjectProxy<?> parent, Column modelObject) {
		super(parent, modelObject);
	}

	@ExternalizedProperty
	public int getAttributeId() {
		return modelObject.getAttributeId();
	}

	
}
