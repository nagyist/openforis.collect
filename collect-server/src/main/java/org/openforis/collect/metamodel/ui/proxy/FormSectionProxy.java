package org.openforis.collect.metamodel.ui.proxy;

import java.util.ArrayList;
import java.util.List;

import org.granite.messaging.amf.io.util.externalizer.annotation.ExternalizedProperty;
import org.openforis.collect.metamodel.proxy.LanguageSpecificTextProxy;
import org.openforis.collect.metamodel.ui.Field;
import org.openforis.collect.metamodel.ui.FormSection;
import org.openforis.collect.metamodel.ui.FormSectionComponent;
import org.openforis.collect.metamodel.ui.Table;

/**
 * 
 * @author S. Ricci
 *
 */
public class FormSectionProxy extends UIModelObjectProxy<FormSection> implements FormSectionComponentProxy<FormSection> {

	public FormSectionProxy(UIModelObjectProxy<?> parent, FormSection modelObject) {
		super(parent, modelObject);
	}

	public static List<FormSectionProxy> fromList(UIModelObjectProxy<?> parent, List<FormSection> items) {
		List<FormSectionProxy> result = new ArrayList<FormSectionProxy>();
		for (FormSection item: items) {
			result.add(new FormSectionProxy(parent, item));
		}
		return result;
	}
	
	@ExternalizedProperty
	public List<FormSectionComponentProxy<?>> getChildren() {
		List<FormSectionComponentProxy<?>> result = new ArrayList<FormSectionComponentProxy<?>>();
		List<FormSectionComponent> children = modelObject.getChildren();
		for (FormSectionComponent c: children) {
			if ( c instanceof FormSection ) {
				result.add(new FormSectionProxy(this, (FormSection) c));
			} else if ( c instanceof Table ) {
				result.add(new TableProxy(this, (Table) c));
			} else if ( c instanceof Field ) {
				result.add(new FieldProxy(this, (Field) c));
			}
		}
		return result;
	}
	
	@ExternalizedProperty
	public List<LanguageSpecificTextProxy> getLabels() {
		return LanguageSpecificTextProxy.fromList(modelObject.getLabels());
	}

}
