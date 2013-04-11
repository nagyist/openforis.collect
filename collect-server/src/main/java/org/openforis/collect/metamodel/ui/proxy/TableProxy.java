package org.openforis.collect.metamodel.ui.proxy;

import java.util.List;

import org.granite.messaging.amf.io.util.externalizer.annotation.ExternalizedProperty;
import org.openforis.collect.metamodel.ui.Table;
import org.openforis.idm.metamodel.LanguageSpecificText;

/**
 * 
 * @author S. Ricci
 *
 */
public class TableProxy extends ComponentProxy<Table> implements FormSectionComponentProxy<Table> {

	public TableProxy(Table table) {
		super(table);
	}

	@ExternalizedProperty
	public List<TableHeadingComponentProxy<?>> getHeadingComponents() {
		return TableHeadingComponentProxy.fromList(modelObject.getHeadingComponents());
	}

	@ExternalizedProperty
	public List<LanguageSpecificText> getLabels() {
		return modelObject.getLabels();
	}

	@ExternalizedProperty
	public int getEntityId() {
		return modelObject.getEntityId();
	}

	@ExternalizedProperty
	public boolean isShowRowNumbers() {
		return modelObject.isShowRowNumbers();
	}

	@ExternalizedProperty
	public boolean isCountInSummaryList() {
		return modelObject.isCountInSummaryList();
	}
	
}
