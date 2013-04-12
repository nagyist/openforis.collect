/**
 * 
 */
package org.openforis.collect.metamodel.ui.proxy;

import java.util.ArrayList;
import java.util.List;

import org.openforis.collect.metamodel.ui.FormSet;

/**
 * @author S. Ricci
 *
 */
public class FormSetProxy extends FormContainerProxy<FormSet> {

	public FormSetProxy(FormSet modelObject) {
		super(modelObject);
	}

	public static List<FormSetProxy> fromList(List<FormSet> items) {
		List<FormSetProxy> result = new ArrayList<FormSetProxy>();
		for (FormSet item: items) {
			result.add(new FormSetProxy(item));
		}
		return result;
	}

}
