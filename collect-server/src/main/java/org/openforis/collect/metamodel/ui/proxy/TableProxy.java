package org.openforis.collect.metamodel.ui.proxy;

import java.util.List;

import org.granite.messaging.amf.io.util.externalizer.annotation.ExternalizedProperty;
import org.openforis.collect.metamodel.ui.Table;
import org.openforis.collect.metamodel.ui.Table.Direction;

/**
 * 
 * @author S. Ricci
 *
 */
public class TableProxy extends UIModelObjectProxy<Table> implements FormComponentProxy, EntityDefinitionLinkedUIObjectProxy {

	public TableProxy(UIModelObjectProxy<?> parent, Table table) {
		super(parent, table);
	}
	
	@Override
	@ExternalizedProperty
	public int getNodeDefinitionId() {
		return getEntityDefinitionId();
	}
	
	@Override
	@ExternalizedProperty
	public int getEntityDefinitionId() {
		return modelObject.getEntityDefinitionId();
	}

	@ExternalizedProperty
	public List<TableHeadingComponentProxy<?>> getHeadingComponents() {
		return TableHeadingComponentProxy.fromList(this, modelObject.getHeadingComponents());
	}

	@ExternalizedProperty
	public boolean isShowRowNumbers() {
		return modelObject.isShowRowNumbers();
	}

	@ExternalizedProperty
	public boolean isCountInSummaryList() {
		return modelObject.isCountInSummaryList();
	}
	
	@ExternalizedProperty
	public Direction getDirection() {
		return modelObject.getDirection();
	}
	
}
