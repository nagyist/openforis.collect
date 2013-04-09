/**
 * 
 */
package org.openforis.collect.metamodel.ui;

/**
 * @author S. Ricci
 *
 */
public abstract class TableHeadingComponent extends UIModelObject {

	private static final long serialVersionUID = 1L;
	
	TableHeadingComponent(Table table, int id) {
		super(table.getUiOptions(), id);
		this.table = table;
	}

	private Table table;
	
	public Table getTable() {
		return table;
	}
	
}
