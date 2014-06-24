package org.openforis.collect.persistence.xml.internal.unmarshal;

import org.openforis.collect.metamodel.ui.FormSectionComponent;
import org.openforis.collect.metamodel.ui.FormSectionContainer;

/**
 * 
 * @author S. Ricci
 *
 */
public class FormSectionComponentPR extends UIModelPR {

	protected FormSectionContainer parent;
	protected FormSectionComponent component;
	
	FormSectionComponentPR(String tagName) {
		super(tagName);
	}
	
	public FormSectionContainer getParent() {
		return parent;
	}

	public void setParent(FormSectionContainer parent) {
		this.parent = parent;
	}
	
}
