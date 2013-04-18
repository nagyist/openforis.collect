/**
 * 
 */
package org.openforis.collect.metamodel.ui.proxy;

import java.util.ArrayList;
import java.util.List;

import org.granite.messaging.amf.io.util.externalizer.annotation.ExternalizedProperty;
import org.openforis.collect.metamodel.proxy.UIOptionsProxy;
import org.openforis.collect.metamodel.ui.FormSet;

/**
 * @author S. Ricci
 *
 */
public class FormSetProxy extends FormContainerProxy<FormSet> {

	private transient UIOptionsProxy uiOptions;

	public FormSetProxy(UIOptionsProxy uiOptions, FormSet modelObject) {
		super(null, modelObject);
		this.uiOptions = uiOptions;
	}

	public static List<FormSetProxy> fromList(UIOptionsProxy uiOptions, List<FormSet> items) {
		List<FormSetProxy> result = new ArrayList<FormSetProxy>();
		for (FormSet item: items) {
			result.add(new FormSetProxy(uiOptions, item));
		}
		return result;
	}

	@ExternalizedProperty
	public UIOptionsProxy getUIOptions() {
		return uiOptions;
	}

}
