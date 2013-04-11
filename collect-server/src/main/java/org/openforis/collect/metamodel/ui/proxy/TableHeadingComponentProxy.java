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

	public TableHeadingComponentProxy(T modelObject) {
		super(modelObject);
	}

	public static List<TableHeadingComponentProxy<?>> fromList(List<TableHeadingComponent> components) {
		ArrayList<TableHeadingComponentProxy<?>> result = new ArrayList<TableHeadingComponentProxy<?>>();
		for (TableHeadingComponent comp : components) {
			if ( comp instanceof Column ) {
				result.add(new ColumnProxy((Column) comp));
			} else {
				result.add(new ColumnGroupProxy((ColumnGroup) comp));
			}
		}
		return result;
	}
	
}
