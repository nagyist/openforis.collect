/**
 * 
 */
package org.openforis.collect.metamodel.ui.proxy;

import java.util.ArrayList;
import java.util.List;

import org.openforis.collect.metamodel.ui.Component;
import org.openforis.collect.metamodel.ui.Field;
import org.openforis.collect.metamodel.ui.Table;

/**
 * @author S. Ricci
 *
 */
public abstract class ComponentProxy<T extends Component> extends UIModelObjectProxy<T> {
	
	public ComponentProxy(T modelObject) {
		super(modelObject);
	}

	public static List<ComponentProxy<?>> fromList(List<Component> components) {
		ArrayList<ComponentProxy<?>> result = new ArrayList<ComponentProxy<?>>();
		for (Component comp : components) {
			if ( comp instanceof Table ) {
				result.add(new TableProxy((Table) comp));
			} else {
				result.add(new FieldProxy((Field) comp));
			}
		}
		return result;
	}
	
}
