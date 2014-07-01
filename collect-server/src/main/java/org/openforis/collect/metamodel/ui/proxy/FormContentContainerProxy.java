package org.openforis.collect.metamodel.ui.proxy;

import java.util.ArrayList;
import java.util.List;

import org.granite.messaging.amf.io.util.externalizer.annotation.ExternalizedProperty;
import org.openforis.collect.metamodel.ui.Field;
import org.openforis.collect.metamodel.ui.FormComponent;
import org.openforis.collect.metamodel.ui.FormContentContainer;
import org.openforis.collect.metamodel.ui.FormSection;
import org.openforis.collect.metamodel.ui.Table;

/**
 * 
 * @author S. Ricci
 *
 */
public abstract class FormContentContainerProxy <T extends FormContentContainer> extends UIModelObjectProxy<T> {

	public FormContentContainerProxy(UIModelObjectProxy<?> parent, T modelObject) {
		super(parent, modelObject);
	}

	@ExternalizedProperty
	public List<FormComponentProxy> getChildren() {
		List<FormComponentProxy> result = new ArrayList<FormComponentProxy>();
		List<FormComponent> children = modelObject.getChildren();
		for (FormComponent c: children) {
			if ( c instanceof Field ) {
				result.add(new FieldProxy(this, (Field) c));
			} else if ( c instanceof FormSection ) {
				result.add(new FormSectionProxy(this, (FormSection) c));
			} else if ( c instanceof Table ) {
				result.add(new TableProxy(this, (Table) c));
			}
		}
		return result;
	}
	
	@ExternalizedProperty
	public List<FormProxy> getForms() {
		return FormProxy.fromList(this, modelObject.getForms());
	}
	
}
