/**
 * 
 */
package org.openforis.collect.metamodel.ui.proxy;

import java.util.ArrayList;
import java.util.List;

import org.granite.messaging.amf.io.util.externalizer.annotation.ExternalizedProperty;
import org.openforis.collect.metamodel.proxy.LanguageSpecificTextProxy;
import org.openforis.collect.metamodel.ui.Form;

/**
 * @author S. Ricci
 *
 */
public class FormProxy extends UIModelObjectProxy<Form> {

	public FormProxy(Form modelObject) {
		super(modelObject);
	}
	
	public static List<FormProxy> fromList(List<Form> items) {
		List<FormProxy> result = new ArrayList<FormProxy>();
		for (Form item: items) {
			result.add(new FormProxy(item));
		}
		return result;
	}
	
	@ExternalizedProperty
	public List<FormSectionProxy> getFormSections() {
		return FormSectionProxy.fromList(modelObject.getFormSections());
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
