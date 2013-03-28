package org.openforis.collect.metamodel.ui;

import java.util.ArrayList;
import java.util.List;

import org.openforis.commons.collection.CollectionUtils;
import org.openforis.idm.metamodel.EntityDefinition;

/**
 * 
 * @author S. Ricci
 *
 */
public class Table extends Component {

	private static final long serialVersionUID = 1L;

	private EntityDefinition entityDefinition;
	private List<Column> columns;
	private List<ColumnGroup> columnGroups;
	
	public List<Column> getColumns() {
		return CollectionUtils.unmodifiableList(columns);
	}
	
	public void addColumn(Column column) {
		if ( columns == null ) {
			columns = new ArrayList<Column>();
		}
		columns.add(column);
	}
	
	public void removeColumn(Column column) {
		columns.remove(column);
	}

	public List<ColumnGroup> getColumnGroups() {
		return CollectionUtils.unmodifiableList(columnGroups);
	}
	
	public void addColumnGroup(ColumnGroup columnGroup) {
		if ( columnGroups == null ) {
			columnGroups = new ArrayList<ColumnGroup>();
		}
		columnGroups.add(columnGroup);
	}
	
	public void removeColumnGroup(ColumnGroup columnGroup) {
		columnGroups.remove(columnGroup);
	}

	public EntityDefinition getEntityDefinition() {
		return entityDefinition;
	}

	public void setEntityDefinition(EntityDefinition entityDefinition) {
		this.entityDefinition = entityDefinition;
	}
	
}
