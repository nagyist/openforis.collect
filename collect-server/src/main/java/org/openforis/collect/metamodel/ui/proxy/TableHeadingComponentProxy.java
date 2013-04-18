/**
 * 
 */
package org.openforis.collect.metamodel.ui.proxy;

import java.util.ArrayList;
import java.util.List;

import org.openforis.collect.metamodel.ui.Column;
import org.openforis.collect.metamodel.ui.ColumnGroup;
import org.openforis.collect.metamodel.ui.TableHeadingComponent;

/**
 * @author S. Ricci
 *
 */
public abstract class TableHeadingComponentProxy<T extends TableHeadingComponent> extends UIModelObjectProxy<T> {

	public TableHeadingComponentProxy(UIModelObjectProxy<?> parent, T modelObject) {
		super(parent, modelObject);
	}

	public static List<TableHeadingComponentProxy<?>> fromList(UIModelObjectProxy<?> parent, List<TableHeadingComponent> components) {
		ArrayList<TableHeadingComponentProxy<?>> result = new ArrayList<TableHeadingComponentProxy<?>>();
		for (TableHeadingComponent comp : components) {
			if ( comp instanceof Column ) {
				result.add(new ColumnProxy(parent, (Column) comp));
			} else {
				result.add(new ColumnGroupProxy(parent, (ColumnGroup) comp));
			}
		}
		return result;
	}
	
}
