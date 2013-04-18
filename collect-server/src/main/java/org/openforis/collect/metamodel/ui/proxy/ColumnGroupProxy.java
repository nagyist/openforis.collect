/**
 * 
 */
package org.openforis.collect.metamodel.ui.proxy;

import java.util.List;

import org.granite.messaging.amf.io.util.externalizer.annotation.ExternalizedProperty;
import org.openforis.collect.metamodel.ui.ColumnGroup;
import org.openforis.idm.metamodel.LanguageSpecificText;

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
	public List<LanguageSpecificText> getLabels() {
		return modelObject.getLabels();
	}

	
	
}
