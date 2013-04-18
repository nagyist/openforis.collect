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
	
	public ComponentProxy(UIModelObjectProxy<?> parent, T modelObject) {
		super(parent, modelObject);
	}

	public static List<ComponentProxy<?>> fromList(UIModelObjectProxy<?> parent, List<Component> components) {
		ArrayList<ComponentProxy<?>> result = new ArrayList<ComponentProxy<?>>();
		for (Component comp : components) {
			if ( comp instanceof Table ) {
				result.add(new TableProxy(parent, (Table) comp));
			} else {
				result.add(new FieldProxy(parent, (Field) comp));
			}
		}
		return result;
	}
	
}
