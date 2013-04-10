package org.openforis.collect.persistence.xml.internal.unmarshal;

import org.openforis.collect.metamodel.ui.FormSectionComponent;
import org.openforis.collect.metamodel.ui.FormSectionsContainer;

/**
 * 
 * @author S. Ricci
 *
 */
public class FormSectionComponentPR extends UIModelPR {

	protected FormSectionsContainer parent;
	protected FormSectionComponent component;
	
	FormSectionComponentPR(String tagName) {
		super(tagName);
	}
	
	public FormSectionsContainer getParent() {
		return parent;
	}

	public void setParent(FormSectionsContainer parent) {
		this.parent = parent;
	}
	
}
