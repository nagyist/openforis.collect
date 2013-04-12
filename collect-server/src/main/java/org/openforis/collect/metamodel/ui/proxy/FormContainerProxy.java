/**
 * 
 */
package org.openforis.collect.metamodel.ui.proxy;

import java.util.List;

import org.granite.messaging.amf.io.util.externalizer.annotation.ExternalizedProperty;
import org.openforis.collect.metamodel.proxy.LanguageSpecificTextProxy;
import org.openforis.collect.metamodel.ui.FormContainer;

/**
 * @author S. Ricci
 *
 */
public abstract class FormContainerProxy<T extends FormContainer> extends UIModelObjectProxy<T> {

	public FormContainerProxy(T modelObject) {
		super(modelObject);
	}
	
	@ExternalizedProperty
	public List<FormProxy> getForms() {
		return FormProxy.fromList(modelObject.getForms());
	}

	@ExternalizedProperty
	public int getEntityId() {
		return modelObject.getEntityId();
	}

	@ExternalizedProperty
	public List<LanguageSpecificTextProxy> getLabels() {
		return LanguageSpecificTextProxy.fromList(modelObject.getLabels());
	}

}
