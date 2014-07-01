/**
 * 
 */
package org.openforis.collect.metamodel.ui.proxy;

import java.util.List;

import org.granite.messaging.amf.io.util.externalizer.annotation.ExternalizedProperty;
import org.openforis.collect.metamodel.ui.ColumnGroup;

/**
 * @author S. Ricci
 *
 */
public class ColumnGroupProxy extends TableHeadingComponentProxy<ColumnGroup> {
	
	public ColumnGroupProxy(UIModelObjectProxy<?> parent, ColumnGroup modelObject) {
		super(parent, modelObject);
	}

	@ExternalizedProperty
	public List<TableHeadingComponentProxy<?>> getHeadingComponents() {
		return TableHeadingComponentProxy.fromList(this, modelObject.getHeadingComponents());
	}

	@ExternalizedProperty
	public int getEntityDefinitionId() {
		return modelObject.getEntityDefinitionId();
	}
}
