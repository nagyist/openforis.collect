/**
 * 
 */
package org.openforis.collect.metamodel.ui.proxy;

import java.util.ArrayList;
import java.util.List;

import org.granite.messaging.amf.io.util.externalizer.annotation.ExternalizedProperty;
import org.openforis.collect.metamodel.proxy.LanguageSpecificTextProxy;
import org.openforis.collect.metamodel.ui.FormSet;

/**
 * @author S. Ricci
 *
 */
public class FormSetProxy extends UIModelObjectProxy<FormSet> {

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

	@ExternalizedProperty
	public int getEntityId() {
		return modelObject.getEntityId();
	}

	@ExternalizedProperty
	public List<FormProxy> getForms() {
		return FormProxy.fromList(modelObject.getForms());
	}

	@ExternalizedProperty
	public List<LanguageSpecificTextProxy> getLabels() {
		return LanguageSpecificTextProxy.fromList(modelObject.getLabels());
	}

	
}
