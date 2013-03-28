/**
 * 
 */
package org.openforis.collect.metamodel.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.openforis.commons.collection.CollectionUtils;

/**
 * @author S. Ricci
 *
 */
public class ColumnGroup implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Table table;
	private List<Column> columns;

	public ColumnGroup(Table table) {
		super();
		this.table = table;
	}
	
	public Table getTable() {
		return table;
	}
	
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
	
}
